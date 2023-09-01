package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.Branch;

import java.util.Random;

public class BranchGenerator {

    private static final String[] BRANCH_NAMES = {"Downtown Branch", "Airport Branch", "Suburb Branch", "City Center Branch", "East Side Branch"};
    private static final String[] OWNERS = {"John Doe", "Jane Smith", "David Johnson", "Emily Davis", "Michael Wilson"};

    public static Branch generateRandomBranch() {
        Random random = new Random();
        Branch branch = new Branch();

        branch.setAddress(null);
        branch.setRental(null);
        branch.setEmployees(null);
        branch.setName(BRANCH_NAMES[random.nextInt(BRANCH_NAMES.length)]);
        branch.setOwner(OWNERS[random.nextInt(OWNERS.length)]);

        return branch;
    }
}

