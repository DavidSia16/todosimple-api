package daviddev.todosimple_api.services.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataBidingViolationException extends DataIntegrityViolationException {

    public DataBidingViolationException(String message) {   
        super(message);
    }
    
}
