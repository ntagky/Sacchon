package gr.codehub.sacchon.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AllConsultationsReceivedForOnePatientDto {

    private long id;
    private String doctor_first_name;
    private String doctor_last_name;
    private String doctor_email;
    private LocalDate date_created;
    private String details;
    private boolean seenConsultation;

}
