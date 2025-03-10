package ecommerce_server.ecommerce_server.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.createUser(userRegisterRequest);

    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
//        return userService.authenticate(userLoginRequest);
//    }
//
//    @GetMapping("/me")
//    public ResponseEntity<UserMeDTO> getMe() {
//        System.out.println("test");
//        return userService.getMe();
//    }
}
