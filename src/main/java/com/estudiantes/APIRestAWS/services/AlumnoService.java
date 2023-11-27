package com.estudiantes.APIRestAWS.services;

import com.estudiantes.APIRestAWS.dto.AlumnoDTO;
import com.estudiantes.APIRestAWS.dto.request.PreAlumnoInfo;

import com.estudiantes.APIRestAWS.entity.Sesion;
import com.estudiantes.APIRestAWS.exceptions.BusinessException;
import com.estudiantes.APIRestAWS.repositories.AlumnoRepository;
import com.estudiantes.APIRestAWS.repositories.SesionRepository;
import com.estudiantes.APIRestAWS.schemas.AlumnoSchema;
import org.springframework.stereotype.Service;


import java.util.*;
@Service
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final SesionRepository sesionRepository;

    public AlumnoService(AlumnoRepository alumnoRepository, SesionRepository sesionRepository) {
        this.alumnoRepository = alumnoRepository;
        this.sesionRepository = sesionRepository;
    }

    public List<AlumnoDTO> getAlumnos() {
        return alumnoRepository
                .findAll()
                .stream()
                .map(AlumnoDTO::getFromSchema)
                .toList();
    }

    public AlumnoDTO getAlumnoById(int id){
        Optional<AlumnoSchema> alumno = alumnoRepository
                .findById(id);
        return alumno.map(AlumnoDTO::getFromSchema).orElse(null);
    }

    public AlumnoDTO createAlumno(PreAlumnoInfo alumnoAux){
        if(alumnoRepository.findById(alumnoAux.getId()).isPresent()){
            throw BusinessException
                    .builder()
                    .message("Mismo id")
                    .build();
        }

        AlumnoSchema alumnoSchema = new AlumnoSchema();
        alumnoSchema.setNombres(alumnoAux.getNombres());
        alumnoSchema.setApellidos(alumnoAux.getApellidos());
        alumnoSchema.setMatricula(alumnoAux.getMatricula());
        alumnoSchema.setPromedio(alumnoAux.getPromedio());
        alumnoSchema.setPassword(alumnoAux.getPassword());

        AlumnoSchema alumnoGuardado = alumnoRepository.save(alumnoSchema);

        return AlumnoDTO.getFromSchema(alumnoGuardado);
    }

    public AlumnoDTO actualizar(int id, PreAlumnoInfo alumnoAux){
        Optional<AlumnoSchema> alumnoExistente = alumnoRepository.findById(id);
        if (alumnoExistente.isPresent()) {
            AlumnoSchema alumno = alumnoExistente.get();
            alumno.setNombres(alumnoAux.getNombres());
            alumno.setApellidos(alumnoAux.getApellidos());
            alumno.setMatricula(alumnoAux.getMatricula());
            alumno.setPromedio(alumnoAux.getPromedio());

            AlumnoSchema alumnoActualizado = alumnoRepository.save(alumno);

            return AlumnoDTO.getFromSchema(alumnoActualizado);
        } else {
            return null;
        }
    }

    public AlumnoDTO deleteAlumno(int id){
        Optional<AlumnoSchema> alumnoAEliminar = alumnoRepository.findById(id);
        if (alumnoAEliminar.isPresent()) {
            alumnoRepository.deleteById(id);
            System.out.println("Alumno con ID " + id + " eliminado correctamente.");
            return AlumnoDTO.getFromSchema(alumnoAEliminar.get());
        } else {
            System.out.println("No se encontró ningún alumno con el ID: " + id);
            return null;
        }
    }

    public Sesion createSesion(int alumnoId,String password){
        Optional<AlumnoSchema> alumnoOptional = alumnoRepository.findById(alumnoId);
        Sesion sesion = new Sesion();
        if(alumnoOptional.isPresent()) {
            AlumnoSchema alumno = alumnoOptional.get();

            // Compara las contraseñas
            if (password.equals(alumno.getPassword())) {
                String uuid = UUID.randomUUID().toString();
                sesion.setId(uuid);
                sesion.setAlumnoId(alumnoId);  // Asigna el alumno a la sesión si es necesario
                sesion.setActive(true);
                sesion.setFecha(System.currentTimeMillis());
                sesionRepository.save(sesion);
                return sesion;
            } else {
                // Contraseña incorrecta
                throw BusinessException
                        .builder()
                        .message("Contraseña incorrecta")
                        .build();
            }
        } else {
            // No se encontró al alumno con el ID proporcionado
            throw BusinessException
                    .builder()
                    .message("No se encontró al alumno con el ID: " + alumnoId)
                    .build();
        }
    }

    public Sesion verifySesion(String sessionString){
        Sesion sesionAux = sesionRepository.getSessionBySessionString(sessionString);
        if (sesionAux != null && sesionAux.getActive()) {
            return sesionAux;
        }
        return null;
    }

    public Sesion logOut(String sessionString){
        Sesion sesionAux = sesionRepository.getSessionBySessionString(sessionString);
        if (sesionAux != null && sesionAux.getActive()) {
            sesionAux.setActive(false);
            sesionRepository.save(sesionAux);
            return sesionAux;
        }
        return null;
    }

}
