package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ChiefDoctorException;

import java.time.LocalDate;
import java.util.List;

public interface ChiefDoctorService {

    public List<ConsultationsGivenByDoctor>
    getConsultationsBetweenDatesGiven(long id, LocalDate startingDate, LocalDate endingDate);
    ChiefDoctorDto createChiefDoctor(ChiefDoctorDto chiefDoctor);
    List<ChiefDoctorDto> readChiefDoctor();
    ChiefDoctorDto readChiefDoctor(long id) throws ChiefDoctorException;
    boolean updateChiefDoctor(ChiefDoctorDto chiefDoctor, long id);
    boolean deleteChiefDoctor(long id);
}
