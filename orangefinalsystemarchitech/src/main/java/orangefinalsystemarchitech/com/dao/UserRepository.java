package orangefinalsystemarchitech.com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import orangefinalsystemarchitech.com.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
