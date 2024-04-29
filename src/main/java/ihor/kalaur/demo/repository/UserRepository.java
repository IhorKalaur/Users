package ihor.kalaur.demo.repository;

import ihor.kalaur.demo.model.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.birthDate BETWEEN :from AND :to")
    List<User> findByBirthDateBetween(LocalDate from, LocalDate to);

    List<User> findAll();
}
