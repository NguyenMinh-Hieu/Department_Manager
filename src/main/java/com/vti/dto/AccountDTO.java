package com.vti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vti.entity.Account;
import com.vti.entity.Department;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDTO extends RepresentationModel<AccountDTO> {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private Account.Role role;
    private String departmentName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    private LocalDateTime updatedAt;
}
