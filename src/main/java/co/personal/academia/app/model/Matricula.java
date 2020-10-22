package co.personal.academia.app.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "matriculas")
public class Matricula {

    @Id
    private String id;

    @DateTimeFormat(pattern="dd/MM/yyyy")
    private LocalDate fechaMatricula;

    @NotNull(message = "La lista de estudiantes no debe estar vacia")
    private Estudiante estudiante;

    @NotEmpty(message = "La lista de cursos no debe estar vacia")
    private List<MatriculaCurso> cursos;

    private boolean estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
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
