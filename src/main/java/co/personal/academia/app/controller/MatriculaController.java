package co.personal.academia.app.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.personal.academia.app.model.Matricula;
import co.personal.academia.app.service.IMatriculaService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private IMatriculaService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Matricula>>> listar() {
        Flux<Matricula> fxMatriculas = service.listar();

        return Mono.just(ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fxMatriculas)
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Matricula>> listarPorId(@PathVariable("id") String id) {
        return service.listarPorId(id)
                .map(mat -> ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(mat)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Matricula>> registrar(@RequestBody Matricula matricula, final ServerHttpRequest request) {
        return service.registrar(matricula)
                .map(m -> ResponseEntity.created(URI.create(request.getURI().toString().concat(m.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(m));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Matricula>> actualizar(@PathVariable String id, @RequestBody Matricula matricula) {
        Mono<Matricula> matriculaBd = service.listarPorId(id);
        Mono<Matricula> matriculaParam = Mono.just(matricula);

        return matriculaBd.zipWith(matriculaParam, (bd, mat) -> {
            bd.setCursos(mat.getCursos());
            bd.setEstudiante(mat.getEstudiante());
            bd.setFechaMatricula(mat.getFechaMatricula());
            bd.setEstado(mat.isEstado());
            return bd;
        })
        .flatMap(service::modificar)
        .map(est -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(est)
        )
        .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {
        return service.listarPorId(id)
                .flatMap(p -> {
                    return service.eliminar(p.getId())
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                })
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
