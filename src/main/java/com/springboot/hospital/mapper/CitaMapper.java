package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Medico;
import com.springboot.hospital.model.Paciente;
import com.springboot.hospital.model.StatusCita;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class CitaMapper {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public CitaDTO toDTO(Cita cita){
        CitaDTO citaDTO = new CitaDTO();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatedFecha = sdf.format(cita.getFecha());

        citaDTO.setId(cita.getId());
        citaDTO.setFecha(formatedFecha);
        citaDTO.setCancelado(cita.isCancelado());
        citaDTO.setStatusCita(cita.getStatusCita().name());
        citaDTO.setPacienteId(cita.getPaciente().getId());
        citaDTO.setMedicoId(cita.getMedico().getId());

        return citaDTO;

    }

    public Cita toEntity(CitaDTO citaDTO, Paciente paciente, Medico medico) throws ParseException{
        Cita cita = new Cita();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citaDTO.getFecha());

        cita.setId(citaDTO.getId());
        cita.setFecha(fecha);
        cita.setCancelado(citaDTO.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));
        cita.setPaciente(paciente);
        cita.setMedico(medico);

        return cita;

    }

    public Cita toEntity(CitaDTO citaDTO) throws ParseException{
        Cita cita = new Cita();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citaDTO.getFecha());

        cita.setId(citaDTO.getId());
        cita.setFecha(fecha);
        cita.setCancelado(citaDTO.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

        Optional<Paciente> paciente = pacienteRepository.findById(citaDTO.getPacienteId());
        Paciente pacienteDb = paciente.get();

        Optional<Medico> medico = medicoRepository.findById(citaDTO.getMedicoId());
        Medico medicoDb = medico.get();

        cita.setPaciente(pacienteDb);
        cita.setMedico(medicoDb);

        return cita;

    }

}
