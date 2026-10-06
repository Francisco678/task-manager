package com.pacao.task_manager_api.users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    Integer id;
    String nombre;
    String email;
}
