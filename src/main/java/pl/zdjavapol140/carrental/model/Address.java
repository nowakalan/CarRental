package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String country;
    String city;
    String postalCode;
    String details;


    //TODO Czy poprawna jest implementacja equals & hashcode na id?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getId(), address.getId()) && Objects.equals(getCountry(), address.getCountry()) && Objects.equals(getCity(), address.getCity()) && Objects.equals(getPostalCode(), address.getPostalCode()) && Objects.equals(getDetails(), address.getDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
