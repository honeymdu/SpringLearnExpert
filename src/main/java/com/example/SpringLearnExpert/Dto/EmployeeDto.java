package com.example.SpringLearnExpert.Dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EmployeeDto {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate dateOfBirth;
    private Boolean isActive;

}
