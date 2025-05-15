package by.antohakon.webservernotifications.dto;

import java.util.List;

public record UserSubscriptionsDto(Long userId,
                                   List<WebServiceDto> subscriptions) {
}
