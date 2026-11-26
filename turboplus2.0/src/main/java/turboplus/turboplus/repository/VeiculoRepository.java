
package turboplus.turboplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import turboplus.turboplus.model.Veiculo;

import java.util.List;

public interface VeiculoRepository
        extends JpaRepository<Veiculo, Long> {

    List<Veiculo> findByClienteId(Long clienteId);
}
