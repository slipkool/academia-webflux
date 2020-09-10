package co.personal.academia.app.service;

import co.personal.academia.app.model.Estudiante;
import reactor.core.publisher.Flux;

public interface IEstudianteService extends ICRUD<Estudiante, String> {

    Flux<Estudiante> listarOrdenado();
}
