package intern.server.infrastructure.security.repository;

import jakarta.validation.constraints.Size;
import intern.server.entity.User;
import intern.server.infrastructure.constant.EntityStatus;
import intern.server.repository.UserRepository;

import java.util.Optional;

public interface UserAuthRepository extends UserRepository {

    Optional<User> findByEmail(@Size(max = 255) String email);

    Optional<User> findByEmailAndStatus(@Size(max = 255) String email, EntityStatus status);
}
