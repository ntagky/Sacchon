package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.ConsultationBasicInfoDto;
import gr.codehub.sacchon.app.dto.ConsultationDto;
import gr.codehub.sacchon.app.dto.ConsultationReceivedDto;
import gr.codehub.sacchon.app.exception.ConsultationException;

import java.util.List;

public interface ConsultationService {
    ConsultationDto createConsultation(ConsultationReceivedDto consultation);
    List<ConsultationDto> readConsultation();
    ConsultationDto readConsultation(long id) throws ConsultationException;
    List<ConsultationDto> readConsultationByPatientId(long id);
    List<ConsultationBasicInfoDto> findConsultationInfoByPatientId(long id);
    boolean updateConsultation(ConsultationReceivedDto consultation, long id);
    boolean deleteConsultation(long id);
    // List<Long> findPatientsWaitingForConsultation(LocalDate dateBefore);
}
