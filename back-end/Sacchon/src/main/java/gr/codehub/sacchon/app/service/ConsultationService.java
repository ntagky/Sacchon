package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.AllConsultationsReceivedForOnePatientDto;
import gr.codehub.sacchon.app.dto.ConsultationBasicInfoDto;
import gr.codehub.sacchon.app.dto.ConsultationDto;
import gr.codehub.sacchon.app.dto.ConsultationReceivedDto;
import gr.codehub.sacchon.app.exception.ConsultationException;

import java.time.LocalDate;
import java.util.List;

public interface ConsultationService {
    public List<AllConsultationsReceivedForOnePatientDto> getAllConsultationsReceivedForPatient(long patientId);
    ConsultationDto createConsultation(ConsultationReceivedDto consultation);
    List<ConsultationDto> readConsultation();
    ConsultationDto readConsultation(long id) throws ConsultationException;
    List<ConsultationDto> readConsultationByPatientId(long id);
    List<ConsultationBasicInfoDto> findConsultationInfoByPatientId(long id);
    List<Long> findPatientWithActiveConsultation(LocalDate dateGiven);
    boolean updateConsultation(ConsultationReceivedDto consultation, long id);
    boolean deleteConsultation(long id);
    // List<Long> findPatientsWaitingForConsultation(LocalDate dateBefore);
}
