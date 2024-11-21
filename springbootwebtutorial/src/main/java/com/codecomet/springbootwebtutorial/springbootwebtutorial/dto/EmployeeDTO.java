package com.codecomet.springbootwebtutorial.springbootwebtutorial.dto;


import com.codecomet.springbootwebtutorial.springbootwebtutorial.Annotations.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name of the employee cannot be blank")
    @Size(min = 3,max = 10, message = "Number of character should be in range 3 to 10")
    private String name;

    @Email(message = "Email should be a valid email")
    private String email;

    @Max(value = 80, message = "Age cannot be grater than 80")
    @Min(value = 18, message = "Age cannot be less than 18")
    private Integer age;

    //@Pattern(regexp = "^(ADMIN|USER)$", message = "The Role od employee can be USER or ADMIN only")
    @EmployeeRoleValidation
    private String role; //ADMIN,USER

    @NotNull
    @Positive
    @Digits(integer = 6,fraction = 2, message = "Sallary can be in the from XXXXXX.YY")
    private Double salary;

    @PastOrPresent(message = "Date of joining cannot be in the future")
    private LocalDate dateOfJoining;
    @JsonProperty("isActive")
    private Boolean isActive;

}
