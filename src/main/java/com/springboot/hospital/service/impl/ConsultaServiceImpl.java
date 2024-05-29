package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.ConsultaMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Consulta;
import com.springboot.hospital.repository.CitaRepository;
import com.springboot.hospital.repository.ConsultaRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.ConsultaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private ConsultaMapper consultaMapper;

    @Autowired
    private CitaService citaService;



    @Override
    public List<ConsultaDTO> getAllConsultas(){

        List<Consulta> consultas = consultaRepository.findAll();

        return consultas.stream().map(consultaMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ConsultaDTO> getConsultaById(Long id){

        Optional<Consulta> consulta = consultaRepository.findById(id);

        return consulta.map(consultaMapper::toDTO);
    }

    @Override
    public ConsultaDTO createConsulta(Long citaId, ConsultaDTO consultaDTO) throws ParseException{

        CitaDTO citaDto = citaService.getCitaById(citaId).orElseThrow(() -> new EntityNotFoundException("Cita not found"));

        Consulta consulta = new Consulta();
        consulta.setCita(citaMapper.toEntity(citaDto));
        consulta.setFechaConsulta(new Date());
        consulta.setInforme(consultaDTO.getInforme());

        Consulta createdConsulta = consultaRepository.save(consulta);

        return consultaMapper.toDTO(createdConsulta);
    }

    @Override
    public ConsultaDTO updateConsulta(Long consultaId, ConsultaDTO consultaDTO) throws ParseException{

        Optional<Consulta> consultaOptional = consultaRepository.findById(consultaId);

        if(consultaOptional.isPresent()){

            Consulta consulta = consultaOptional.get();

            consulta.setFechaConsulta(consultaDTO.getFechaConsultaDate());
            consulta.setInforme(consultaDTO.getInforme());

            Consulta updateConsulta = consultaRepository.save(consulta);

            Cita cita = consulta.getCita();

            if(cita != null){
                CitaDTO citaDTO  = new CitaDTO();

                citaDTO.setFecha(cita.getFecha().toString());
                citaDTO.setFecha(cita.getFecha().toString());
                citaDTO.setCancelado(cita.isCancelado());
                citaDTO.setStatusCita(cita.getStatusCita().toString());
                citaDTO.setPacienteId(cita.getPaciente().getId());
                citaDTO.setMedicoId(cita.getMedico().getId());

                citaService.updateCita(cita.getId(),citaDTO);
            }

            return consultaMapper.toDTO(updateConsulta);

        }

        return null;
    }

    @Override
    public void deleteConsulta(Long consultaId){

        consultaRepository.deleteById(consultaId);

    }

    @Override
    public List<ConsultaDTO> getConsultasByInformeContaining(String searchTerm){

        List<Consulta> consultas = consultaRepository.findByInformeContainingIgnoreCase(searchTerm);

        return consultas.stream().map(consultaMapper::toDTO).collect(Collectors.toList());

    }

    @Override
    public List<ConsultaDTO> getConsultasByCita(Cita cita){

        List<Consulta> consultas = consultaRepository.findByCita(cita);

        return consultas.stream().map(consultaMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> getConsultasByCita(Long citaId) throws ParseException{

        CitaDTO citaDTO = citaService.getCitaById(citaId).orElseThrow(() -> new EntityNotFoundException("Cita not found"));

        Cita cita = citaMapper.toEntity(citaDTO);

        List<ConsultaDTO> consultas = getConsultasByCita(cita);

        return consultas;
    }
}
