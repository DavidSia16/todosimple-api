package daviddev.todosimple_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@RestController
public class HomeController {

    @GetMapping("/bemvindo")
    public ResponseEntity<String> bemVindo() {
        return ResponseEntity.ok("API ToDo Simple rodando com sucesso!");
    }


}
