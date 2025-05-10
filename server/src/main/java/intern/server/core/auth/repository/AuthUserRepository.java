package intern.server.core.auth.repository;

import intern.server.entity.User;
import intern.server.repository.UserRepository;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public interface AuthUserRepository extends UserRepository {
    Optional<User> findByEmail(@Size(max = 255) String email);
}
