package intern.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import intern.server.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
