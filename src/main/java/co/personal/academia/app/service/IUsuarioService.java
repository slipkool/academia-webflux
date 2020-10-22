package co.personal.academia.app.service;

import co.personal.academia.app.model.Usuario;
import co.personal.academia.app.security.User;
import reactor.core.publisher.Mono;

public interface IUsuarioService extends ICRUD<Usuario, String> {

    Mono<User> buscarPorUsuario(String usuario);
}
