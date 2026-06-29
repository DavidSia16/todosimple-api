package daviddev.todosimple_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import daviddev.todosimple_api.models.Task;
import daviddev.todosimple_api.models.projection.TaskProjection;   

@Repository

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<TaskProjection> findByUser_Id(Long Id);  

    //@Query("SELECT t from Task t where t.user.id = : userId")
    //List<Task> findByUserId(@Param("userId") Long userId);
    //@Query(value = "SELECT * FROM task t 
    // WHERE t.user_id = :userId", nativeQuery = true)
}
