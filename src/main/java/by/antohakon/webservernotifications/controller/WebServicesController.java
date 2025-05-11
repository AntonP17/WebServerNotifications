package by.antohakon.webservernotifications.controller;

import by.antohakon.webservernotifications.entity.WebService;
import by.antohakon.webservernotifications.service.WebServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/service")
public class WebServicesController {

    private final WebServicesService webServicesService;

    public WebServicesController(WebServicesService webServicesService) {
        this.webServicesService = webServicesService;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<WebService> getAllWebServices(){
        return webServicesService.getAll();
    }


}
