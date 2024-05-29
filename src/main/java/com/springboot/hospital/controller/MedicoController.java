package com.springboot.hospital.controller;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.MedicoDTO;
import com.springboot.hospital.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<MedicoDTO>> listarMedicos() {
        List<MedicoDTO> medicos = medicoService.getAllMedicos();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> listarMedicoPOrId(@PathVariable Long id){
        return medicoService.getMedicoById(id)
                .map(medico -> new ResponseEntity<>(medico,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<MedicoDTO> guardarMedico(@RequestBody MedicoDTO medicoDTO){
        MedicoDTO createMedico = medicoService.createMedico(medicoDTO);
        return new ResponseEntity<>(createMedico,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> actualizarMedico(@PathVariable Long id, @RequestBody MedicoDTO medicoDTO){
        MedicoDTO updateMedico = medicoService.updtaeMedito(id, medicoDTO);

        if (updateMedico != null){
            return new ResponseEntity<>(updateMedico,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MedicoDTO> eliminarMedico(@PathVariable Long id){
        medicoService.deleteMedico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/citas")
    public ResponseEntity<Collection<CitaDTO>> listarCitasPorMedico(@PathVariable Long id){
        Collection<CitaDTO> citas = medicoService.getCitasByMedicoId(id);
        if (citas != null){
            return new ResponseEntity<>(citas,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<MedicoDTO>> listarMedicosPOrEspecialidad(@PathVariable String especialidad){
        List<MedicoDTO> medicos = medicoService.getMedicosByEspecialidad(especialidad);
        return new ResponseEntity<>(medicos,HttpStatus.OK);
    }

}
