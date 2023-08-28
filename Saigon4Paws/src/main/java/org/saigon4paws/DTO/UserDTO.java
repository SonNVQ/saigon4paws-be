package org.saigon4paws.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.saigon4paws.Models.Role;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;

    @Size(min = 4, max = 32, message = "Username length must be between 4 and 32 characters!")
    @NotEmpty(message = "Username must not be empty!")
    private String username;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Password must not be empty!")
    private String password;

    @NotEmpty(message = "Email must not be empty!")
    private String fullName;

    @Email(message = "Email format is invalid!")
    @NotEmpty(message = "Email must not be empty!")
    private String email;
}
