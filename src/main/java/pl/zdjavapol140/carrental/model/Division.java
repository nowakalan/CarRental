package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "divisions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Address address;

    @OneToMany
    private Set<Employee> employees;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Car> availableCars;


}
