package co.personal.academia.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import co.personal.academia.app.model.Estudiante;
import co.personal.academia.app.repo.IEstudianteRepo;
import co.personal.academia.app.repo.IGenericRepo;
import co.personal.academia.app.service.IEstudianteService;
import reactor.core.publisher.Flux;

public class EstudianteServiceImpl extends CRUDImpl<Estudiante, String> implements IEstudianteService {

    @Autowired
    private IEstudianteRepo repo;

    @Override
    protected IGenericRepo<Estudiante, String> getRepo() {
        return repo;
    }

    @Override
    public Flux<Estudiante> listarOrdenado() {
        return repo.findByOrderByEdadDesc();
    }

}
