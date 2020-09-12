package co.personal.academia.app.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estudiantes")
public class Estudiante {

    @Id
    private String id;

    @NotNull(message = "El nombre debe estar presente")
    @Size(min=2, max=30, message = "El nombre del estudiante '${validatedValue}' debe estar entre {min} y {max} numero de caracteres")
    private String nombres;

    @NotNull(message = "El apellido debe estar presente")
    @Size(min=2, max=30, message = "El apellido del estudiante '${validatedValue}' debe estar entre {min} y {max} numero de caracteres")
    private String apellidos;

    @NotNull(message = "El DNI debe estar presente")
    @Size(min=2, max=30, message = "El DNI del estudiante '${validatedValue}' debe estar entre {min} y {max} numero de caracteres")
    private String dni;

    private int edad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

}
