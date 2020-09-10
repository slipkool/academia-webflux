package co.personal.academia.app.repo;

import co.personal.academia.app.model.Estudiante;
import reactor.core.publisher.Flux;

public interface IEstudianteRepo extends IGenericRepo<Estudiante, String> {

    Flux<Estudiante> findByOrderByEdadDesc();
}
