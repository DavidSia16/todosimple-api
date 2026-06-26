package daviddev.todosimple_api.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import daviddev.todosimple_api.models.User;
import daviddev.todosimple_api.repository.UserRepository;
import daviddev.todosimple_api.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = this.userRepository.findByUsername(username);
        if(Objects.isNull(user))
        throw new  UsernameNotFoundException("Usuario não encontrado " + username);
        return  new UserSpringSecurity(user.getId(), user.getUsername(), user.getPassword(), user.getProfiles());
    }

    


    
}
