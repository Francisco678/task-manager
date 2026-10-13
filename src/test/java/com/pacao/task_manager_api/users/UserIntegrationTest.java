package com.pacao.task_manager_api.users;

import com.pacao.task_manager_api.users.entity.UserEntity;
import com.pacao.task_manager_api.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.containsInAnyOrder;




/*
Spring Boot carga el contexto completo de la aplicación. Esto incluye los componentes reales:
UserController, UserService, UserRepository, Configuración de JPA y Hibernate.
* */
@SpringBootTest

/*
* ¿Porque se utiliza @AutoConfigureMockMvc para usar MockMvc en esta clase y no se usa en
 la clase UserControllerTest?
 Porque en la clase controller se uso @WebMvcTest que crea un contexto de Spring enfocado
 en la capa web y configura automáticamente MockMvc para realizar peticiones HTTP simuladas.

 En esta clase se usa @SpringBootTest que si necesita configurar un objeto MockMvc para que
 puedas realizar peticiones HTTP simuladas.
 */
@AutoConfigureMockMvc
@Testcontainers
public class UserIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;



    /*
    * Esta prueba supone que tu endpoint devuelve directamente un UserResponse con las
    * propiedades id, nombre y email.
    * */
    @Test
    void shouldReturnUserByIdFromDatabase() throws Exception {

        // ARRANGE
        deleteDataFromDB();
        UserEntity user = UserEntity.builder()
                .nombre("Paco")
                .email("paco@gmail.com")
                .build();

        UserEntity savedUser = repository.saveAndFlush(user);


        /*
        * El controlador llama al servicio, el servicio utiliza el repositorio y este consulta PostgreSQL mediante JPA.*/
        // ACT & ASSERT
        mockMvc.perform(
                        get("/api/v1/users/{id}", savedUser.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(savedUser.getId()))
                .andExpect(jsonPath("$.nombre")
                        .value("Paco"))
                .andExpect(jsonPath("$.email")
                        .value("paco@gmail.com"));

    }


    @Test
    void shouldReturnAllUsersFromDatabase() throws Exception{

        //ARRANGE
        deleteDataFromDB();

        UserEntity user1 = UserEntity.builder()
                .nombre("Francisco")
                .email("fran@gmail.com")
                .build();

        UserEntity user2 = UserEntity.builder()
                .nombre("Juan")
                .email("juan@gmail.com")
                .build();

        UserEntity user1Saved=repository.saveAndFlush(user1);
        UserEntity user2Saved=repository.saveAndFlush(user2);


        //ACT & ASSERT
        /*
        * En este caso la consulta que ejecuta el repositorio atraves de Hibernate
        * no implementa un ORDER BY por lo que no garantiza el orden de los resultados.
        * Si tu endpoint no establece un orden, es preferible comprobar el contenido sin
        * depender de las posiciones.
        * Con la sintaxis que se implemento para leer el contenido deL JSON se
        * comprueba que existen ambos IDs y ambos nombres, independientemente de su posición.
        * */

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[*].id",
                        containsInAnyOrder(
                                user1Saved.getId(),
                                user2Saved.getId()
                        )))
                .andExpect(jsonPath("$[*].nombre",
                        containsInAnyOrder(
                                "Francisco",
                                "Juan"
                        )));


    }


    @Test
    void shouldReturn404WhenUserNotFound() throws Exception{
        //ARRANGE
        deleteDataFromDB();

        //ACT & ASSET
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("name").value("User error"))
                .andExpect(jsonPath("message").value("Usuario con Id: 1 no encontrado"));
    }



    void deleteDataFromDB(){
        repository.deleteAll();
        repository.flush();
    }


}
