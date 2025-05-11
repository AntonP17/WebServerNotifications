package by.antohakon.webservernotifications.service;

import by.antohakon.webservernotifications.entity.User;
import by.antohakon.webservernotifications.entity.WebService;
import by.antohakon.webservernotifications.repository.UsersRepository;
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

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<WebService> findAllSubscriptions(Long userId) {

        User findUser = usersRepository.findById(userId).orElse(null);

        List<WebService> userServices = Optional.ofNullable(findUser.getWebServices())
                .orElse(Collections.emptyList())
                .stream()
                .toList();

        return userServices;

    }

    public User getUserbyId(Long id) {
        return usersRepository.findById(id).orElse(null);
    }


    public Page<User> findAll(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    public User createUser(User user) {

        return usersRepository.save(user);
    }

    public void deleteUserById(Long userId) {
        usersRepository.deleteById(userId);
    }

    public User updateUser(User user, Long userId) {

        User findUser = usersRepository.findById(userId).orElse(null);

        if (findUser != null) {
            findUser.setFirstName(user.getFirstName());
            findUser.setLastName(user.getLastName());
            findUser.setLogin(user.getLogin());
        }

        return findUser;

    }
}
