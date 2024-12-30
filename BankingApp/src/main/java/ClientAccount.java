public class ClientAccount extends BankAccount {
    private static double overdraftFacility = 1500.0;

    public ClientAccount(String accountName, String accountNumber, double balance) {
        super(accountName, accountNumber, balance, overdraftFacility);
    }

    public double getOverdraftFacility() {
        return overdraftFacility;
    }
}
