public class SmallBusinessAccount extends BankAccount {
    private static double overdraftFacility = 1000.0;
    
    public SmallBusinessAccount(String accountName, String accountNumber, double balance) {
        super(accountName, accountNumber, balance, overdraftFacility);
    }

    public double getOverdraftFacility() {
        return overdraftFacility;
    }
}
