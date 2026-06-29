package daviddev.todosimple_api.services;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import daviddev.todosimple_api.models.Task;
import daviddev.todosimple_api.models.User;
import daviddev.todosimple_api.models.enums.ProfileEnum;
import daviddev.todosimple_api.repository.TaskRepository;
import daviddev.todosimple_api.security.UserSpringSecurity;
import daviddev.todosimple_api.services.exceptions.AuthorizationException;
import daviddev.todosimple_api.services.exceptions.DataBidingViolationException;
import daviddev.todosimple_api.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> 
            new ObjectNotFoundException
        ("Tarefa não encontrada! Id: " + id + " ,Tipo:" + Task.class.getName()
        ));
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasTask(userSpringSecurity, task)) {
            throw new AuthorizationException("Acesso negado!");
        }
         return task;
    }

    public List<Task> findAllByUser() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated(); 
        if (Objects.isNull(userSpringSecurity))
             throw new AuthorizationException("Acesso negado!");
        List<Task> tasks = this.taskRepository.findByUser_Id(userSpringSecurity.getId());
        return  tasks;
    }

    @Transactional
    public Task create (Task obj) {
         UserSpringSecurity userSpringSecurity = UserService.authenticated(); 
        if (Objects.isNull(userSpringSecurity))
             throw new AuthorizationException("Acesso negado!");
        User user = this.userService.findById(userSpringSecurity.getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update (Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete (Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e){
            throw new DataBidingViolationException("Não foi possivel apagar pois está relacionada com uma entidade!");
        }
    }

    public Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }



}
