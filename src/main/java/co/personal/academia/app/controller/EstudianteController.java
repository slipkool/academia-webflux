package co.personal.academia.app.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
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
        Flux<Estudiante> fxEstudiantes = Flux.empty();

        return Mono.just(ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fxEstudiantes)
                );
    }

    @GetMapping("/ordenado")
    public Mono<ResponseEntity<Flux<Estudiante>>> listarOrdenado() {
        Flux<Estudiante> fxEstudiantes = Flux.empty();

        return Mono.just(ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fxEstudiantes)
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Estudiante>> listarPorId(@PathVariable("id") String id) {
        return Mono.empty();
    }

    @PostMapping
    public Mono<ResponseEntity<Estudiante>> registrar(@RequestBody Estudiante estudiante, final ServerHttpRequest request) {
        return service.registrar(estudiante)
                .map(e -> ResponseEntity.created(URI.create(request.getURI().toString().concat(estudiante.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e));
    }

    @PutMapping
    public Mono<ResponseEntity<Estudiante>> actualizar(@RequestBody Estudiante estudiando) {
        return Mono.empty();
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {
        return Mono.empty();
    }
}
