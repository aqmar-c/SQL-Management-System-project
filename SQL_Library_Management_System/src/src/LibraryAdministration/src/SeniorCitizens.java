package LibraryAdministration.src;

import java.util.HashMap;

public class SeniorCitizens implements SubscriptionPlan{

    HashMap<String, Integer> freeBorrowableLimit = new HashMap<>();
    HashMap<String, Integer> penaltyFine = new HashMap<>();
    HashMap<String, Integer> maximumBorrowablity = new HashMap<>();
    int fee;

    SeniorCitizens(){
        setFee();
        setFreeBorrowableLimit();
        setPenaltyFine();
        setMaximumItems();
    }


    @Override
    public void setFee() {
        fee = 0;
    }

    @Override
    public void setFreeBorrowableLimit() {
        freeBorrowableLimit.put("BOOKS", 42);
        freeBorrowableLimit.put("CDS", 56);
        freeBorrowableLimit.put("DVDS", 56);
    }

    @Override
    public void setPenaltyFine() {
        penaltyFine.put("BOOKS", 10);
        penaltyFine.put("CDS", 10);
        penaltyFine.put("DVDS", 10);
    }

    @Override
    public void setMaximumItems() {
        maximumBorrowablity.put("BOOKS", 5);
        maximumBorrowablity.put("CDS", 2);
        maximumBorrowablity.put("DVDS", 2);
    }

    @Override
    public int getBorrowableLimit(String type) {
        return freeBorrowableLimit.get(type.toUpperCase());
    }

    @Override
    public int getPenaltyFine(String type) {
        return penaltyFine.get(type.toUpperCase());
    }
}

