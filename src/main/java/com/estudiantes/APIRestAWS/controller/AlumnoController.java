package com.estudiantes.APIRestAWS.controller;

import com.estudiantes.APIRestAWS.dto.AlumnoDTO;
import com.estudiantes.APIRestAWS.dto.request.PreAlumnoInfo;
import com.estudiantes.APIRestAWS.entity.Sesion;
import com.estudiantes.APIRestAWS.services.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    @Operation(summary = "Obtener todos los estudiantes")
    public ResponseEntity<List<AlumnoDTO>> getAllAlumnos() {
        List<AlumnoDTO> alumnos = this.alumnoService.getAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estudiante por id")
    public ResponseEntity<AlumnoDTO> getAlumnoById(@PathVariable int id){
        AlumnoDTO newAlumno = this.alumnoService.getAlumnoById(id);
        if(newAlumno == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newAlumno,HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo estudiante")
    public ResponseEntity<?> createAlumno(@Valid @RequestBody PreAlumnoInfo info){
        AlumnoDTO newAlumno = this.alumnoService.createAlumno(info);
        return new ResponseEntity<>(newAlumno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar estudiante")
    public ResponseEntity<AlumnoDTO> editAlumnoByid(@Valid @RequestBody PreAlumnoInfo info, @PathVariable int id){
        AlumnoDTO newAlumno = this.alumnoService.actualizar(id,info);
        return new ResponseEntity<>(newAlumno,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "eliminar estudiante")
    public ResponseEntity<AlumnoDTO> delete(@PathVariable int id){
        AlumnoDTO alumnoAEliminar = this.alumnoService.deleteAlumno(id);

        if (alumnoAEliminar != null) {
            return new ResponseEntity<>(alumnoAEliminar, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/session/login")
    @Operation(summary = "Crear una nueva sesion")
    public ResponseEntity<?> createSesion(@PathVariable int id, @Valid @RequestBody String password){
        Sesion sesion = this.alumnoService.createSesion(id, password);
        return new ResponseEntity<>(sesion, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/session/verify")
    @Operation(summary = "Crear una nueva sesion")
    public ResponseEntity<?> verifySesion(@PathVariable int id, @Valid @RequestBody String sessionString){
        Sesion sesion = this.alumnoService.verifySesion(sessionString);
        return new ResponseEntity<>(sesion, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/session/logout")
    @Operation(summary = "Crear una nueva sesion")
    public ResponseEntity<?> logut(@PathVariable int id, @Valid @RequestBody String sessionString){
        Sesion sesion = this.alumnoService.logOut(sessionString);
        return new ResponseEntity<>(sesion, HttpStatus.CREATED);
    }

}
