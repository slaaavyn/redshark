package tk.slaaavyn.redshark.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.slaaavyn.redshark.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String role;

    public static UserResponseDto toDto(User user) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole().getName());

        return userDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
