package daviddev.todosimple_api.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class USerUpdateDTO {
    
    private Long id;

    @NotBlank
    @Size(min = 8, max = 60)
    private String password;
}
