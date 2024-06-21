package LibraryAdministration.src;

import java.util.HashMap;

public interface SubscriptionPlan {

    public void setFee();
    public void setFreeBorrowableLimit();
    public void setPenaltyFine();
    public void setMaximumItems();
    public int getBorrowableLimit(String type);
    public int getPenaltyFine(String type);
}
