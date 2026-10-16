package com.pacao.task_manager_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/*
* @SpringBootTest le indica a Spring Boot que debe levantar el contexto de
* la aplicación para realizar la prueba.
*
* Esto implica que Spring Boot intentará configurar los componentes necesarios
* para iniciar tu aplicación, entre ellos:
	-Controladores (@RestController).
	-Servicios (@Service).
	-Repositorios (@Repository).
	-Configuraciones de Spring.
	-Conexión con la base de datos, cuando corresponde.

 Asi que TaskManagerApiApplicationTests necesita la BD para iniciar el contexto.


  	contextLoads() no es un metodo especial que realice algo, solo es una prueba vacia
  * que no ejecuta nada pero el mero hecho de que se pueda ejecutar confirma que el
  * contexto de spring se incio correctamente

* */


@SpringBootTest
@Testcontainers
class TaskManagerApiApplicationTests {


	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres =
			new PostgreSQLContainer<>("postgres:16");

	@Test
	void contextLoads() {
	}

}
