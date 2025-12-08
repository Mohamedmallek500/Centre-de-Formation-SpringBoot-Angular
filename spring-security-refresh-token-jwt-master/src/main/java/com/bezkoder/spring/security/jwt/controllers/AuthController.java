package com.bezkoder.spring.security.jwt.controllers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.security.jwt.exception.TokenRefreshException;
import com.bezkoder.spring.security.jwt.models.Admin;
import com.bezkoder.spring.security.jwt.models.Etudiant;
import com.bezkoder.spring.security.jwt.models.Formateur;
import com.bezkoder.spring.security.jwt.models.Utilisateur;
import com.bezkoder.spring.security.jwt.models.RefreshToken;
import com.bezkoder.spring.security.jwt.models.Role;
import com.bezkoder.spring.security.jwt.payload.request.LoginRequest;
import com.bezkoder.spring.security.jwt.payload.request.SignupRequest;
import com.bezkoder.spring.security.jwt.payload.response.UserInfoResponse;
import com.bezkoder.spring.security.jwt.payload.response.MessageResponse;
import com.bezkoder.spring.security.jwt.repository.RoleRepository;
import com.bezkoder.spring.security.jwt.repository.UtilisateurRepository;
import com.bezkoder.spring.security.jwt.security.jwt.JwtUtils;
import com.bezkoder.spring.security.jwt.security.services.RefreshTokenService;
import com.bezkoder.spring.security.jwt.security.services.UserDetailsImpl;
import com.bezkoder.spring.security.jwt.service.EtudiantService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UtilisateurRepository utilisateurRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private RefreshTokenService refreshTokenService;

  @Autowired
  private EtudiantService etudiantService;   // 🔥 pour générer le matricule

  // ===========================
  //        LOGIN (EMAIL + MDP)
  // ===========================
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    // JWT access token dans un cookie
    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    // Rôles sous forme de String
    List<String> roles = userDetails.getAuthorities()
            .stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    // Refresh Token en base + cookie
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

    UserInfoResponse userInfo = new UserInfoResponse(
            userDetails.getId(),
            userDetails.getNom(),
            userDetails.getPrenom(),
            userDetails.getEmail(),
            roles
    );

    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
            .body(userInfo);
  }

  // ===========================
  //            SIGNUP
  // ===========================
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

    if (utilisateurRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Instancier la bonne sous-classe de Utilisateur
    Utilisateur user;

    switch (signUpRequest.getRole()) {
      case FORMATEUR -> {
        Formateur formateur = new Formateur();
        formateur.setSpecialite(signUpRequest.getSpecialite()); // spécialité formateur
        user = formateur;
      }
      case ETUDIANT -> {
        Etudiant etudiant = new Etudiant();
        // 🔥 Matricule généré automatiquement
        etudiant.setMatricule(etudiantService.generateMatricule());
        // 🔥 dateInscription est remplie automatiquement par @CreationTimestamp
        user = etudiant;
      }
      case ADMIN -> {
        // 🔥 Admin séparé, PAS de matricule
        Admin admin = new Admin();
        user = admin;
      }
      default -> throw new RuntimeException("Unsupported role: " + signUpRequest.getRole());
    }

    // Champs communs de Utilisateur
    user.setNom(signUpRequest.getNom());
    user.setPrenom(signUpRequest.getPrenom());
    user.setEmail(signUpRequest.getEmail());
    user.setMotDePasse(encoder.encode(signUpRequest.getPassword()));
    user.setTelephone(signUpRequest.getTelephone());
    user.setCin(signUpRequest.getCin());
    user.setPhoto(signUpRequest.getPhoto());

    // ROLE (entité Role)
    Role selectedRole = roleRepository.findByName(signUpRequest.getRole())
            .orElseThrow(() -> new RuntimeException("Error: Role not found"));

    user.setRole(selectedRole);

    utilisateurRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  // ===========================
  //            LOGOUT
  // ===========================
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetailsImpl userDetails) {
      refreshTokenService.deleteByUserId(userDetails.getId());
    }

    ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
    ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
            .body(new MessageResponse("You've been signed out!"));
  }

  // ===========================
  //        REFRESH TOKEN
  // ===========================
  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(HttpServletRequest request) {

    String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

    if (refreshToken == null || refreshToken.isEmpty()) {
      return ResponseEntity.badRequest()
              .body(new MessageResponse("Refresh Token is empty!"));
    }

    return refreshTokenService.findByToken(refreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
              ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
              return ResponseEntity.ok()
                      .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                      .body(new MessageResponse("Token refreshed successfully!"));
            })
            .orElseThrow(() -> new TokenRefreshException(
                    refreshToken,
                    "Refresh token is not in database!"
            ));
  }
}
