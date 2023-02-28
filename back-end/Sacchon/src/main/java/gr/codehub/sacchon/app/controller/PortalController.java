package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.PortalDto;
import gr.codehub.sacchon.app.model.AESUtil;
import gr.codehub.sacchon.app.service.PortalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class PortalController {

    private final PortalService portalService;

    @GetMapping("/login")
    public PortalDto findUser(@RequestParam("email") String email, @RequestParam("password") String password){
        System.out.println("Password give: " + password + " and decrypted:" + new AESUtil().decrypt(password, password));
        return portalService.findRegisteredUser(email.replace("%", "&"),  new AESUtil().decrypt(password, password));
    }

}
