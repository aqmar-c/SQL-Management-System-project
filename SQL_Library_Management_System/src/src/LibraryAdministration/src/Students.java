package LibraryAdministration.src;

import java.util.HashMap;

public class Students implements SubscriptionPlan{

    HashMap<String, Integer> freeBorrowableLimit = new HashMap<String, Integer>();
    HashMap<String, Integer> penaltyFine = new HashMap<String, Integer>();
    HashMap<String, Integer> maximumBorrowablity = new HashMap<String, Integer>();
    int fee;

    Students(){
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
        freeBorrowableLimit.put("CDS", 42);
        freeBorrowableLimit.put("DVDS", 42);
    }

    @Override
    public void setPenaltyFine() {
        penaltyFine.put("BOOKS", 25);
        penaltyFine.put("CDS", 25);
        penaltyFine.put("DVDS", 25);
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
