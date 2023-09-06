package com.pepeta.player.model.specification;

import com.pepeta.player.model.Player;
import com.pepeta.player.model.enumeration.Gender;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

/**
 * Filters for searching the data and filtering for the user
 *
 * @author Kelsas
 */
public class PlayerSpecification {

    public PlayerSpecification() {
        super();
    }

    public static Specification<Player> createSpecification(Gender gender, String email, String phoneNumber, String name) {
        return (root, query, cb) -> {
            final ArrayList<Predicate> predicates = new ArrayList<>();

            if(gender!=null) {
                predicates.add(cb.equal(root.get("gender"), gender));
            }

            if (email!=null) {
                predicates.add(cb.equal(root.get("email"), email ));
            }

            if (phoneNumber!=null) {
                predicates.add(cb.equal(root.get("phoneNumber"), phoneNumber ));
            }

            if (name != null) {
                String exp = "%" + name + "%";
                predicates.add(cb.like(root.get("firstName"), exp));
                predicates.add(cb.like(root.get("lastName"), exp));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
