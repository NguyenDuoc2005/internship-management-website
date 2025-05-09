package intern.server.infrastructure.security.repository;

import intern.server.entity.RefreshToken;
import intern.server.repository.RefreshTokenRepository;

import java.util.Optional;

public interface RefreshTokenAuthRepository extends RefreshTokenRepository {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Optional<RefreshToken> findByUserId(String userId);
}
