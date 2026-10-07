package com.pacao.task_manager_api.task.entity;

import com.pacao.task_manager_api.users.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TaskEntity {
    @Id
    Integer id;
    String title;
    String description;
    String statutus;
    @Column(name = "createdat")
    String createdAt;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userid") //hace referencia al nombre de la columna en la tabla de tasks que funciona fk
    UserEntity user;

}
