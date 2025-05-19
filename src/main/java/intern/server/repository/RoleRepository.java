package intern.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import intern.server.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
