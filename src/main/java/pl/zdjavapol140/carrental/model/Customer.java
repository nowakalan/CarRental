package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Id
//    private String id = UUID.randomUUID().toString();

    private String firstName;
    private String lastName;

    @Email
    private String email;


    //@ManyToOne

    //    @Pattern(regexp = "^+\\d{2} \\d{3} \\d{3} \\d{3}$")
    private String phone;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", reservations=" + reservations +
                ", user_id=" + user.getEmail() +
                '}';
    }
}
