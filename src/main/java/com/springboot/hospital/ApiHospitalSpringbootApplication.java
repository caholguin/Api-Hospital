package com.springboot.hospital;

import com.springboot.hospital.model.*;
import com.springboot.hospital.repository.CitaRepository;
import com.springboot.hospital.repository.ConsultaRepository;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.repository.PacienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiHospitalSpringbootApplication {

    public static void main(String[] args){
        SpringApplication.run(ApiHospitalSpringbootApplication.class, args);
    }

    //@Bean
    /*CommandLineRunner start(PacienteRepository pacienteRepository, MedicoRepository medicoRepository, CitaRepository citaRepository, ConsultaRepository consultaRepository){

        return args -> {
            Stream.of("Christan", "Raul", "Lanudo")
                    .forEach(nombre -> {
                        Paciente paciente = new Paciente();
                        paciente.setNombre(nombre);
                        paciente.setFechaNacimiento(new Date());
                        paciente.setEnfermedad(false);
                        pacienteRepository.save(paciente);
                    });

            Stream.of("Pepe", "Oscar", "Luis")
                    .forEach(nombre -> {
                        Medico medico = new Medico();
                        medico.setNombre(nombre);
                        medico.setEmail(nombre + ((int) Math.random() * 9) + "@gmail.com");
                        medico.setEspecialidad(Math.random() > 0.5 ? "Cardiología" : "General");
                        medicoRepository.save(medico);
                    });

            Paciente paciente1 = pacienteRepository.findById(1L).orElse(null);

            Medico medico = medicoRepository.findByNombre("Pepe");

            Cita cita1 = new Cita();
            cita1.setFecha(new Date());
            cita1.setStatusCita(StatusCita.PENDIENTE);
            cita1.setMedico(medico);
            cita1.setPaciente(paciente1);
            citaRepository.save(cita1);

            Cita citaDB = citaRepository.findById(1L).orElse(null);

            Consulta consulta = new Consulta();
            consulta.setFechaconsulta(new Date());
            consulta.setCita(citaDB);
            consulta.setInforme("informe consulta");
            consultaRepository.save(consulta);

        };

    }*/

}
