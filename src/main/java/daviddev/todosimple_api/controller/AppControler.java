package daviddev.todosimple_api.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Use @Controller em vez de @RestController para retornar páginas HTML
public class AppControler {

    @GetMapping("/")
    public String home() {
        return "redirect:/login.html"; // Vai procurar pelo arquivo login.html dentro da pasta templates
    }


}
