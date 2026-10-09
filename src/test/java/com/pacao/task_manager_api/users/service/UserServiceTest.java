package com.pacao.task_manager_api.users.service;

import com.pacao.task_manager_api.task.dto.TaskResponse;
import com.pacao.task_manager_api.task.entity.TaskEntity;
import com.pacao.task_manager_api.users.dto.UserResponse;
import com.pacao.task_manager_api.users.entity.UserEntity;
import com.pacao.task_manager_api.users.mapper.UserMapper;
import com.pacao.task_manager_api.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.config.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/*
*
Esta clase realiza pruebas unitarias porque estamos aislámos el service de su dependencia externa, el repositorio.

¿Cómo sería una prueba de integración?
En una prueba de integración podríamos utilizar:
-Un UserService real.
-Un UserRepository real.
-Una base de datos de prueba, por ejemplo PostgreSQL con Testcontainers.

Así comprobaríamos que Spring Data JPA consulta correctamente la base de datos, recupera las
entidades y el servicio las convierte en respuestas.

* En esta clase se uso JUnit 5 + Mockito
* */

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    /*
    shouldReturnAllUsers() valida que:
    -El servicio obtiene los usuarios del repositorio.
    -Los usuarios se transforman correctamente en UserResponse.
    * */

    @Test
    void shouldReturnAllUsers() {

        // ARRANGE
        UserEntity user1 = UserEntity.builder()
                .id(1)
                .nombre("Francisco")
                .email("francisco@gmail.com")
                .tasks(List.of())
                .build();

        UserEntity user2 = UserEntity.builder()
                .id(2)
                .nombre("Carlos")
                .email("carlos@gmail.com")
                .tasks(List.of())
                .build();

        when(repository.findAll())
                .thenReturn(List.of(user1, user2));

        // ACT
        List<UserResponse> result = service.getUsers();

        // ASSERT
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).id());
        assertEquals("Francisco", result.get(0).nombre());
        assertEquals("francisco@gmail.com", result.get(0).email());

        assertEquals(2, result.get(1).id());
        assertEquals("Carlos", result.get(1).nombre());

        verify(repository, times(1)).findAll();
    }

    /*
      shouldReturnEmptyList() valida que:
      Cuando el repositorio devuelve una lista vacía de entidades (UserEntity), el método getUsers() debe
      devolver una lista vacía de respuestas (UserResponse) sin lanzar ninguna excepción.
    */
    @Test
    void shouldReturnEmptyList(){

        /*
            El metodo getUsers del service devulve una lista vacia porque el repositorio
            devulve una lista vacia por lo que no se ejecuta el mapper ya que no hay
            elementos que transformar

            Aunque el mapper no se ejecute, el operador map() sigue definiendo que el resultado del
            Stream será de tipo UserResponse, el operador map() cambia el tipo del Stream.

            ¿Cómo sabe Java qué tipo devolver si no ejecuta el mapper?
            Porque Java determina los tipos durante la compilación, no necesita ejecutar el método para
            conocer su tipo de retorno.
        * */

        when(repository.findAll())
                .thenReturn(List.of());


        List<UserResponse> result = service.getUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository,times(1)).findAll();
    }

    /*
    shouldReturnUsersWithTasks() valida que:
    -El repositorio devuelve el usuario con sus dos tareas.
    -El mapper convierte las tareas en sus respectivos DTO.
    -La respuesta conserva los datos correctos de sus tareas.
    * */
    @Test
    void shouldReturnUsersWithTasks(){

        UserEntity user = new UserEntity(
                1,
                "Francisco",
                "paco@gmail.com",
                new ArrayList<TaskEntity>()
        );


        TaskEntity task1 = new TaskEntity(
                1,
                "Estudiar",
                "2 horas",
                "Pendiente",
                LocalDate.of(2026, 10, 8),
                user
        );

        TaskEntity task2 = new TaskEntity(
                2,
                "Jugar",
                "En Xbox Halo Infinite",
                "Hecha",
                LocalDate.of(2026, 5, 2),
                user
        );

        user.getTasks().add(task1);
        user.getTasks().add(task2);

        when(repository.findAll())
                .thenReturn(List.of(user));

        List<UserResponse> result = service.getUsers();

        assertEquals(2,result.get(0).task().size());

        assertEquals(1,result.get(0).task().get(0).id());
        assertEquals("Estudiar",result.get(0).task().get(0).title());

        assertEquals(2,result.get(0).task().get(1).id());
        assertEquals("Jugar",result.get(0).task().get(1).title());


        verify(repository,times(1)).findAll();

    }


}

