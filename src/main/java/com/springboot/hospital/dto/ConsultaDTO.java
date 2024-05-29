package com.springboot.hospital.dto;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ConsultaDTO {

    private Long id;
    private String fechaConsulta;
    private String informe;
    private CitaDTO citaDto;

    public Date getFechaConsultaDate() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(this.fechaConsulta);
    }

    public void setFechaConsultaFromDate(Date fechaConsulta) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.fechaConsulta = sdf.format(fechaConsulta);
    }


}
