package kettlebell.agencyamazon.service.user;

import com.mongodb.DuplicateKeyException;
import kettlebell.agencyamazon.models.user.User;
import kettlebell.agencyamazon.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(String username, String password) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("Username already exists");
        }

    }

}
