package com.heleyquin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private Integer id;
    @NotBlank(message = "Username is not blank")
    @Length(min = 4, max = 10, message = "Username min 4, max 10")
    private String username;
    @Pattern(regexp = "^[\\S]{4,}$", message = "Password is not valid")
    private String password;
    @NotBlank(message = "First name is not blank")
    private String firstName;
    @NotBlank(message = "Last name is not blank")
    private String lastName;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;
}
