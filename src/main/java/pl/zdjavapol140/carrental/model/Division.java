package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
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
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Car> availableCars = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return Objects.equals(id, division.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
