package com.estudiantes.APIRestAWS.dto.request;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PreProfesorInfo {
    private int id;
    private int numeroEmpleado;
    private String nombres;
    private String apellidos;
    private  int horasClase;
}
