public class BankAccount {
    private String accountName;
    private String accountNumber;
    private double balance;

    public BankAccount(String accountName, String accountNumber, double balance) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance - amount >= 0) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Insufficient funds!");
            return false;
        }
    }

    public boolean transfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        if (fromAccount.balance - amount >= 0) {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            return true;
        } else {
            System.out.println("Insufficient funds!");
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean TwoSignatoriesRestriction(String firstSignatory) {
        return false;
    }


}


