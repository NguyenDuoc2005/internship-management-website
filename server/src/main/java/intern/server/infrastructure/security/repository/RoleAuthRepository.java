package intern.server.infrastructure.security.repository;

import intern.server.entity.Role;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import intern.server.repository.RoleRepository;

import java.util.List;

public interface RoleAuthRepository extends RoleRepository {


    @Query(
            value = """

                    SELECT DISTINCT
                          r.code
                      FROM
                          Role r
                      LEFT JOIN
                       UserRole sr ON r.id = sr.role.id
                      WHERE
                          sr.user.id = :id
                    """
    )
    List<String> findRoleByUserId(@Param("id") String id);

    @Query(
            value = """

                    SELECT DISTINCT
                          r.name
                      FROM
                          Role r
                      LEFT JOIN
                       UserRole sr ON r.id = sr.role.id
                      WHERE
                          sr.user.id = :id
                    """
    )
    List<String> findRoleNameByUserId(@Param("id") String id);

    Role findRoleByName(@Size(max = 255) String name);

    Role findRoleByCode(@Size(max = 255) String code);
}
