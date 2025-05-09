package intern.server.infrastructure.listener;

import jakarta.persistence.PrePersist;
import intern.server.entity.base.PrimaryEntity;
import intern.server.infrastructure.constant.EntityStatus;

import java.util.UUID;

public class CreatePrimaryEntityListener {

    @PrePersist
    private void onCreate(PrimaryEntity entity) {
        entity.setId(UUID.randomUUID().toString());
        entity.setStatus(EntityStatus.ACTIVE);
    }

}
