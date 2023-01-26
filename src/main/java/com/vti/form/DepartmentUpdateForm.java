package com.vti.form;

import com.vti.validation.DepartmentIdExists;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentUpdateForm {
//    @DepartmentIdExists
    private Integer id;
    private String name;
    private Integer totalMembers;
    private String type;
}
