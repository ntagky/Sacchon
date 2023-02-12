package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.ConsultationDto;
import gr.codehub.sacchon.app.exception.ConsultationException;

import java.util.List;

public interface ConsultationServices {
    ConsultationDto createConsultation(ConsultationDto consultation);
    List<ConsultationDto> readConsultation();
    ConsultationDto readConsultation(int id) throws ConsultationException;
    boolean updateConsultation(ConsultationDto consultation, int id);
    boolean deleteConsultation(int id);
}
