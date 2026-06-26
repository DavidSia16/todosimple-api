package daviddev.todosimple_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import daviddev.todosimple_api.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Transactional
    User  findByUsername(String name);
}
