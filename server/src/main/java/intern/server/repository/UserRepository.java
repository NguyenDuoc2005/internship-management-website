package intern.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import intern.server.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
