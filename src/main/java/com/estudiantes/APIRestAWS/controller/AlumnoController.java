package com.estudiantes.APIRestAWS.controller;

import com.estudiantes.APIRestAWS.dto.AlumnoDTO;
import com.estudiantes.APIRestAWS.dto.request.PreAlumnoInfo;
import com.estudiantes.APIRestAWS.services.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<List<AlumnoDTO>> getAllAlumnos() {
        List<AlumnoDTO> alumnos = this.alumnoService.getAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> getAlumnoById(@PathVariable int id){
        AlumnoDTO newAlumno = this.alumnoService.getAlumnoById(id);
        return new ResponseEntity<>(newAlumno,HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<AlumnoDTO> createAlumno(@RequestBody PreAlumnoInfo info){
        AlumnoDTO newAlumno = this.alumnoService.createAlumno(info);
        return new ResponseEntity<>(newAlumno,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDTO> editAlumnoByid(@RequestBody PreAlumnoInfo info, @PathVariable int id){
        AlumnoDTO newAlumno = this.alumnoService.actualizar(id,info);
        return new ResponseEntity<>(newAlumno,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlumnoDTO> delete(@PathVariable int id){
        AlumnoDTO newAlumno = this.alumnoService.deleteAlumno(id);
        return new ResponseEntity<>(newAlumno,HttpStatus.OK);
    }

}
