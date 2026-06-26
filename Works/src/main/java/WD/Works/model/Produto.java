package WD.Works.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
