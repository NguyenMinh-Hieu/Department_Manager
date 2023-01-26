package com.vti.specification;

import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DepartmentSpecification {
    public static Specification<Department> buildWhere(DepartmentFilterForm form) {
        if (form == null) {
            return null;
        }
        return hasNameLike(form.getSearch())
//                .or(hasAccountUsernameLike(form.getSearch()))
                .and(hasCreatedDateEqual(form.getCreatedDate()))
                .and(hasCreatedDateGreaterThanOrEqualTo(form.getMinCreatedDate()))
                .and(hasCreatedDateLessThanOrEqualTo(form.getMaxCreatedDate()))
////                .and(hasCreatedYearGreaterThanOrEqualTo(form.getMinCreatedYear()))
                .and(hasTypeEqual(form.getType()));
    }

    private static Specification<Department> hasNameLike(String search) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(search)) {
                return null;
            }
            return builder.like(root.get("name"), "%" + search.trim() + "%");
        };
    }

    private static Specification<Department> hasAccountUsernameLike(String search) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(search)) {
                return null;
            }
            return builder.like(
                    root.join("accounts").get("username"),
                    "%" + search.trim() + "%"
            );
        };
    }

    private static Specification<Department> hasCreatedDateEqual(LocalDate createdDate) {
        return (root, query, builder) -> {
            if (createdDate == null) {
                return null;
            }
            return builder.equal(root.get("createdDate"), createdDate);
        };
    }

    private static Specification<Department> hasCreatedDateGreaterThanOrEqualTo(LocalDate minCreatedDate) {
        return (root, query, builder) -> {
            if (minCreatedDate == null) {
                return null;
            }
            return builder.greaterThanOrEqualTo(root.get("createdDate"), minCreatedDate);
        };
    }

    private static Specification<Department> hasCreatedDateLessThanOrEqualTo(LocalDate maxCreatedDate) {
        return (root, query, builder) -> {
            if (maxCreatedDate == null) {
                return null;
            }
            return builder.lessThanOrEqualTo(root.get("createdDate"), maxCreatedDate);
        };
    }

    private static Specification<Department> hasCreatedYearGreaterThanOrEqualTo(Integer minCreatedYear) {
        return (root, query, builder) -> {
            if (minCreatedYear == null) {
                return null;
            }
            return builder.greaterThanOrEqualTo(
                    builder.function("YEAR", Integer.class, root.get("createdDate")),
                    minCreatedYear
            );
        };
    }

    private static Specification<Department> hasTypeEqual(Department.Type type) {
        return (root, query, builder) -> {
            if (type == null) {
                return null;
            }
            return builder.equal(root.get("type"), type);
        };
    }

    public static Specification<Department> buildSpec(DepartmentFilterForm form) {
        if (form == null) {
            return null;
        }
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(form.getSearch())) {
                String pattern = "%" + form.getSearch().trim() + "%";
                predicates.add(builder.or(
                        builder.like(root.get("name"), pattern),
                        builder.like(root.join("accounts").get("username"), pattern)
                ));
            }
            if (form.getCreatedDate() != null) {
                predicates.add(builder.equal(root.get("createdDate"), form.getCreatedDate()));
            }
            if (form.getMinCreatedDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdDate"), form.getMinCreatedDate()));
            }
            if (form.getMaxCreatedDate() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createdDate"), form.getMaxCreatedDate()));
            }
            if (form.getMinCreatedYear() != null) {
                predicates.add(builder.greaterThanOrEqualTo(builder.function("YEAR", Integer.class, root.get("createdDate")), form.getMinCreatedYear()));
            }
            if (form.getType() != null) {
                predicates.add(builder.equal(root.get("type"), form.getType()));
            }
            if (form.getMinAccount() != null) {
                query.groupBy(root.get("id")).having(builder.greaterThanOrEqualTo(builder.count(root.join("accounts", JoinType.LEFT)), form.getMinAccount()));
            }
            if (form.getMaxAccount() != null) {
                query.groupBy(root.get("id")).having(builder.lessThanOrEqualTo(builder.count(root.join("accounts", JoinType.LEFT)), form.getMaxAccount()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}