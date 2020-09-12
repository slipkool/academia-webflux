package co.personal.academia.app.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "matriculas")
public class Matricula {

    @Id
    private String id;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaMatricula;

    @NotEmpty
    private Estudiante estudiante;

    @NotEmpty
    private List<MatriculaCurso> cursos;

    private boolean estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDateTime fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<MatriculaCurso> getCursos() {
        return cursos;
    }

    public void setCursos(List<MatriculaCurso> cursos) {
        this.cursos = cursos;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
