package by.antohakon.webservernotifications.service;

import by.antohakon.webservernotifications.dto.SubscriptionRequest;
import by.antohakon.webservernotifications.dto.SubscriptionResponse;
import by.antohakon.webservernotifications.entity.User;
import by.antohakon.webservernotifications.entity.WebService;
import by.antohakon.webservernotifications.exceptions.DuplicateSubscriptionException;
import by.antohakon.webservernotifications.exceptions.ServiceNotFoundException;
import by.antohakon.webservernotifications.exceptions.UserNotFoundException;
import by.antohakon.webservernotifications.repository.UsersRepository;
import by.antohakon.webservernotifications.repository.WebServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WebServicesService {

    private final WebServiceRepository webServiceRepository;
    private final UsersRepository usersRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public WebServicesService(WebServiceRepository webServiceRepository, UsersRepository usersRepository) {
        this.webServiceRepository = webServiceRepository;
        this.usersRepository = usersRepository;
    }

    public List<WebService> getAll() {
        return webServiceRepository.findAll();
    }

    @CacheEvict(value = "user_cache_services", key = "#userId")
    public User addSubscriptionToUser(Long userId, String serviceName) {

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        // 2. Находим сервис (если не найден — кидаем исключение)
        WebService service = webServiceRepository.findByName(serviceName);
        if (service == null) {
            LOGGER.error("Service with name {} not found", serviceName);
            throw new ServiceNotFoundException("Service with name " + serviceName + " not found");
        }

        // 3. Проверяем дублирование подписки
        if (user.getWebServices().stream()
                .anyMatch(ws -> ws.getName().equalsIgnoreCase(serviceName))) {
            LOGGER.error("User {} already has subscription to {}", userId, serviceName);
            throw new DuplicateSubscriptionException("User " + userId + " already has subscription to " + serviceName);
        }

        // 4. Добавляем подписку и сохраняем
        user.getWebServices().add(service);
        User savedUser = usersRepository.save(user);
        LOGGER.info("Added subscription to user {}", savedUser.getId());

        return savedUser;
    }

    @Transactional
    @CacheEvict(value = "user_cache_services", key = "#userId")
    public void removeSubscription(Long userId, String serviceName) {

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        // 2. Находим сервис (если не найден — кидаем исключение)
        WebService service = webServiceRepository.findByName(serviceName);
        if (service == null) {
            LOGGER.error("Service with name {} not found", serviceName);
            throw new ServiceNotFoundException("Service with name " + serviceName + " not found");
        }

        usersRepository.deleteSubscription(user.getId(), service.getId());
    }

}
