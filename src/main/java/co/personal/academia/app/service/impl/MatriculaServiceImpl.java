package co.personal.academia.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import co.personal.academia.app.model.Matricula;
import co.personal.academia.app.repo.IGenericRepo;
import co.personal.academia.app.repo.IMatriculaRepo;
import co.personal.academia.app.service.IMatriculaService;

public class MatriculaServiceImpl extends CRUDImpl<Matricula, String> implements IMatriculaService {

    @Autowired
    private IMatriculaRepo repo;

    @Override
    protected IGenericRepo<Matricula, String> getRepo() {
        return repo;
    }

}
