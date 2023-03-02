package gr.codehub.sacchon.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ConsultationsGivenByDoctor {
    private String doctor_first_name;
    private String doctor_last_name;
    private String doctor_email;
    private LocalDate date_created;
    private String details;

    public ConsultationsGivenByDoctor(String doctor_first_name, String doctor_last_name, String doctor_email, LocalDate date_created, String details) {
        this.doctor_first_name = doctor_first_name;
        this.doctor_last_name = doctor_last_name;
        this.doctor_email = doctor_email;
        this.date_created = date_created;
        this.details = details;
    }
}
