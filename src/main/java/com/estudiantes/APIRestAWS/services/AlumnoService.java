package com.estudiantes.APIRestAWS.services;

import com.estudiantes.APIRestAWS.dto.AlumnoDTO;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class AlumnoService {
    private List<AlumnoDTO> alumnos = new ArrayList<>();

    public AlumnoService() {
        this.alumnos = new ArrayList<>();
        // Puedes inicializar la lista con algunos valores predeterminados si lo deseas
         this.alumnos.add(new AlumnoDTO(1,"Nombre1", "apellido", 12234, 90.0));
         this.alumnos.add(new AlumnoDTO(2,"Nombre1", "apellido", 12234, 90.0));
    }

    public List<AlumnoDTO> getAlumnos() {
        return alumnos;
    }

}
