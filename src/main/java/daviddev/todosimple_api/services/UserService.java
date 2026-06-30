
package daviddev.todosimple_api.services;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import daviddev.todosimple_api.models.User;
import daviddev.todosimple_api.models.dto.USerUpdateDTO;
import daviddev.todosimple_api.models.dto.UserCreateDTO;
import daviddev.todosimple_api.models.enums.ProfileEnum;
import daviddev.todosimple_api.repository.UserRepository;
import daviddev.todosimple_api.security.UserSpringSecurity;
import daviddev.todosimple_api.services.exceptions.AuthorizationException;
import daviddev.todosimple_api.services.exceptions.DataBidingViolationException;
import daviddev.todosimple_api.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findById(Long id) {
        UserSpringSecurity userSpringSecurity = authenticated();
        if(!Objects.nonNull(userSpringSecurity )|| !userSpringSecurity.hasRole(ProfileEnum.ADMIN)
             && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("Acesso negado!");

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException
        ("Usuario não encontrado! Id: " + id + " ,Tipo:" + User.class.getName()
        )); 
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
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

    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public User fromDTO(@Valid UserCreateDTO obj) {
        User user = new User();
        user.setUsername(obj.getUsername());
        user.setPassword(obj.getPassword());
        return user ;
    }

    public User fromDTO(@Valid USerUpdateDTO obj) {
        User user = new User();
        user.setId(obj.getId());
        user.setPassword(obj.getPassword());
        return user ;
    }
}