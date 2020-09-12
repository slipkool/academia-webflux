package co.personal.academia.app.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cursos")
public class Curso {

    @Id
    private String id;

    @NotNull(message = "El nombre debe estar presente")
    @Size(min=2, max=30, message = "El nombre del curso '${validatedValue}' debe estar entre {min} y {max} numero de caracteres")
    private String nombre;

    @NotNull(message = "La sigla debe estar presente")
    @Size(min=2, max=5, message = "La sigla '${validatedValue}' debe estar entre {min} y {max} numero de caracteres")
    private String siglas;

    private boolean estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
