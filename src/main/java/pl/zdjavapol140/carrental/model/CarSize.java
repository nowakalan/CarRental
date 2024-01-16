package pl.zdjavapol140.carrental.model;


import java.math.BigDecimal;

public enum CarSize {

    SMALL(4L, 2L, 1L, BigDecimal.valueOf(50), "For example Fiat 500"),
    ECONOMY(4L, 4L, 1L, BigDecimal.valueOf(60), "For example Renault Clio"),
    MIDSIZE(5L, 4L, 2L, BigDecimal.valueOf(70), "For example Seat Leon"),
    ESTATES(5L,5L, 3L, BigDecimal.valueOf(80), "For example Skoda Octavia Combi"),
    SUV(5L, 4L, 3L, BigDecimal.valueOf(90), "For example Volvo XC60");

    private final Long maxNumberOfPersons;
    private final Long numberOfDoors;
    private final Long numberOfSuitcases;
    private final BigDecimal price;
    private final String carClassShortDescription;

    CarSize(Long maxNumberOfPersons, Long numberOfDoors, Long numberOfSuitcases, BigDecimal price, String carClassShortDescription) {
        this.maxNumberOfPersons = maxNumberOfPersons;
        this.numberOfDoors = numberOfDoors;
        this.numberOfSuitcases = numberOfSuitcases;
        this.price = price;
        this.carClassShortDescription = carClassShortDescription;
    }


    public Long getMaxNumberOfPersons() {
        return maxNumberOfPersons;
    }

    public Long getNumberOfDoors() {
        return numberOfDoors;
    }

    public Long getNumberOfSuitcases(){
        return numberOfSuitcases;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCarClassShortDescription(){
        return carClassShortDescription;
    }

}

