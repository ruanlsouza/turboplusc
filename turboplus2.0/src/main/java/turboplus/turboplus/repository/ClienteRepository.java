
package turboplus.turboplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import turboplus.turboplus.model.Cliente;

public interface ClienteRepository
        extends JpaRepository<Cliente, Long> {
}
