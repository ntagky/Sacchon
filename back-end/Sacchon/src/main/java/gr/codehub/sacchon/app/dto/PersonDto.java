package gr.codehub.sacchon.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PersonDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private LocalDate signedDate;
    private String phoneNumber;
}
