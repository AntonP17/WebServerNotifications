package by.antohakon.webservernotifications.repository;

import by.antohakon.webservernotifications.entity.WebService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebServiceRepository extends JpaRepository<WebService, Long> {

}
