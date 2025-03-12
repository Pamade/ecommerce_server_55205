package ecommerce_server.ecommerce_server.user;

import ecommerce_server.ecommerce_server.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public boolean phoneNumberExists(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
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
        String phoneNumber = userRegisterRequest.getPhoneNumber();
        String city = userRegisterRequest.getCity();
        String address = userRegisterRequest.getAddress();

        System.out.println(firstName+lastName+email+password+repeatPassword+phoneNumber+city+address);

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

        if (iSCorrect(phoneNumber, 2, 15)) {
            errors.put("phoneNumber", "Phone number must contain 2 to 15 characters");
        } else if (phoneNumberExists(phoneNumber)) {
            errors.put("phoneNumber", "Phone number is already in use");
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
            System.out.println("Errors");
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
            System.out.println("User registered");
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(new HashMap<>(Map.of("other", "Server Error")));
        }

    }

    public ResponseEntity<?> login (UserLoginRequest userLoginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getEmail(),
                            userLoginRequest.getPassword()
                    )
            );
            UserDetails user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow();
            String jwtToken = jwtService.generateToken(user);
            return ResponseEntity.status(201).body(new HashMap<>(Map.of("access_token", jwtToken)));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(404).body(new HashMap<>(Map.of("error", "Invalid Credentials")));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new HashMap<>(Map.of("error", "Server Error")));
        }

    }


    public ResponseEntity<Optional<UserMe>> getMe(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(401).body(Optional.empty());
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Optional<UserDetails> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(Optional.empty());
        }

        User user = (User) optionalUser.get();
        UserMe userMe = UserMe.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .city(user.getCity())
                .createdAt(user.getCreatedAt())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();

        return ResponseEntity.ok(Optional.of(userMe));
    }
}
