package intern.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import intern.server.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
}
