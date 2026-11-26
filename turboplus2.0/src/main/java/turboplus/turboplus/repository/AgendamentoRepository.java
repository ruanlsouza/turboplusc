
package turboplus.turboplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import turboplus.turboplus.model.Agendamento;

import java.util.List;

public interface AgendamentoRepository
        extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByClienteId(Long clienteId);
}
