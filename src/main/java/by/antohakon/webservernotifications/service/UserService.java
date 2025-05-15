package by.antohakon.webservernotifications.service;

import by.antohakon.webservernotifications.dto.NewUserDto;
import by.antohakon.webservernotifications.dto.UserDto;
import by.antohakon.webservernotifications.dto.UserSubscriptionsDto;
import by.antohakon.webservernotifications.dto.WebServiceDto;
import by.antohakon.webservernotifications.entity.User;
import by.antohakon.webservernotifications.entity.WebService;
import by.antohakon.webservernotifications.exceptions.DuplicateUserException;
import by.antohakon.webservernotifications.exceptions.UserNotFoundException;
import by.antohakon.webservernotifications.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Cacheable(value = "user_cache_services", key = "#userId")
    public UserSubscriptionsDto findAllSubscriptions(Long userId) {

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User findUser = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        List<WebServiceDto> subscriptions = Optional.ofNullable(findUser.getWebServices())
                .orElse(Collections.emptyList()) // Если null → пустой список
                .stream()
                .map(ws -> new WebServiceDto(ws.getId(), ws.getName()))
                .toList();

        UserSubscriptionsDto userSubscriptions = new UserSubscriptionsDto(findUser.getId(), subscriptions);

        return userSubscriptions;

    }

    @Cacheable(value = "user_cache", key = "#userId")
    public UserDto getUserbyId(Long userId) {

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User findUser = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        UserDto userDto = new UserDto(
                findUser.getId(),
                findUser.getFirstName(),
                findUser.getLastName(),
                findUser.getLogin());

        return userDto;
    }

  // @Cacheable(value = "all_users", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public Page<UserDto> findAll(Pageable pageable) {

        return usersRepository.findAll(pageable)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getLogin()));
    }

    // ВОПРОС!!!!!!!!!!!!
    @CachePut(value = "user_cache", key = "#result.id")
    public UserDto createUser(NewUserDto newUser) {

        if (usersRepository.existsByLogin(newUser.login())) {
            throw new DuplicateUserException("User with login " + newUser.login() + " already exists.");
        }

        User createdUser = new User(
                newUser.firstName(),
                newUser.lastName(),
                newUser.login());

        usersRepository.save(createdUser);

        UserDto userDto = new UserDto(
                createdUser.getId(),
                createdUser.getFirstName(),
                createdUser.getLastName(),
                createdUser.getLogin());

        return userDto;
    }

    @CacheEvict(value = "user_cache", key = "#userId")
    public void deleteUserById(Long userId) {

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User findUser = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        usersRepository.deleteById(findUser.getId());
    }

    @CachePut(value = "user_cache", key = "#userId")
    public UserDto updateUser(NewUserDto newUser, Long userId) {

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User findUser = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        if (findUser != null) {
            findUser.setFirstName(newUser.firstName());
            findUser.setLastName(newUser.lastName());
            findUser.setLogin(newUser.login());
            usersRepository.save(findUser);
        }

        UserDto userDto = new UserDto(
                findUser.getId(),
                findUser.getFirstName(),
                findUser.getLastName(),
                findUser.getLogin()
        );

        return userDto;

    }
}
