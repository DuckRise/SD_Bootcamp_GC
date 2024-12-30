public class CommunityAccount extends BankAccount {
    private static double overdraftFacility = 2500.0;

    public CommunityAccount(String accountName, String accountNumber, double balance) {
        super(accountName, accountNumber, balance, overdraftFacility);
    }

    public double getOverdraftFacility() {
        return overdraftFacility;
    }
}
