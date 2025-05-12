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

        try {
            User updatedUser = webServicesService.addSubscriptionToUser(
                    subscriptionRequest.getUserId(),
                    subscriptionRequest.getServiceName()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Пользователю " + updatedUser.getLogin() + " добавлена подписка");

        } catch (IllegalArgumentException e) {
            // Ловим ошибки из сервиса (не найден пользователь/сервис, дублирование)
            return ResponseEntity.badRequest()
                    .body("Ошибка: " + e.getMessage()); // 400 Bad Request

        } catch (Exception e) {
            // На случай других непредвиденных ошибок
            return ResponseEntity.internalServerError()
                    .body("Произошла внутренняя ошибка"); // 500
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteSubscription(@RequestBody SubscriptionRequest subscriptionRequest){

        webServicesService.removeSubscription(subscriptionRequest.getUserId(), subscriptionRequest.getServiceName());
        return ResponseEntity.ok().build();

    }


}
