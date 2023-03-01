package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.service.NetworkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 The network controller contains the read controller that enable the user to achieve the following
 functionalities in the application:
 ______________________________________________________________________________________
 • feed-forward the inputs given to the network and receive the probabilistic output

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
public class NetworkController {

    private NetworkService networkService;

    @GetMapping("/network/inputs")
    public Object[] findNetworkOutput(
            @RequestParam("pregnancies") Double pregnancies,
            @RequestParam("glucose") Double glucose,
            @RequestParam("bloodPressure") Double bloodPressure,
            @RequestParam("skinThickness") Double skinThickness,
            @RequestParam("insulin") Double insulin,
            @RequestParam("bmi") Double bmi,
            @RequestParam("dpf") Double dpf,
            @RequestParam("age") Double age
    ){
        log.info("The end point /network has been used.");
        return networkService.getNetworkOutput(
                pregnancies,
                glucose,
                bloodPressure,
                skinThickness,
                insulin,
                bmi,
                dpf,
                age
        );
    }

}
