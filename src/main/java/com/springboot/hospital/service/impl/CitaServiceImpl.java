package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.MedicoDTO;
import com.springboot.hospital.dto.PacienteDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.ConsultaMapper;
import com.springboot.hospital.mapper.MedicoMapper;
import com.springboot.hospital.mapper.PacienteMapper;
import com.springboot.hospital.model.*;
import com.springboot.hospital.repository.CitaRepository;
import com.springboot.hospital.repository.ConsultaRepository;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.repository.PacienteRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.MedicoService;
import com.springboot.hospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private ConsultaMapper consultaMapper;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicoMapper medicoMapper;


    @Override
    public List<CitaDTO> getAllCitas() {
        List<Cita> citas = citaRepository.findAll();
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<CitaDTO> getCitaById(Long id){

        Optional<Cita> cita = citaRepository.findById(id);

        return cita.map(citaMapper::toDTO);
    }

    @Override
    public Cita createCita(CitaDTO citaDTO, Long pacienteId, Long medicoId) throws ParseException{

        PacienteDTO pacienteDTO = pacienteService.getPacienteById(pacienteId).orElse(null);
        MedicoDTO medicoDTO = medicoService.getMedicoById(medicoId).orElse(null);

        if (pacienteDTO == null || medicoDTO == null) {
            return null;
        }

        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        Medico medico = medicoMapper.toEntity(medicoDTO);
        Cita cita = citaMapper.toEntity(citaDTO, paciente, medico);

        return citaRepository.save(cita);
    }


    @Override
    public CitaDTO updateCita(Long id, CitaDTO citaDTO) throws ParseException{

        Optional<Cita> citaOptional = citaRepository.findById(id);

        if(citaOptional.isPresent()){
            Cita cita = citaOptional.get();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha = dateFormat.parse(citaDTO.getFecha());

            cita.setCancelado(citaDTO.isCancelado());
            cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

            Optional<Paciente> pacienteOptional = pacienteRepository.findById(citaDTO.getPacienteId());
            cita.setPaciente(pacienteOptional.get());

            Optional<Medico> medicoOptional = medicoRepository.findById(citaDTO.getMedicoId());
            cita.setMedico(medicoOptional.get());

            return citaMapper.toDTO(citaRepository.save(cita));

        }

        return null;
    }

    @Override
    public void deleteCita(Long id){
        Optional<Cita> citaOptional = citaRepository.findById(id);

        if (citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            if (cita.getConsulta() != null){
                Consulta consulta = cita.getConsulta();
                consulta.setCita(null);
                consultaRepository.delete(consulta);
            }
            citaRepository.delete(cita);
        }
    }

    @Override
    public List<CitaDTO> getCitasByPacienteId(Long pacienteId){

        List<Cita> citas = citaRepository.findByPacienteId(pacienteId);

        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasByMedicoId(Long medicoId){
        List<Cita> citas = citaRepository.findByMedicoId(medicoId);

        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasByStetusCita(StatusCita statusCita){

        List<Cita> citas = citaRepository.findByStatusCita(statusCita);

        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
