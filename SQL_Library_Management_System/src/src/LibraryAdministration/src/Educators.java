package LibraryAdministration.src;

import java.util.HashMap;

public class Educators implements SubscriptionPlan{

    HashMap<String, Integer> freeBorrowableLimit = new HashMap<>();
    HashMap<String, Integer> penaltyFine = new HashMap<>();
    HashMap<String, Integer> maximumBorrowablity = new HashMap<>();
    int fee;

    Educators(){
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
        freeBorrowableLimit.put("BOOKS", 56);
        freeBorrowableLimit.put("CDS", 56);
        freeBorrowableLimit.put("DVDS", 56);
    }

    @Override
    public void setPenaltyFine() {
        penaltyFine.put("BOOKS", 50);
        penaltyFine.put("CDS", 50);
        penaltyFine.put("DVDS", 50);
    }

    @Override
    public void setMaximumItems() {
        maximumBorrowablity.put("BOOKS", 10);
        maximumBorrowablity.put("CDS", 5);
        maximumBorrowablity.put("DVDS", 5);
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
