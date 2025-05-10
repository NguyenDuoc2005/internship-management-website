package intern.server.core.manage.intern.repository;

import intern.server.entity.Role;
import intern.server.repository.RoleRepository;
import jakarta.validation.constraints.Size;

public interface MARoleRepository extends RoleRepository {
    Role findByCode(@Size(max = 255) String code);
}
