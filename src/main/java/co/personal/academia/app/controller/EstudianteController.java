package co.personal.academia.app.controller;

import java.net.URI;

import javax.validation.Valid;

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

import co.personal.academia.app.model.Estudiante;
import co.personal.academia.app.service.IEstudianteService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private IEstudianteService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Estudiante>>> listar() {
        Flux<Estudiante> fxEstudiantes = service.listar();

        return Mono.just(ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fxEstudiantes)
                );
    }

    @GetMapping("/ordenado")
    public Mono<ResponseEntity<Flux<Estudiante>>> listarOrdenado() {
        Flux<Estudiante> fxEstudiantes = service.listarOrdenado();

        return Mono.just(ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fxEstudiantes)
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Estudiante>> listarPorId(@PathVariable("id") String id) {
        return service.listarPorId(id)
                .map(est -> ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(est)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Estudiante>> registrar(@Valid @RequestBody Estudiante estudiante, final ServerHttpRequest request) {
        return service.registrar(estudiante)
                .map(e -> ResponseEntity.created(URI.create(request.getURI().toString().concat(estudiante.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Estudiante>> actualizar(@PathVariable String id, @Valid @RequestBody Estudiante estudiante) {
        Mono<Estudiante> estudianteBd = service.listarPorId(id);
        Mono<Estudiante> estudianteParam = Mono.just(estudiante);

        return estudianteBd.zipWith(estudianteParam, (bd, est) -> {
            bd.setNombres(est.getNombres());
            bd.setApellidos(est.getApellidos());
            bd.setDni(est.getDni());
            bd.setEdad(est.getEdad());
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
