package kettlebell.agencyamazon.service.user;

import kettlebell.agencyamazon.models.user.dto.UserCreateRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    void addUser(UserCreateRequest request);
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
