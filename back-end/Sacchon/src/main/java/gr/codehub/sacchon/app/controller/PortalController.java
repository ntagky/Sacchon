package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.PortalDto;
import gr.codehub.sacchon.app.service.PortalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 The portal controller contains the read controller that enable the user to achieve the following
 functionalities in the application:
 ______________________________________________________________________________________
 • login to his account

 @RestController: This annotation is used to define the class as a Spring Rest Controller
 @AllArgsConstructor: This annotation is used to generate a constructor with arguments for all fields in the class
 @Slf4j: This annotation is used to enable logging in the class using the Simple Logging Facade for Java (SLF4J) API

 @author Alexandros Ntagkonikos
 @version 1.0
 @since 2023-03-01
 */
@RestController
@AllArgsConstructor
@Slf4j
public class PortalController {
    private final PortalService portalService;

    @GetMapping("/login")
    public PortalDto findUser(@RequestParam("email") String email, @RequestParam("password") String password){
        log.info("The end point /login has been used.");
        return portalService.findRegisteredUser(email,password);
    }
}
