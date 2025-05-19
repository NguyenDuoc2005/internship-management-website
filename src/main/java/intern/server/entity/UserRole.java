package intern.server.entity;

import jakarta.persistence.*;
import lombok.*;
import intern.server.entity.base.PrimaryEntity;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_roles")
public class UserRole extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
