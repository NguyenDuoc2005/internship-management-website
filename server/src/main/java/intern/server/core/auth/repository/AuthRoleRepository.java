package intern.server.core.auth.repository;

import intern.server.core.auth.dto.response.RoleResponse;
import intern.server.repository.RoleRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthRoleRepository extends RoleRepository {

    @Query("""
select r.id as id ,
        r.name as name
 from Role r
""")
    List<RoleResponse> getRoles();

}
