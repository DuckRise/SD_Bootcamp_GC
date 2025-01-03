import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private final ArrayList<BankAccount> accounts;
    static Scanner scanner = new Scanner(System.in);
    static boolean loggedIn = false;
    static ArrayList<User> userList = new ArrayList<>();
    static String currentUser;
    static Main atm;

    public Main() {
        accounts = new ArrayList<>();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Business ATM.\n");
        
        while (!loggedIn) {
            welcomeScreen();
            if (loggedIn) {
                break;
            }
        }

        // Continue with ATM functionality if user is logged in
        if (loggedIn) {
            mainMenu(); 
        }
    }

    private static void welcomeScreen(){
        // Display options to user
        System.out.println("***Main menu***");
        System.out.println("1. Login");
        System.out.println("2. Create account");

        // Get user input
        int choice;
        while (true) {
            System.out.print("Type an option and press enter: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                // Handle user input
                if (choice == 1) {
                    // Login
                    loggedIn = login(scanner);
                    break;
                } else if (choice == 2) {
                    // Create account
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    // Create new user account
                    createUserAccount(username, password);
                    System.out.println("Account created successfully!\n");
                    break;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } else {
                System.out.println("Invalid option, please try again.");
                scanner.next();
            }
        }
    }

    private static boolean login(Scanner scanner) {
        if (userList.isEmpty()) {
            System.out.println("There are no user accounts on the system, you might need to try creating one first!\n");
            return false;
        }

        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        // Check if the username and password are correct
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful!\n");
                currentUser = username;
                return true;
            }
        }
        System.out.println("Invalid username or password. Please try again.\n");
        return false;
    }

    private static void mainMenu(){
        Main atm = new Main();
        
        while (true) {
            System.out.println("Welcome, " + currentUser + " to the Business ATM system!");
            System.out.println("Please select an option:");
            System.out.println("1. Add an account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Transfer money");
            System.out.println("5. Check balance");
            System.out.println("6. Exit");

            if (scanner.hasNextInt()) {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {

                    case 1:
                        System.out.println("Please select an account type:");
                        System.out.println("1. Small business account");
                        System.out.println("2. Community account");
                        System.out.println("3. Client account");

                        if (scanner.hasNextInt()) {
                            int accountType = scanner.nextInt();
                            scanner.nextLine();

                            if (accountType >=1 && accountType <= 3) {
                                System.out.print("Please enter the account name: ");
                                String accountName = scanner.nextLine();

                                BankAccount account = null;

                                float startingBalance = 0;
                                
                                switch (accountType) {
                                    case 1:
                                        account = new SmallBusinessAccount(accountName, Integer.toString(atm.accounts.size() + 1),
                                            startingBalance);
                                        break;
                                    case 2:
                                        account = new CommunityAccount(accountName, Integer.toString(atm.accounts.size() + 1),
                                            startingBalance);
                                        break;
                                    case 3:
                                        account = new ClientAccount(accountName, Integer.toString(atm.accounts.size() + 1),
                                            startingBalance);
                                        break;
                                    default:
                                        System.out.println("Invalid account type!");
                                        break;
                                }

                                if (account != null) {
                                    atm.addAccount(account);
                                    System.out.println("Successfully added:     " + account + " | AccountName: " + 
                                        accountName + " | AccountNumber: " + account.getAccountNumber() + " | Overdraft: " + account.getOverdraft() + "\n");
                                    
                                }    
                            } else {
                                System.out.println("Invalid option, please try again.\n");
                            }
                        } else {
                            System.out.println("Invalid option, please try again.\n");
                            scanner.next();
                        }
                    break;

                    case 2:
                        System.out.print("Please enter the account number: ");
                        if (scanner.hasNextInt()) {
                            String depositAccountNumber = scanner.nextLine();

                            System.out.print("Please enter the deposit amount: ");
                            double depositAmount = scanner.nextDouble();
                            scanner.nextLine();
    
                            boolean depositSuccess = atm.deposit(depositAccountNumber, depositAmount);
    
                            if (depositSuccess) {
                                System.out.println("Deposit successful!");
                                System.out.println("New balance: " + atm.checkBalance(depositAccountNumber) + "\n");
                            } else {
                                System.out.println("Deposit failed! Account not found.\n");
                            }                                    
                        } else {
                            System.out.println("Invalid option, please try again.\n");
                            scanner.next();
                        }
                        break;

                    case 3:
                        System.out.print("Please enter the account number: ");
                        String withdrawAccountNumber = scanner.nextLine();

                        System.out.print("Please enter the withdrawal amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        scanner.nextLine();

                        boolean withdrawSuccess = atm.withdraw(withdrawAccountNumber, withdrawAmount);

                        if (withdrawSuccess) {
                            System.out.println("Withdrawal successful!");
                            System.out.println("New balance: " + atm.checkBalance(withdrawAccountNumber) + "\n");
                        } else {
                            System.out.println("Withdrawal failed! Account not found or insufficient balance.\n");
                        }
                        break;


                    case 4:
                        System.out.print("Please enter the account number for account 1 (SOURCE): ");
                        String account1Number = scanner.nextLine();
                        System.out.print("Please enter the account number for account 2 (DESTINATION): ");
                        String account2Number = scanner.nextLine();
                        BankAccount account1 = atm.findAccount(account1Number);
                        BankAccount account2 = atm.findAccount(account2Number);

                        if (account1 == null || account2 == null) {
                            System.out.println("One or both accounts not found!\n");
                        } else {
                            System.out.print("Please enter the transfer amount: ");
                            double transferAmount = scanner.nextDouble();
                            scanner.nextLine();

                            boolean transferSuccess = account1.transfer(account1, account2, transferAmount);

                            if (transferSuccess) {
                                System.out.println("Transfer successful!");
                                System.out.println("New balance for account " + account1Number + ": "
                                        + atm.checkBalance(account1Number));
                                System.out.println("New balance for account " + account2Number + ": "
                                        + atm.checkBalance(account2Number) + "\n");

                            } else {
                                System.out.println("Transfer failed! Insufficient funds.\n");
                            }
                        }
                        break;


                    case 5:
                        System.out.print("Please enter the account number: ");
                        String balanceAccountNumber = scanner.nextLine();

                        double balance = atm.checkBalance(balanceAccountNumber);
                        double overdraft = atm.checkOverdraft(balanceAccountNumber);

                        if (balance != -1) {
                            System.out.println("Balance of account " + balanceAccountNumber + ": " + balance + " | Overdraft: " + overdraft + "\n");
                        } else {
                            System.out.println("Balance check failed! Account not found.\n");
                        }
                        break;
                    
                    case 6:
                        System.out.println("Exiting the Business ATM system. Thank you for using our services!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid option! Please select a valid option.\n");
                        break;
                        
                }
            } else {
                System.out.println("Invalid option, please try again.\n");
                scanner.next();
            }         
        }
    }

    private static void createUserAccount(String username, String password) {
        User newUser = new User(username, password);
        userList.add(newUser);
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public boolean deposit(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        if (account != null && (account.getBalance() + account.getOverdraft()) >= amount) {
            account.withdraw(amount);
            return true;
        }
        return false;
    }

    public double checkBalance(String accountNumber) {
        BankAccount account = findAccount(accountNumber);
        if (account != null) {
            return account.getBalance();
        }
        return -1;
    }

    public double checkOverdraft(String accountNumber){
        BankAccount account = findAccount(accountNumber);
        if (account != null) {
            return account.getOverdraft();
        }
        return -1;
    }

    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}
