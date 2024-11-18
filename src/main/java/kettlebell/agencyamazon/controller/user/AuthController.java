package kettlebell.agencyamazon.controller.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kettlebell.agencyamazon.config.security.JwtTokenProvider;
import kettlebell.agencyamazon.models.user.UserDto;
import kettlebell.agencyamazon.service.user.CustomUserDetailsService;
import kettlebell.agencyamazon.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password, Model model) {

        try {
            userService.register(username, password);
            log.info("registration with username: {}  successfully", username);
            return "redirect:/api/auth/login";
        } catch (Exception e) {
            log.info("un correct data registration with username: {}", password);
            model.addAttribute("error", e.getMessage());
            return "register";
        }

    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response) {
        UserDetails user = customUserDetailsService.loadUserByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtTokenProvider.generateToken(username);

            Cookie jwtCookie = new Cookie("token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            return "redirect:/api/statistics/data-on-asin?asinList=B0BHWD3DW6,B0BHWGQF3B";
        }
        return "redirect:/api/auth/login";
    }
}
