package ru.mardeev.rusgramm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_inform")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User owner;
    @Column(name = "image_name", nullable = false)
    String name;
    @Column(name = "image_data", nullable = false)
    byte[] image;

    public Image(User owner, String name, byte[] image) {
        this.owner = owner;
        this.name = name;
        this.image = image;
    }
}
