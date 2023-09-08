package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Id
//    private String idd = UUID.randomUUID().toString();

    private String firstName;
    private String lastName;

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String phone;


    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations = new ArrayList<>();

}
