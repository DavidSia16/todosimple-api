package daviddev.todosimple_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import daviddev.todosimple_api.models.User;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    
}
