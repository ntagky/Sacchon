package gr.codehub.sacchon.app.configuration;

import gr.codehub.sacchon.app.controller.ChiefDoctorController;
import gr.codehub.sacchon.app.model.ChiefDoctor;
import gr.codehub.sacchon.app.model.Consultation;
import gr.codehub.sacchon.app.model.Doctor;
import gr.codehub.sacchon.app.repository.ChiefDoctorRepository;
import gr.codehub.sacchon.app.repository.ConsultationRepository;
import gr.codehub.sacchon.app.repository.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class InitialConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(
            DoctorRepository docRepository, ChiefDoctorRepository chiefDocRepository, ConsultationRepository consultationRepository){
        return args -> {
            Doctor doctor1 = new Doctor(0);
            Doctor doctor2 = new Doctor(1);
            docRepository.saveAll(List.of(doctor1, doctor2));

//            ChiefDoctor chiefDoctor1 = new ChiefDoctor();
//            ChiefDoctor chiefDoctor2 = new ChiefDoctor();
//            chiefDocRepository.saveAll(List.of(chiefDoctor1, chiefDoctor2));
        };
    }
}
