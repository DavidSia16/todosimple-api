
package daviddev.todosimple_api.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daviddev.todosimple_api.models.User;
import daviddev.todosimple_api.repository.UserRepository;
import daviddev.todosimple_api.services.exceptions.DataBidingViolationException;
import daviddev.todosimple_api.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException
        ("Usuario não encontrado! Id: " + id + " ,Tipo:" + User.class.getName()
        )); 
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBidingViolationException("Não é possível excluir o usuário com id: " + id +
             "ainda há entidades ");
        }
    }
}