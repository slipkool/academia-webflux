package co.personal.academia.app.repo;

import org.springframework.data.mongodb.repository.Query;

import co.personal.academia.app.model.Estudiante;
import reactor.core.publisher.Flux;

public interface IEstudianteRepo extends IGenericRepo<Estudiante, String> {

    Flux<Estudiante> findByOrderByEdadDesc();

    @Query(value = "{}", sort = "{ edad : 1 }")
    Flux<Estudiante> listarOrdenado2();

}
