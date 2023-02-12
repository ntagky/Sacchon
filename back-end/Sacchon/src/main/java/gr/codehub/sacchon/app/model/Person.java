package gr.codehub.sacchon.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person {

    private String firstName;
    private String lastName;
    private String password;
    private String email;

}
