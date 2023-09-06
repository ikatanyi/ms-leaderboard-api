package com.castille.pkg.model.specification;

import com.castille.customer.model.Customer;
import com.castille.customer.model.enumeration.Gender;
import com.castille.pkg.model.ProductPackage;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

/**
 * Filters for searching the data and filtering for the user
 *
 * @author Kelsas
 */
public class ProductPackageSpecification {

    public ProductPackageSpecification() {
        super();
    }

    public static Specification<ProductPackage> createSpecification(String productName, String description) {
        return (root, query, cb) -> {
            final ArrayList<Predicate> predicates = new ArrayList<>();

            if(productName!=null) {
                String exp = "%" + productName + "%";
                predicates.add(cb.like(root.get("product").get("description"), exp));
            }

            if (description != null) {
                String exp = "%" + description + "%";
                predicates.add(cb.like(root.get("description"), exp));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
