package com.pacao.task_manager_api.users.repository;

import com.pacao.task_manager_api.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
}
