package co.personal.academia.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.personal.academia.app.handler.CursoHandler;
import co.personal.academia.app.handler.EstudiantaHandler;
import co.personal.academia.app.handler.MatriculaHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> rutasEstudiante(EstudiantaHandler handler) {
        return route(GET("/v2/estudiantes"), handler::listar)
                .andRoute(GET("/v2/estudiantes/ordenado"), handler::listarOrdenado)
                .andRoute(GET("/v2/estudiantes/{id}"), handler::listarPorId)
                .andRoute(POST("/v2/estudiantes"), handler::registrar)
                .andRoute(PUT("/v2/estudiantes/{id}"), handler::modificar)
                .andRoute(DELETE("/v2/estudiantes/{id}"), handler::eliminar);
    }

    @Bean
    public RouterFunction<ServerResponse> rutasCurso(CursoHandler handler) {
        return route(GET("/v2/cursos"), handler::listar)
                .andRoute(GET("/v2/cursos/{id}"), handler::listarPorId)
                .andRoute(POST("/v2/cursos"), handler::registrar)
                .andRoute(PUT("/v2/cursos/{id}"), handler::modificar)
                .andRoute(DELETE("/v2/cursos/{id}"), handler::eliminar);
    }

    @Bean
    public RouterFunction<ServerResponse> rutasMatricula(MatriculaHandler handler) {
        return route(GET("/v2/matriculas"), handler::listar)
                .andRoute(GET("/v2/matriculas/{id}"), handler::listarPorId)
                .andRoute(POST("/v2/matriculas"), handler::registrar)
                .andRoute(PUT("/v2/matriculas/{id}"), handler::modificar)
                .andRoute(DELETE("/v2/matriculas/{id}"), handler::eliminar);
    }
}
