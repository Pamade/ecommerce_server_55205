package ecommerce_server.ecommerce_server.user;

import ecommerce_server.ecommerce_server.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    public boolean emailExists(String email) {
        if (email == null) {
            return false;
        }
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean iSCorrect(String value, int min_size, int max_size){
        return (value == null || value.length() > max_size || value.length() < min_size);

    }
    public ResponseEntity<?> createUser(UserRegisterRequest userRegisterRequest) {
        Map<String, String> errors = new HashMap<>();
        String firstName = userRegisterRequest.getFirstName();
        String lastName = userRegisterRequest.getLastName();
        String email = userRegisterRequest.getEmail();
        String password = userRegisterRequest.getPassword();
        String repeatPassword = userRegisterRequest.getRepeatPassword();
        int phoneNumber = userRegisterRequest.getPhoneNumber();
        String city = userRegisterRequest.getCity();
        String address = userRegisterRequest.getAddress();

        if (iSCorrect(firstName, 2, 35)) {
            errors.put("firstName", "First name must containt 2 to 35 characters");
        }

        if (iSCorrect(lastName, 2, 35)) {
            errors.put("lastName", "Last name must containt 2 to 35 characters");
        }

        if (iSCorrect(city, 2, 35)) {
            errors.put("city", "City must containt 2 to 35 characters");
        }

        if (iSCorrect(address, 2, 50)) {
            errors.put("address", "Address must containt 2 to 50 characters");
        }

        if (iSCorrect(String.valueOf(phoneNumber), 2, 15)) {
            errors.put("phoneNumber", "Phone number must contain 2 to 15 characters");
        }

        if (email == null) {
            errors.put("email", "Email can't be empty");
        } else if (emailExists(email)) {
            errors.put("email", "Email is already in use");
        }

        if (password == null || password.length() < 8) {
            errors.put("password", "Password must contain 8 characters");
        }
        if (repeatPassword == null || !repeatPassword.equals(password)) {
            errors.put("repeatPassword", "Passwords do not match");
        }


        // If any errors, return the errors
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            String encryptedPassword  = passwordEncoder.encode(userRegisterRequest.getPassword());
            User user = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .phoneNumber(phoneNumber)
                    .city(city)
                    .address(address)
                    .role(Role.USER)
                    .password(encryptedPassword)
                    .build();
            userRepository.save(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new HashMap<>(Map.of("other", "Server Error")));
        }


    }

}
