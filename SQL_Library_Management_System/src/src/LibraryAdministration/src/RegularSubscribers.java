package LibraryAdministration.src;

import java.util.HashMap;

public class RegularSubscribers implements SubscriptionPlan{

    HashMap<String, Integer> freeBorrowableLimit = new HashMap<>();
    HashMap<String, Integer> penaltyFine = new HashMap<>();
    HashMap<String, Integer> maximumBorrowablity = new HashMap<>();
    int fee;

    RegularSubscribers(){
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
        freeBorrowableLimit.put("BOOKS", 28);
        freeBorrowableLimit.put("CDS", 28);
        freeBorrowableLimit.put("DVDS", 28);
    }

    @Override
    public void setPenaltyFine() {
        penaltyFine.put("BOOKS", 100);
        penaltyFine.put("CDS", 100);
        penaltyFine.put("DVDS", 100);
    }

    @Override
    public void setMaximumItems() {
        maximumBorrowablity.put("BOOKS", 3);
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

