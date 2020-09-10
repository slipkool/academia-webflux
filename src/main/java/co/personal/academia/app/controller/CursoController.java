package co.personal.academia.app.controller;

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

import co.personal.academia.app.model.Curso;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @GetMapping
    public Mono<ResponseEntity<Flux<Curso>>> listar() {
        Flux<Curso> fxcursos = Flux.empty();

        return Mono.just(ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fxcursos)
                );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Curso>> listarPorId(@PathVariable("id") String id) {
        return Mono.empty();
    }

    @PostMapping
    public Mono<ResponseEntity<Curso>> registrar(@RequestBody Curso estudiando, final ServerHttpRequest request) {
        return Mono.empty();
    }

    @PutMapping
    public Mono<ResponseEntity<Curso>> actualizar(@RequestBody Curso estudiando) {
        return Mono.empty();
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {
        return Mono.empty();
    }
}
