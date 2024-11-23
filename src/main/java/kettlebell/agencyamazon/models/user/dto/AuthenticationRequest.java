package kettlebell.agencyamazon.models.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticationRequest {
    @Size(min = 5, max = 255, message = "The name must be between 5 and 255 characters long.")
    @NotBlank(message = "Username is required")
    private String username;

    @Size(max = 255, message = "The password length cannot exceed 8 characters.")
    @NotBlank(message = "Password is required")
    @Pattern(message = "Password must be between 6 and 8 characters long with 1 capital letter", regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,8}$")
    private String password;
}
