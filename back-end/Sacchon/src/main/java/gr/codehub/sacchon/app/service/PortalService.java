package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.PortalDto;

public interface PortalService {
    PortalDto findRegisteredUser(String email, String password);
}
