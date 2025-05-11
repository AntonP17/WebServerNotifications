package by.antohakon.webservernotifications.service;

import by.antohakon.webservernotifications.entity.WebService;
import by.antohakon.webservernotifications.repository.WebServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebServicesService {

    private final WebServiceRepository webServiceRepository;

    public WebServicesService(WebServiceRepository webServiceRepository) {
        this.webServiceRepository = webServiceRepository;
    }

    public List<WebService> getAll() {
        return webServiceRepository.findAll();
    }
}
