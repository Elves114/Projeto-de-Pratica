package WD.Works.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "clientesDivida")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Divida {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nomeDevedor;

        private BigDecimal valor;

        private LocalDate dataCriacao;

        private Boolean paga;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;
}
