package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ChiefDoctorException;
import gr.codehub.sacchon.app.model.ChiefDoctor;
import gr.codehub.sacchon.app.repository.ChiefDoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChiefDoctorServImpl implements ChiefDoctorService {
    private final ChiefDoctorRepository doctorRepository;

    @Override
    public List<ConsultationsGivenByDoctor>
    getConsultationsBetweenDatesGiven(long id, LocalDate startingDate, LocalDate endingDate){
       return doctorRepository.findConsultationsBetweenGivenDates(id,startingDate,endingDate);
    }

    @Override
    public ChiefDoctorDto createChiefDoctor(ChiefDoctorDto doctorDto){
        //validation
        ChiefDoctor doctor = doctorDto.asChiefDoctor();
        return new ChiefDoctorDto(doctorRepository.save(doctor));
    }

    @Override
    public List<ChiefDoctorDto> readChiefDoctor(){
        return doctorRepository
                .findAll()
                .stream()
                .map(ChiefDoctorDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ChiefDoctorDto readChiefDoctor(long id) throws ChiefDoctorException {
        return new ChiefDoctorDto(readChiefDoctorDb(id));
    }

    // private method created for internal use
    private ChiefDoctor readChiefDoctorDb(long id) throws ChiefDoctorException{
        Optional<ChiefDoctor> chiefDoctorOptional = doctorRepository.findById(id);
        if (chiefDoctorOptional.isPresent())
            return chiefDoctorOptional.get();
        throw new ChiefDoctorException("ChiefDoctor with id " + id + "is not found!");
    }


    @Override
    public boolean updateChiefDoctor(ChiefDoctorDto doctor, long id){
        boolean action;
        try {
            ChiefDoctor dbChiefDoctor = readChiefDoctorDb(id);
            doctorRepository.save(dbChiefDoctor);
            action = true;
        } catch (ChiefDoctorException e){
            action = false;
        }
        return action;
    }

    @Override
    public boolean deleteChiefDoctor(long id) {
        boolean action;
        try {
            ChiefDoctor dbChiefDoctor = readChiefDoctorDb(id);
            doctorRepository.delete(dbChiefDoctor);
            action = true;
        } catch (ChiefDoctorException e) {
            action = false;
        }
        return action;
    }

}
