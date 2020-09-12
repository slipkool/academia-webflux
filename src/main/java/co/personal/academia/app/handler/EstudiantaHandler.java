package co.personal.academia.app.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.personal.academia.app.model.Estudiante;
import co.personal.academia.app.service.IEstudianteService;
import co.personal.academia.app.validator.RequestValidator;
import reactor.core.publisher.Mono;

@Component
public class EstudiantaHandler {

    @Autowired
    private IEstudianteService service;

    @Autowired
    private RequestValidator validadorGeneral;

    public Mono<ServerResponse> listar(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.listar(), Estudiante.class);
    }

    public Mono<ServerResponse> listarOrdenado(ServerRequest req) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.listarOrdenado(), Estudiante.class);
    }

    public Mono<ServerResponse> listarPorId(ServerRequest req) {
        String id = req.pathVariable("id");
        return service.listarPorId(id)
                .flatMap(est -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(fromValue(est)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> registrar(ServerRequest req) {
        Mono<Estudiante> monoEstudiante = req.bodyToMono(Estudiante.class);
        return monoEstudiante
                .flatMap(validadorGeneral::validate)
                .flatMap(service::registrar)
                .flatMap(est -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(est.getId())))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(fromValue(est)));
    }

    public Mono<ServerResponse> modificar(ServerRequest req) {
        Mono<Estudiante> monoEstudiante = req.bodyToMono(Estudiante.class);
        Mono<Estudiante> monoEstudianteBd = service.listarPorId(req.pathVariable("id"));

        return monoEstudianteBd.zipWith(monoEstudiante, (estBd, est) -> {
                    estBd.setNombres(est.getNombres());
                    estBd.setApellidos(est.getApellidos());
                    estBd.setDni(est.getDni());
                    estBd.setEdad(est.getEdad());
                    return estBd;
                })
                .flatMap(validadorGeneral::validate)
                .flatMap(service::modificar)
                .flatMap(est -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(fromValue(est))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> eliminar(ServerRequest req) {
        String id = req.pathVariable("id");
        return service.listarPorId(id)
                .flatMap(est -> service.eliminar(est.getId())
                                .then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
