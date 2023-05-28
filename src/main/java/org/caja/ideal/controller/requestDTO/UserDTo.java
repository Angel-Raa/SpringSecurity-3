package org.caja.ideal.controller.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTo {
    @Email
    private String email;
    private String password;
    @NotBlank
    private String username;
    private Set<String> role;
}
