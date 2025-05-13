package by.antohakon.webservernotifications.controller;

import by.antohakon.webservernotifications.dto.SubscriptionRequest;
import by.antohakon.webservernotifications.dto.SubscriptionResponse;
import by.antohakon.webservernotifications.entity.User;
import by.antohakon.webservernotifications.entity.WebService;
import by.antohakon.webservernotifications.service.WebServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<String> addSubscription(@RequestBody SubscriptionRequest subscriptionRequest){

        User updatedUser = webServicesService.addSubscriptionToUser(
                    subscriptionRequest.getUserId(),
                   subscriptionRequest.getServiceName());

        return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Пользователю login : " + updatedUser.getLogin() + " добавлена подписка");

    }

    @DeleteMapping
    public ResponseEntity<String> deleteSubscription(@RequestBody SubscriptionRequest subscriptionRequest){

        webServicesService.removeSubscription(subscriptionRequest.getUserId(), subscriptionRequest.getServiceName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Подписка успешно удалена");

    }


}
