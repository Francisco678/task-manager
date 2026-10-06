package com.pacao.task_manager_api.users.entity;

import com.pacao.task_manager_api.task.entity.TaskEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    Integer id;
    String nombre;
    String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<TaskEntity> tasks;
}
