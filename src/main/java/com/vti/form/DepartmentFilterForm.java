package com.vti.form;

import com.vti.entity.Department;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class DepartmentFilterForm {

    private String search;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate minCreatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate maxCreatedDate;

    private Integer minCreatedYear;

    private Department.Type type;

    private Long minAccount;

    private Long maxAccount;

}
