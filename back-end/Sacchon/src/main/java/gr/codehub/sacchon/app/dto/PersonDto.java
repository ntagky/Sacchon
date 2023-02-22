package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Person;
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

    public PersonDto(Person person) {
        if (person == null)
            return;
        id = person.getId();
        firstName = person.getFirstName();
        lastName = person.getLastName();
        password = person.getPassword();
        email = person.getEmail();
        signedDate = person.getSignedDate();
    }

    public Person asPerson() {
        return new Person(id, firstName, lastName, password, email, signedDate);
    }

}
