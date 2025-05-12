package by.antohakon.webservernotifications.dto;

public class SubscriptionRequest {

    private Long userId;

    private String serviceName;

    public SubscriptionRequest() {
    }

    public SubscriptionRequest(Long userId, String serviceName) {
        this.userId = userId;
        this.serviceName = serviceName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
