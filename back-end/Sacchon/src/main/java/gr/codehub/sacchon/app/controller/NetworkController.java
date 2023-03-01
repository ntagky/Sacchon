package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.service.NetworkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam("age") Double age,
            @RequestParam("dpf") Double dpf
    ){
        return networkService.getNetworkOutput(
                pregnancies,
                glucose,
                bloodPressure,
                skinThickness,
                insulin,
                bmi,
                age,
                dpf
        );
    }

}
