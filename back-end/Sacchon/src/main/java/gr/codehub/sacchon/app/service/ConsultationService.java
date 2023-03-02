package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ConsultationException;

import java.time.LocalDate;
import java.util.List;

public interface ConsultationService {
    List<AllConsultationsReceivedForOnePatientDto> getAllConsultationsReceivedForPatient(long patientId);
    ConsultationDto createConsultation(ConsultationReceivedDto consultation);
    List<ConsultationDto> readConsultation();
    ConsultationDto readConsultation(long id) throws ConsultationException;
    ConsultationModifiedDto readConsultationModified(long id) throws ConsultationException;
    List<ConsultationDto> readConsultationByPatientId(long id);
    List<ConsultationBasicInfoDto> findConsultationInfoByPatientId(long id);
    List<Long> findPatientWithActiveConsultation(LocalDate dateGiven);
    boolean deleteConsultation(long id);
    ConsultationModifiedDto updateConsultationFromDoctorByPatientId(ConsultationModifiedDto consultationModifiedDto);
    ConsultationPureDto findConsultationIdInAndStatusSpecificDate(long patientId, LocalDate dateGiven, LocalDate previousDays);
}
