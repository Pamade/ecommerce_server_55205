package ecommerce_server.ecommerce_server.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Builder
@Data
public class UserMe {
    private String firstName;
    private String email;
    private String lastName;
    private String phoneNumber;
    private String city;
    private String address;
    private Date createdAt;
}
