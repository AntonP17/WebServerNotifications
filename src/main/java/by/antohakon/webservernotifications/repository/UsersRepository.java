package by.antohakon.webservernotifications.repository;

import by.antohakon.webservernotifications.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

}
