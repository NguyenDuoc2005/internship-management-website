package intern.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import intern.server.entity.base.PrimaryEntity;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "products_user")
public class ProductsUser extends PrimaryEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product idProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User idUser;

    @Size(max = 255)
    @Column(name = "role_in_product")
    private String roleInProduct;

}