public class BankAccount {
    private String accountName;
    private String accountNumber;
    private double balance;
    private double overDraft;

    public BankAccount(String accountName, String accountNumber, double balance, double overDraft) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.overDraft = overDraft;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount){
        if (amount > (getBalance() + overDraft)) {
            System.out.println("Insufficient funds, even with your overdraft!");
            return false;
        } else {
            if (amount <= (getBalance() + overDraft)) {
                balance -= amount;
            }
            System.out.println("");
            return true;
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

    public double getOverdraft(){
        return overDraft;
    }
}
