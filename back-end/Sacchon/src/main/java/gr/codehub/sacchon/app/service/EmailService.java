package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.model.Email;

public interface EmailService {
    String sendSimpleMail(Email email);
    void newConsultation(String email, String doctorName);
}
