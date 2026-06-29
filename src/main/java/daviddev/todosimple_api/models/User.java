package daviddev.todosimple_api.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import daviddev.todosimple_api.models.enums.ProfileEnum;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user.TABLE_NAME")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    public static final String TABLE_NAME = "user";

    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Id  
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @Size( min = 2, max = 100)
    @NotBlank(message = "usuario e senha não podem estar vazios")
    private String username;

   
    @Column(name = "password", length = 60, nullable = false)
    @NotBlank
    @Size( min = 8, max = 60) 
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<>();

    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable =  false)
    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum>getProfiles() {
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum) {
        this.profiles.add(profileEnum.getCode());
    }

   
}
