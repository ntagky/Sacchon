package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.ConsultationBasicInfoDto;
import gr.codehub.sacchon.app.dto.ConsultationDto;
import gr.codehub.sacchon.app.exception.ConsultationException;

import java.util.List;

public interface ConsultationService {
    ConsultationDto createConsultation(ConsultationDto consultation);
    List<ConsultationDto> readConsultation();
    ConsultationDto readConsultation(int id) throws ConsultationException;
    List<ConsultationDto> readConsultationByPatientId(int id);
    List<ConsultationBasicInfoDto> findConsultationInfoByPatientId(int id);
    boolean updateConsultation(ConsultationDto consultation, int id);
    boolean deleteConsultation(int id);
}
