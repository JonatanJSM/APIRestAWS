package com.estudiantes.APIRestAWS.dto.request;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PreAlumnoInfo {
    private int id;
    private String nombres;
    private String apellidos;
    private Long matricula;
    private  double promedio;
}
