package com.pacao.task_manager_api.task.entity;

import com.pacao.task_manager_api.users.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String title;
    String description;
    String statutus;
    @Column(name = "createdat")
    LocalDate createdAt;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userid") //hace referencia al nombre de la columna en la tabla de tasks que funciona fk
    UserEntity user;

}
