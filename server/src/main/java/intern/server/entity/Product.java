package intern.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import intern.server.entity.base.PrimaryEntity;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends PrimaryEntity implements Serializable {

    @Column(name = "progress")
    private Float progress;

    @Column(name = "end_time")
    private Long endTime;

    @Column(name = "start_time")
    private Long startTime;

    @Size(max = 5000)
    @Column(name = "description", length = 5000)
    private String description;

    @Size(max = 255)
    @Column(name = "background_color")
    private String backgroundColor;

    @Size(max = 255)
    @Column(name = "background_image")
    private String backgroundImage;

    @Size(max = 255)
    @Column(name = "code")
    private String code;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

}