package ru.mardeev.rusgramm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_inform")
public class User {
    @Id
    @Column(name = "user_id")
    String name;
    @Column(name = "user_password", nullable = false)
    String password;
    @Column(name = "user_role", nullable = false, columnDefinition = "USER")
    String role;


}
