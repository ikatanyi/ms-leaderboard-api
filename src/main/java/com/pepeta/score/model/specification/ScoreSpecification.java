package com.pepeta.score.model.specification;

import com.pepeta.score.model.Score;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

/**
 * Filters for searching the data and filtering for the user
 *
 * @author Kelsas
 */
public class ScoreSpecification {

    public ScoreSpecification() {
        super();
    }

    public static Specification<Score> createSpecification(Long playerId, Long score) {
        return (root, query, cb) -> {
            final ArrayList<Predicate> predicates = new ArrayList<>();

            if(playerId != null) {
                predicates.add(cb.equal(root.get("player").get("id"), playerId));
            }

            if (score != null) {
                predicates.add(cb.equal(root.get("score"), score));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
