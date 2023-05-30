package org.caja.ideal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 *  Tabla de User
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_db")
public class UserModels implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotBlank
    @Size(max = 80)
    private String email;
    @NotBlank
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Sin espacion en blanco")
    private String passaword;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name ="user_role", // config de tabla intermedia con el nombre user_role
       joinColumns = @JoinColumn(name = "user_id"), // Nombre de llave foranea de la tabla User
            inverseJoinColumns = @JoinColumn(name = "role_id")  // Nombre de llave foranea de la tabla Role
    )
    private Set<Role> roles;
}
