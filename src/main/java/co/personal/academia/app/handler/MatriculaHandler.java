package co.personal.academia.app.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.personal.academia.app.model.Matricula;
import co.personal.academia.app.service.IMatriculaService;
import co.personal.academia.app.validator.RequestValidator;
import reactor.core.publisher.Mono;

@Component
public class MatriculaHandler {

    @Autowired
    private IMatriculaService service;

    @Autowired
    private RequestValidator validadorGeneral;

    public Mono<ServerResponse> listar(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.listar(), Matricula.class);
    }

    public Mono<ServerResponse> listarPorId(ServerRequest req) {
        String id = req.pathVariable("id");
        return service.listarPorId(id)
                .flatMap(mat -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(fromValue(mat)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> registrar(ServerRequest req) {
        Mono<Matricula> monoMatricula = req.bodyToMono(Matricula.class);
        return monoMatricula
                .flatMap(validadorGeneral::validate)
                .flatMap(service::registrar)
                .flatMap(mat -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(mat.getId())))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(fromValue(mat)));
    }

    public Mono<ServerResponse> modificar(ServerRequest req) {
        Mono<Matricula> monoMatricula = req.bodyToMono(Matricula.class);
        Mono<Matricula> monoMatriculaBd = service.listarPorId(req.pathVariable("id"));

        return monoMatriculaBd.zipWith(monoMatricula, (matBd, mat) -> {
                    matBd.setCursos(mat.getCursos());
                    matBd.setEstudiante(mat.getEstudiante());
                    matBd.setFechaMatricula(mat.getFechaMatricula());
                    matBd.setEstado(mat.isEstado());
                    return matBd;
                })
                .flatMap(validadorGeneral::validate)
                .flatMap(service::modificar)
                .flatMap(mat -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(fromValue(mat))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> eliminar(ServerRequest req) {
        String id = req.pathVariable("id");
        return service.listarPorId(id)
                .flatMap(mat -> service.eliminar(mat.getId())
                                .then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
