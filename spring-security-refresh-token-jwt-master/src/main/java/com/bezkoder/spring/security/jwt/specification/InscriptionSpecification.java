package com.bezkoder.spring.security.jwt.specification;

import com.bezkoder.spring.security.jwt.models.Inscription;
import com.bezkoder.spring.security.jwt.models.StatutInscription;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class InscriptionSpecification {

    public static Specification<Inscription> withFilters(
            String etudiantNomPrenom,
            StatutInscription statut,
            String groupeNom
    ) {
        return (Root<Inscription> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Predicate predicate = cb.conjunction();

            // 🔹 JOIN vers Etudiant
            Join<Object, Object> etudiantJoin = root.join("etudiant", JoinType.INNER);
            // 🔹 JOIN vers Groupe
            Join<Object, Object> groupeJoin = root.join("groupe", JoinType.INNER);

            // =========================
            // FILTRE : Nom + Prénom étudiant (ensemble)
            // =========================
            if (etudiantNomPrenom != null && !etudiantNomPrenom.isBlank()) {
                String likePattern = "%" + etudiantNomPrenom.toLowerCase() + "%";

                Expression<String> nom = cb.lower(etudiantJoin.get("nom"));
                Expression<String> prenom = cb.lower(etudiantJoin.get("prenom"));

                Predicate nomOrPrenom = cb.or(
                        cb.like(nom, likePattern),
                        cb.like(prenom, likePattern)
                );

                predicate = cb.and(predicate, nomOrPrenom);
            }

            // =========================
            // FILTRE : Statut inscription
            // =========================
            if (statut != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("statut"), statut));
            }

            // =========================
            // FILTRE : Nom du groupe
            // =========================
            if (groupeNom != null && !groupeNom.isBlank()) {
                String likePattern = "%" + groupeNom.toLowerCase() + "%";

                predicate = cb.and(predicate,
                        cb.like(cb.lower(groupeJoin.get("nom")), likePattern));
            }

            return predicate;
        };
    }
}
