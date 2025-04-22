package ecommerce_server.ecommerce_server.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ecommerce_server.ecommerce_server.ProductReview.ProductReview;
import ecommerce_server.ecommerce_server.cart.Cart;
import jakarta.persistence.*;
import lombok.*;
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
@ToString(exclude = "cart") // exclude circular reference
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
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProductReview> reviews;
    @OneToOne(mappedBy = "user")
    private Cart cart;

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
