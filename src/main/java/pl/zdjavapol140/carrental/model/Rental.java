package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

}
