package ecommerce_server.ecommerce_server.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {

//    @Column(length = 15)
//    private int phoneNumber;
//    @Column(length = 35)
//    private String city;
//    @Column(length = 50)
//    private String address;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 35, message = "First name must containt 2 to 35 characters")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 35, message = "Last name must be between 2 and 35 characters")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private String repeatPassword;
    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 15, message = "Phone number must containt minimum 9 characters")
    private int phoneNumber;
    @NotBlank(message = "City is required")
    @Size(min = 2, max = 35, message = "City must containt 2 to 35 characters")
    private String city;
    @NotBlank(message = "Address is required")
    @Size(min = 2, max = 50, message = "City must containt 2 to 50 characters")
    private String address;

}