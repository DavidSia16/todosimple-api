package daviddev.todosimple_api.services.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends EntityNotFoundException {

    public ObjectNotFoundException(String message) {   
        super(message);
    }
    
}
