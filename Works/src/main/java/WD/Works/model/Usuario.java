package WD.Works.model;

import WD.Works.model.Auth.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String nome;
    @Size(min = 8)
    private String senha;
    @Email
    private String email;

    @Column(nullable = false)
    private Integer tentativasFalhadas = 0;

    private LocalDateTime bloqueadoAte;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "usuario")
    private List<Produto> produtos = new ArrayList<>();
    @OneToMany(mappedBy = "usuario")
    private List<Venda> vendas  = new ArrayList<>();
    @OneToMany(mappedBy = "usuario")
    private List<Divida> dividas = new ArrayList<>();
}
