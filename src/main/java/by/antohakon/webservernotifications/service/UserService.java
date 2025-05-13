package by.antohakon.webservernotifications.service;

import by.antohakon.webservernotifications.entity.User;
import by.antohakon.webservernotifications.entity.WebService;
import by.antohakon.webservernotifications.exceptions.UserNotFoundException;
import by.antohakon.webservernotifications.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public List<WebService> findAllSubscriptions(Long userId) {

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User findUser = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        List<WebService> userServices = Optional.ofNullable(findUser.getWebServices())
                .orElse(Collections.emptyList())
                .stream()
                .toList();

        return userServices;

    }

    public User getUserbyId(Long userId) {

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User findUser = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        return findUser;
                //usersRepository.findById(userId).orElse(null);
    }


    public Page<User> findAll(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    public User createUser(User user) {

        return usersRepository.save(user);
    }

    public void deleteUserById(Long userId) {

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User findUser = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        usersRepository.deleteById(findUser.getId());
    }

    public User updateUser(User user, Long userId) {

      //  User findUser = usersRepository.findById(userId).orElse(null);

        // 1. Находим пользователя (если не найден — кидаем исключение или возвращаем null)
        User findUser = usersRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.error("User with id {} not found", userId);
                    return   new UserNotFoundException("User with id " + userId + " not found");
                });

        if (findUser != null) {
            findUser.setFirstName(user.getFirstName());
            findUser.setLastName(user.getLastName());
            findUser.setLogin(user.getLogin());
            usersRepository.save(findUser);
        }

        return findUser;

    }
}
