package co.personal.academia.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import co.personal.academia.app.model.Curso;
import co.personal.academia.app.repo.ICursoRepo;
import co.personal.academia.app.repo.IGenericRepo;
import co.personal.academia.app.service.ICursoService;

public class CursoServiceImpl extends CRUDImpl<Curso, String> implements ICursoService {

    @Autowired
    private ICursoRepo repo;

    @Override
    protected IGenericRepo<Curso, String> getRepo() {
        return repo;
    }

}
