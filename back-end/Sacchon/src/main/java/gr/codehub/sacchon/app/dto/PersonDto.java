package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PersonDto {

    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public PersonDto(Person person) {
        if (person == null)
            return;
        firstName = person.getFirstName();
        lastName = person.getLastName();
        password = person.getPassword();
        email = person.getEmail();
    }

    public Person asPerson() {
        return new Person(firstName, lastName, password, email);
    }

}
