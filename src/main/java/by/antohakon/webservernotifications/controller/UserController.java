package by.antohakon.webservernotifications.controller;

import by.antohakon.webservernotifications.dto.UserDto;
import by.antohakon.webservernotifications.dto.UserSubscriptionsDto;
import by.antohakon.webservernotifications.dto.WebServiceDto;
import by.antohakon.webservernotifications.entity.User;
import by.antohakon.webservernotifications.entity.WebService;
import by.antohakon.webservernotifications.repository.UsersRepository;
import by.antohakon.webservernotifications.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UsersRepository usersRepository;

    public UserController(UserService userService, UsersRepository usersRepository) {
        this.userService = userService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/{userId}/services")
    public ResponseEntity<UserSubscriptionsDto> getUserServices(@PathVariable Long userId) {

        UserSubscriptionsDto services = userService.findAllSubscriptions(userId);

        return ResponseEntity.ok(services);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {

        UserDto findUser = userService.getUserbyId(userId);

        return ResponseEntity.ok().body(findUser);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Page<UserDto> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
         userService.deleteUserById(userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateUser(user,userId);
    }
}
