package ecommerce_server.ecommerce_server.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 35)
    private String firstName;
    @Column(unique = true, length = 100)
    private String email;
    @Column(length = 35)
    private String lastName;
    @Column(length = 100)
    private String password;
    @Column(length = 15)
    private String phoneNumber;
    @Column(length = 35)
    private String city;
    @Column(length = 50)
    private String address;
    // Eventually add bought products
    @Enumerated(EnumType.STRING)
    private Role role;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @PrePersist
    public void prePersist(){
        createdAt = new Date();
        lastUpdated = new Date();
    }
    @PreUpdate
    public void preUpdate(){
        lastUpdated = new Date();
    }

}
