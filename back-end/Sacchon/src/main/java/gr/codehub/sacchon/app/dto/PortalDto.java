package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortalDto {
    private long id;
    private UserType userType;
}
