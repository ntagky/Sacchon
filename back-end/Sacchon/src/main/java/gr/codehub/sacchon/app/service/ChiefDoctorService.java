package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.ChiefDoctorDto;
import gr.codehub.sacchon.app.dto.PatientConsultationCountDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ChiefDoctorException;

import java.time.LocalDate;
import java.util.List;

public interface ChiefDoctorService {
    List<ConsultationsGivenByDoctor>
    getConsultationsBetweenDatesGiven(long id, LocalDate startingDate, LocalDate endingDate);
    ChiefDoctorDto createChiefDoctor(ChiefDoctorDto chiefDoctor);
    List<ChiefDoctorDto> readChiefDoctor();
    ChiefDoctorDto readChiefDoctor(long id) throws ChiefDoctorException;
    boolean updateChiefDoctor(ChiefDoctorDto chiefDoctor, long id);
    boolean deleteChiefDoctor(long id);
    List<PatientDto> readPatientsIdWaitingForConsultation(LocalDate dateBefore);
    List<PatientConsultationCountDto> readPatientsWithConsultationBetween(LocalDate startingDate, LocalDate endingDate);
    List<PatientDto> readInactivePatientsWithinRange(LocalDate startingDate, LocalDate endingDate);
    List<DoctorDto> readInactiveDoctorsWithinRange(LocalDate startingDate, LocalDate endingDate);
}
