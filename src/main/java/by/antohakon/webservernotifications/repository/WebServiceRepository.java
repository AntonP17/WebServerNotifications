package by.antohakon.webservernotifications.repository;

import by.antohakon.webservernotifications.entity.WebService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebServiceRepository extends JpaRepository<WebService, Long> {

  WebService  findByName(String name);

}
