package com.vti.specification;

import com.vti.entity.Account;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.AccountFilterForm;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> buildWhere(AccountFilterForm form) {
        if (form == null) {
            return null;
        }
        return hasUsernameLike(form.getSearch())
//                .or(hasDepartmentNameLike(form.getSearch()))
                .and(hasRoleEqual(form.getRole()))
                .and(hasIdGreaterThanOrEqualTo(form.getMinId()))
                .and(hasIdLessThanOrEqualTo(form.getMaxId()));
    }

    private static Specification<Account> hasUsernameLike(String search) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(search)) {
                return null;
            }
            return builder.like(root.get("username"), "%" + search.trim() + "%");
        };
    }

    private static Specification<Account> hasDepartmentNameLike(String search) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(search)) {
                return null;
            }
            return builder.like(root.get("department").get("name"), "%" + search.trim() + "%");
        };
    }

    private static Specification<Account> hasIdGreaterThanOrEqualTo(Integer minId) {
        return (root, query, builder) -> {
            if (minId == null) {
                return null;
            }
            return builder.greaterThanOrEqualTo(root.get("id"), minId);
        };
    }

    private static Specification<Account> hasIdLessThanOrEqualTo(Integer maxId) {
        return (root, query, builder) -> {
            if (maxId == null) {
                return null;
            }
            return builder.lessThanOrEqualTo(root.get("id"), maxId);
        };
    }

    public static Specification<Account> hasRoleEqual(Account.Role role) {
        return (root, query, builder) -> {
            if (role == null) {
                return null;
            }
            return builder.equal(root.get("role"), role);
        };
    }

    public static Specification<Account> buildSpec(AccountFilterForm form) {
        if (form == null) {
            return null;
        }
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(form.getSearch())) {
                String pattern = "%" + form.getSearch().trim() + "%";
                predicates.add(builder.or(
                        builder.like(root.get("username"), pattern),
                        builder.like(root.get("department").get("name"), pattern)
                ));
            }
            if (form.getMinId() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("id"), form.getMinId()));
            }
            if (form.getMaxId() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("id"), form.getMaxId()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}