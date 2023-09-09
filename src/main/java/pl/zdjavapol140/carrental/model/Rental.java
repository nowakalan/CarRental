package pl.zdjavapol140.carrental.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "rentals")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private String owner;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Branch> branches;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(getId(), rental.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", address=" + address +
                ", owner='" + owner + '\'' +
                ", branches=" + Arrays.toString(branches.stream().mapToLong(Branch::getId).toArray()) +
                ", cars=" + Arrays.toString(cars.stream().mapToLong(Car::getId).toArray()) +
                '}';
    }
}
