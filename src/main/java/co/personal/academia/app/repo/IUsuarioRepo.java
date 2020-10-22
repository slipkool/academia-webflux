package co.personal.academia.app.repo;

import co.personal.academia.app.model.Usuario;
import reactor.core.publisher.Mono;

public interface IUsuarioRepo extends IGenericRepo<Usuario, String> {

    Mono<Usuario> findOneByUsuario(String usuario);
}
