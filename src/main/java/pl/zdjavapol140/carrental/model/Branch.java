package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @OneToMany(mappedBy = "division", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "division", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Car> availableCars = new HashSet<>();

}
