package pl.zdjavapol140.carrental.model;


public enum CarBodyType {

    S(2L, 3L),
    M(4L, 5L),
    L(6L, 7L),
    XL(8L,12L);

    private final Long minNumberOfPersons;
    private final Long maxNumberOfPersons;

    CarBodyType(Long minNumberOfPersons, Long maxNumberOfPersons) {
        this.minNumberOfPersons = minNumberOfPersons;
        this.maxNumberOfPersons = maxNumberOfPersons;
    }


    public Long getMinNumberOfPersons() {
        return minNumberOfPersons;
    }

    public Long getMaxNumberOfPersons() {
        return maxNumberOfPersons;
    }

}

