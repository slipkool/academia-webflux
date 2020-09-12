package co.personal.academia.app.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.personal.academia.app.model.Curso;
import co.personal.academia.app.service.ICursoService;
import co.personal.academia.app.validator.RequestValidator;
import reactor.core.publisher.Mono;

@Component
public class CursoHandler {

    @Autowired
    private ICursoService service;

    @Autowired
    private RequestValidator validadorGeneral;

    public Mono<ServerResponse> listar(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.listar(), Curso.class);
    }

    public Mono<ServerResponse> listarPorId(ServerRequest req) {
        String id = req.pathVariable("id");
        return service.listarPorId(id)
                .flatMap(cur -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(fromValue(cur)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> registrar(ServerRequest req) {
        Mono<Curso> monoCurso = req.bodyToMono(Curso.class);
        return monoCurso
                .flatMap(validadorGeneral::validate)
                .flatMap(service::registrar)
                .flatMap(cur -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(cur.getId())))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(fromValue(cur)));
    }

    public Mono<ServerResponse> modificar(ServerRequest req) {
        Mono<Curso> monoCurso = req.bodyToMono(Curso.class);
        Mono<Curso> monoCursoBd = service.listarPorId(req.pathVariable("id"));

        return monoCursoBd.zipWith(monoCurso, (curBd, cur) -> {
                    curBd.setNombre(cur.getNombre());
                    curBd.setSiglas(cur.getSiglas());
                    curBd.setEstado(cur.isEstado());
                    return curBd;
                })
                .flatMap(validadorGeneral::validate)
                .flatMap(service::modificar)
                .flatMap(cur -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(fromValue(cur))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> eliminar(ServerRequest req) {
        String id = req.pathVariable("id");
        return service.listarPorId(id)
                .flatMap(cur -> service.eliminar(cur.getId())
                                .then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
