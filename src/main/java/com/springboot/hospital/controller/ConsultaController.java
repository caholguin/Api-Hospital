package com.springboot.hospital.controller;

import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listarConsultas(){
        List<ConsultaDTO> consultas = consultaService.getAllConsultas();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> obtenerConsulta(@PathVariable Long id){
        Optional<ConsultaDTO> consulta = consultaService.getConsultaById(id);
        return consulta.map(dto -> new ResponseEntity<>(dto,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ConsultaDTO> guardarconsulta(@RequestParam Long citaId, @RequestBody ConsultaDTO consultaDTO) throws ParseException{
        ConsultaDTO createConsulta = consultaService.createConsulta(citaId,consultaDTO);
        return new ResponseEntity<>(createConsulta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> actualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDTO consultaDTO) throws ParseException{
        ConsultaDTO updateConsulta = consultaService.updateConsulta(id,consultaDTO);
        return updateConsulta != null ? new ResponseEntity<>(updateConsulta, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Long id){
        consultaService.deleteConsulta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/searchParam")
    public ResponseEntity<List<ConsultaDTO>> listarConsultaPorInforme(@RequestParam String searchParam){
        List<ConsultaDTO> consultas = consultaService.getConsultasByInformeContaining(searchParam);
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<ConsultaDTO>> listarConsultasPorCita(@PathVariable Long citaId) throws ParseException{
        List<ConsultaDTO> consulta = consultaService.getConsultasByCita(citaId);
        return new ResponseEntity<>(consulta, HttpStatus.OK);
    }

}
