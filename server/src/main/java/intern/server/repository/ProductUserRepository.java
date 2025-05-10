package intern.server.repository;

import intern.server.entity.ProductsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserRepository extends JpaRepository<ProductsUser,String> {

}
