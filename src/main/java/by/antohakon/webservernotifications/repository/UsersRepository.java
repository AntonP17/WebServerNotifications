package by.antohakon.webservernotifications.repository;

import by.antohakon.webservernotifications.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query(value = "DELETE FROM user_subscription WHERE user_id = :userId AND service_id = :serviceId",
            nativeQuery = true)
    void deleteSubscription(@Param("userId") Long userId,
                            @Param("serviceId") Long serviceId);

//    @EntityGraph(attributePaths = "webServices")
//    Page<User> findAll(Pageable pageable);

}
