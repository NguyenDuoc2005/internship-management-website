package intern.server.core.manage.intern.repository;

import intern.server.core.manage.intern.dto.request.MAInternRequest;
import intern.server.core.manage.intern.dto.response.MAInternResponse;
import intern.server.entity.User;
import intern.server.repository.UserRepository;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MAUserRepository extends UserRepository {
    @Query(
            value = """
        SELECT u.id AS id,
               u.username AS userName,
               u.email AS email,
               u.phoneNumber AS phoneNumber,
               u.code AS code,
               u.address AS address,
               u.position AS position,
               u.major AS major,
               u.status AS status,
               u.createdDate AS createdDate
        FROM User u
             JOIN u.userRoles ur
                    JOIN ur.role r
        WHERE
            (:#{#request.internName} IS NULL OR u.username LIKE CONCAT('%', :#{#request.internName}, '%'))
            AND 
            (:#{#request.internStatus} IS NULL OR u.status = :#{#request.internStatus})
            AND r.code ='THANH_VIEN'
    """
    )
    Page<MAInternResponse> getAllUsers(Pageable pageable, MAInternRequest request);

    List<User> findAllByEmail(@Size(max = 255) String email);

    @Query(
            value = """
        SELECT u.id AS id,
               u.username AS userName,
               u.email AS email,
               u.phoneNumber AS phoneNumber,
               u.code AS code,
               u.address AS address,
               u.position AS position,
               u.major AS major,
               u.status AS status,
               u.createdDate AS createdDate
        FROM User u
            where u.id = :id
    """
    )
    Optional<MAInternResponse> getAllUsersById(@Param("id") String id);

    User findUserById(String id);
}
