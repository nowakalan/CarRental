package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
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
@Table(name = "branches")
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

    @OneToMany(mappedBy = "branch", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Employee> employees;

    private String name;
    private String owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(getId(), branch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", address=" + address +
                ", rental=" + rental +
                ", employees=" + employees +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
