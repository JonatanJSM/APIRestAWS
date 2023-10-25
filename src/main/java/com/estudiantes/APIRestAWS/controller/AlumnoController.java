package com.estudiantes.APIRestAWS.controller;

import com.estudiantes.APIRestAWS.dto.AlumnoDTO;
import com.estudiantes.APIRestAWS.services.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
@CrossOrigin(origins = {"*"})
public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService){
        this.alumnoService = alumnoService;
    }

    @GetMapping("")
    public ResponseEntity<List<AlumnoDTO>> getAllCurrencies() {
        List<AlumnoDTO> alumnos = this.alumnoService.getAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }
}
