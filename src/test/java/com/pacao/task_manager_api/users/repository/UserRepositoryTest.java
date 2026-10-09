package com.pacao.task_manager_api.users.repository;

import com.pacao.task_manager_api.users.entity.UserEntity;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/*
Esta clase realiza una prueba de integración, no una prueba unitaria.
La diferencia principal es que ahora vamos a utilizar un repositorio real y
una base de datos PostgreSQL real, pero temporal, creada con Docker mediante
Testcontainers.

En esta clase se valida que UserRepository realmente pueda:
-Guardar un usuario en PostgreSQL.
-Consultar los usuarios almacenados.
-Recuperar correctamente sus propiedades.

* */

//Su función es preparar un entorno de pruebas enfocado en la capa de persistencia con JPA.
@DataJpaTest
//Testcontainers permite utilizar contenedores Docker durante las pruebas automatizadas.
@Testcontainers

/*
Por defecto, @DataJpaTest puede configurar una base de datos embebida para ejecutar las pruebas,
como H2, si está disponible.
Pero nosotros queremos utilizar PostgreSQL.
Entonces "replace" dice,  No reemplaces la configuración de la base de datos que se utilizará
para las pruebas.
De esta manera, Spring puede utilizar la conexión proporcionada por Testcontainers
mediante @ServiceConnection.
 */
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
public class UserRepositoryTest {

    /*
        PostgreSQLContainer,crea un objeto Java que representa la configuración de un
        contenedor PostgreSQL.

        @Container indica a la extensión de Testcontainers para JUnit que debe administrar
        el ciclo de vida del contenedor.
        Como la variable es static, normalmente ocurre lo siguiente:
        1-Antes de ejecutar las pruebas de esta clase, se inicia el contenedor.
        2-Las pruebas utilizan PostgreSQL.
        3-Al finalizar las pruebas de la clase, el contenedor se detiene.

        @ServiceConnection
        Su función es facilitar la conexión entre Spring Boot y el servicio que proporciona el contenedor.
        Normalmente configurarías PostgreSQL en application.properties.
        Sin embargo, cuando Testcontainers crea PostgreSQL, el puerto puede ser asignado dinámicamente.
        Esta notacion permite que Spring Boot obtenga automáticamente la información necesaria del contenedor
        para configurar la conexión.
        Por tanto, no necesitas escribir manualmente la URL, el usuario y la contraseña de ese contenedor.
     */


    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");


    //@Autowired: permite que Spring inyecte el repositorio real.
    @Autowired
    private UserRepository repository;


    @Test
    void shouldRetrieveUsersFromDatabase() {

        // ARRANGE
        UserEntity user = UserEntity.builder()
                .nombre("Francisco")
                .email("francisco@gmail.com")
                .tasks(new ArrayList<>())
                .build();


        /*
           Al crear la entidad de arriba aun no esta registrada en la BD de PostgreSQL.
           El metodo saveAndFlush hace que JPA registre la entidad para persistirla.
           flush() obliga a sincronizar los cambios pendientes con PostgreSQL, ejecutando el INSERT
           correspondiente.

           Diferiencia con save()
           Hibernate, comienza a gestionar la entidad dentro del contexto de persistencia.
           Pero save() no garantiza que el INSERT se ejecute inmediatamente.
           Hibernate puede mantener pendiente la operación hasta que necesite sincronizar
           los cambios con PostgreSQL.
           A menos que la entidad tenga un @GeneratedValue(strategy = GenerationType.IDENTITY)
           para su id en ese caso es automatico
        * */
        repository.saveAndFlush(user);

        // ACT
        List<UserEntity> result = repository.findAll();

        // ASSERT
        assertEquals(1, result.size());

        assertEquals(
                "Francisco",
                result.get(0).getNombre()
        );

        assertEquals(
                "francisco@gmail.com",
                result.get(0).getEmail()
        );
    }


    @Test
    void shouldReturnEmptyWhenUsersNotFound(){

        //ARRANGE
        repository.deleteAll();
        repository.flush();

        //ACT
        List<UserEntity> result = repository.findAll();


        //ASSERT
        assertEquals(0,result.size());
        assertNotNull(result);

    }


    @Test
    void shouldFindUserById(){

        //ARRANGE
        UserEntity user = UserEntity.builder()
                .nombre("Paco")
                .email("paco@gmail.com")
                .build();

        repository.saveAndFlush(user);

        //ACT
        Optional<UserEntity> foundUser = repository.findById(1);

        //ASSERT
        assertEquals(false,foundUser.isEmpty());

        assertEquals(1,foundUser.get().getId());
        assertEquals("Paco",foundUser.get().getNombre());


    }


    @Test
    void shouldSaveUser(){
        //ARRANGE
        UserEntity user = UserEntity.builder()
                .nombre("Paco")
                .email("paco@gmail.com")
                .build();

        //ACT
        UserEntity savedUser = repository.save(user);

        //ASSERT
        assertNotNull(savedUser);
        assertEquals("Paco",savedUser.getNombre());
    }


}
