package WD.Works.repository;

import WD.Works.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByUsuarioId(Long usuarioId);
    List<Produto> findByUsuarioIdAndNomeContainingIgnoreCase(
            Long usuarioId,
            String nome
    );
}
