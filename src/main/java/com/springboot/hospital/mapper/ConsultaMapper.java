package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Consulta;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ConsultaMapper {

    private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ConsultaDTO toDTO(Consulta consulta){

        ConsultaDTO consultaDTO = new ConsultaDTO();

        consultaDTO.setId(consulta.getId());
        consultaDTO.setFechaConsulta(dateformat.format(consulta.getFechaConsulta()));
        consultaDTO.setInforme(consulta.getInforme());

        if (consulta.getCita() != null) {
            Cita cita = consulta.getCita();
            CitaDTO citaDTO = new CitaDTO();

            citaDTO.setId(cita.getId());
            citaDTO.setFecha(dateformat.format(cita.getFecha()));
            citaDTO.setCancelado(cita.isCancelado());
            citaDTO.setStatusCita(cita.getStatusCita().toString());
            citaDTO.setPacienteId(cita.getPaciente().getId());
            citaDTO.setMedicoId(cita.getMedico().getId());

            consultaDTO.setCitaDto(citaDTO);
        }

        return consultaDTO;
    }

    public Consulta toEntity(ConsultaDTO consultaDTO) throws ParseException{

        Consulta consulta = new Consulta();

        consulta.setId(consultaDTO.getId());
        consulta.setFechaConsulta(dateformat.parse(consultaDTO.getFechaConsulta()));
        consulta.setInforme(consultaDTO.getInforme());

        if (consultaDTO.getCitaDto() != null) {
            CitaDTO citaDTO = new CitaDTO();
            Cita cita = new Cita();

            cita.setId(citaDTO.getId());
            consulta.setCita(cita);
        }

        return consulta;
    }
}
