package com.pacao.task_manager_api.users.entity;

import com.pacao.task_manager_api.task.entity.TaskEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String nombre;
    String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<TaskEntity> tasks;
}
