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
    User owner;

    String name;

    byte[] image;

    public Image(User owner, String name, byte[] image) {
        this.owner = owner;
        this.name = name;
        this.image = image;
    }
}
