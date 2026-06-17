package daviddev.todosimple_api.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import daviddev.todosimple_api.models.Task;
import daviddev.todosimple_api.models.User;
import daviddev.todosimple_api.repository.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException
        ("Tarefa não encontrada! Id: " + id + " ,Tipo:" + Task.class.getName()
        ));
    }

    @Transactional
    public Task create (Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task Update (Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete (Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Não foi possivel apagar pois está relacionada com uma entidade!");
        }
    }



}
