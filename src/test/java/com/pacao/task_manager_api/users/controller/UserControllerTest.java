package com.pacao.task_manager_api.users.controller;

import com.pacao.task_manager_api.exception.UserNotFoundException;
import com.pacao.task_manager_api.users.dto.UserResponse;
import com.pacao.task_manager_api.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*@WebMvcTest carga la parte necesaria de Spring MVC para probar el controlador
Crea un contexto de Spring enfocado en la capa web donde spring intenta crear una
instancia real de UserControllere y portanto inyectarle sus dependencia que
en este contexto deben de ser beans por eso se usa @mockitobean
Carga los componentes MVC necesarios para probar el controlador y puede incluir
las clases anotadas con @ControllerAdvice o @RestControllerAdvice.
* */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /*
    ¿Porque utilizar @MockitoBean y no @Mock para simular el service?
    Con @Mock:
    Spring NO podria inyectar la dependencia de UserService porque esta anotacion crea un
    objeto simulado administrado por Mockito, pero no lo registra automáticamente como bean
    en el contexto de Spring por lo que no encontraria el objeto UserService

    Con @MockitoBean:
    Spring SI puede inyectar la dependcia de UserService porque esta anotacion crea un
    objeto simulado tambien administrado por Mockito pero lo registra como bean en el contexto
    de Spring.
    De esta manera, cuando Spring crea UserController, puede inyectarle el servicio simulado.
* */

    @MockitoBean
    private UserService service;


    /*
    Que valida este metodo:
    * Comprueba que el controlador convierta correctamente la respuesta del servicio en una respuesta
    * HTTP con el estado y los campos JSON esperados.
    * */
    @Test
    void shouldReturnAllUsers() throws Exception {

        // ARRANGE
        UserResponse user = new UserResponse(
                1,
                "Francisco",
                "paco@gmail.com",
                List.of()
        );

        when(service.getUsers())
                .thenReturn(List.of(user));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre")
                        .value("Francisco"))
                .andExpect(jsonPath("$[0].email")
                        .value("paco@gmail.com"));
    }


    /*Que valida este metodo:
     Comprueba que apesar de no haber registros en la BD, el controller
     es capaz de devolver una respuesta HTTP con un estado 200 y un
     JSON vacio
    * */
    @Test
    void shouldReturnEmptyList() throws Exception{

        //ARRANGE

        when(service.getUsers())
                .thenReturn(List.of());



        //ACT & ASSERT
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void shouldReturnUserById() throws Exception{
        /*
        * El when condiciona el metodo del service para devolver el usuario "user" únicamente cuando recibe
        * el argumento 1, que es el valor que se le pasa desde el endpoint en perfom
        * */

        //ARRANGE
        UserResponse user = UserResponse.builder()
                .id(1)
                .nombre("Paco")
                .email("paco@gmail.com")
                .build();


        when(service.getUserById(1))
                .thenReturn(user);



        //ACT & ASSERT
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("nombre").value("Paco"));

    }


    @Test
    void shouldReturnErrorWhenUserByIdNotFound() throws Exception{

        //ARRAMGE

        when(service.getUserById(1))
                .thenThrow(new UserNotFoundException(
                        "Usuario con Id: 1 no encontrado"
                ));

        //ACT & ASSERT
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("name").value("User error"))
                .andExpect(jsonPath("message").value("Usuario con Id: 1 no encontrado"));

        verify(service).getUserById(1);


    }









}
