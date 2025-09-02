/*  TEAM LEADER: MOAYED MOHAMEd   || A24CS0017
                 HOSAM ALTALIB    || A24CS4015
                 OMAR QAID        || A24CS0027
                 Mohamed ALsakkaf || A23CS4026               
*/


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


abstract class User {
    protected String id;
    protected String name;
    protected String username;
    protected String password;

    public User(String id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() { 
        
        return username;
    
    }
    public String getPassword() { 
        
        return password;
     }

    public String getName() {
        return name;
    }
    
    public abstract void displayMenu();

}



class BankAccount {
    private String accountNumber;
    private double balance;
    private List<Transaction> transactions;

    
    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    
    }

    public String getAccountNumber() { 
        
        return accountNumber;
    
    }
    
    public double getBalance() { 
        
        return balance; 
    
    }
    
    public void setBalance(double balance) { 
        
        this.balance = balance; 
    
    }
    
    public List<Transaction> getTransactions() { 
        
        return transactions; 
    
    }
}



class Transaction {

    private String transactionId;
    private String type;
    private double amount;
    private String note;
    private Date timestamp;

    public Transaction(String transactionId, String type, double amount, String note) {
   
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.note = note;
        this.timestamp = new Date();
   
    }

     public void printDetails() {
        System.out.println("========== Transaction ==========");
        System.out.println("ID    : " + transactionId);
        System.out.println("Type  : " + type);
        System.out.println(String.format("Amount: RM %.2f", amount));
        System.out.println("Note  : " + note);
        System.out.println("Date  : " + timestamp);
        System.out.println("==================================");
    }

    @Override
    public String toString() {
        
        return transactionId + ": " + type + " RM " +  amount;
    }
}
    
    
    



class Tabung {
    private String name;
    private double targetAmount;
    private double currentAmount;

    
    public Tabung(String name) {
        this.name = name;
    }

    public Tabung(String name, double targetAmount) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = 0;
    }

    public String getName() { 
        
        return name;
    
    }
    
    public double getTargetAmount() {
        
        return targetAmount;
    
    }
    
    public double getCurrentAmount() { 
        
        return currentAmount; 
    
    }

    public void deposit(double amt) { 
        
        this.currentAmount += amt;
    
    }
    
    public void withdraw(double amt) { 
        
        this.currentAmount -= amt;
    
    }
    
    public double progressPercent() {

        return targetAmount == 0 ? 0 : (currentAmount / targetAmount) * 100;
    
    }
}



class Loan {

    private String loanId;
    private double amount;
    private int termMonths;
    private String status;
    private AccountHolder borrower;

    public Loan(String loanId, double amount, int termMonths, String status, AccountHolder borrower) {

        this.loanId = loanId;
        this.amount = amount;
        this.termMonths = termMonths;
        this.status = status;
        this.borrower = borrower;

    }

    public String getLoanId() { 
        
        return loanId;
    
    }
    public double getAmount() { 
        
        return amount;
     
    }
    
    public int getTermMonths() { return 
        
        termMonths;
    
    }
    
    public String getStatus() { 
        
        return status;
    
    }
    
    public void setStatus(String status) { 
        
        this.status = status; 
    
    }
    
    public AccountHolder getBorrower() { 
        
        return borrower; 
    
    }
}


class AccountHolder extends User {
    private BankAccount account;
    private List<Tabung> tabungs;
    private List<Transaction> transactions;
    private Loan loan;

    public AccountHolder(String id, String name, String username, String password, BankAccount account) {
        super(id, name, username, password);
        this.account = account;
        this.tabungs = new ArrayList<>();
        this.transactions = account.getTransactions();
        this.loan = null;
    }

   
    public BankAccount getAccount() {
    
        return account;
    
    }

    @Override
    public void displayMenu() {  
        System.out.println("\n---- Account Holder Menu ----");
        System.out.println("1. View Account Details");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. View Transaction History");
        System.out.println("6. Apply for Loan");
        System.out.println("7. View Loan Status");
        System.out.println("8. Create Tabung");
        System.out.println("9. Allocate Money Into Tabung");
        System.out.println("10. Withdraw From Tabung");
        System.out.println("11. View Tabung Status");
        System.out.println("12. Logout");
        System.out.print("Enter choice: ");
    }

    public void viewAccountDetails() {
    
        System.out.println("\nAccount Number: " + account.getAccountNumber());
        System.out.printf("Balance       : RM %.2f%n", account.getBalance());
    
    }

   
    public void depositMoney(Scanner sc) {

        depositMoney(sc, "Cash deposit");
    
    }
    
    public void depositMoney(Scanner sc, String note) {
        
        System.out.print("Enter amount to deposit: ");
        
        double amt = sc.nextDouble(); 
        sc.nextLine();
        
        if (amt <= 0) {
        
            System.out.println("Invalid amount.");
        
            return;
        }
        
        account.setBalance(account.getBalance() + amt);
    
        transactions.add(new Transaction("TXN" + System.currentTimeMillis(), "Deposit", amt, note));
    
        System.out.printf("Deposit successful. New balance: RM %.2f%n", account.getBalance());
    
    }

    public void depositFromLoan(double amount, String loanId) {
        
           System.out.println("DEBUG: depositFromLoan called with amount = " + amount);
        if (amount > 0) {
            account.setBalance(account.getBalance() + amount);
            transactions.add(new Transaction("TXN" + System.currentTimeMillis(), "Loan Disbursement", amount, "Loan ID: " + loanId));
        }
    }


    public void withdrawMoney(Scanner sc) {
    
        System.out.print("Enter amount to withdraw: ");
    
        double amt = sc.nextDouble(); 
        sc.nextLine();
    
        if (amt <= 0 || amt > account.getBalance()) {
    
            System.out.println("Invalid amount or insufficient balance.");
    
            return;
        }
    
        account.setBalance(account.getBalance() - amt);
        
        transactions.add(new Transaction("TXN" + System.currentTimeMillis(), "Withdrawal", amt, "Cash withdrawal"));
        
        System.out.printf("Withdrawal successful. New balance: RM %.2f%n", account.getBalance());
    
    }

    public void transferMoney(Scanner sc, List<AccountHolder> allUsers) {
        
        System.out.print("Enter target account number: ");
        
        String targetAcc = sc.nextLine();
        
        AccountHolder targetUser = null;
        
        for (AccountHolder ah : allUsers) {
            if (ah.getAccount().getAccountNumber().equals(targetAcc)) {
                targetUser = ah; break;
        
            }
        }
        if (targetUser == null) {
        
            System.out.println("Target account not found.");
        
            return;
        }
        
        System.out.print("Enter amount to transfer: ");
        double amt = sc.nextDouble(); sc.nextLine();
        if (amt <= 0 || amt > account.getBalance()) {
        
            System.out.println("Invalid amount or insufficient balance.");
        
            return;
        }
        
      
        BankAccount sender = account;
        BankAccount receiver = targetUser.getAccount();
        sender.setBalance(sender.getBalance() - amt);
        receiver.setBalance(receiver.getBalance() + amt);

        Transaction outTx = new Transaction("TXN"+System.currentTimeMillis(), "Transfer Out", amt, "To "+receiver.getAccountNumber());
        Transaction inTx  = new Transaction("TXN"+(System.currentTimeMillis()+1), "Transfer In",  amt, "From "+sender.getAccountNumber());

        sender.getTransactions().add(outTx);
        receiver.getTransactions().add(inTx);
           
          System.out.println("Transfer successful. New balance: RM " + sender.getBalance());
    }

    public void viewTransactionHistory() {

        if (transactions.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }

        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public void applyForLoan(Scanner sc, List<Loan> pendingLoans) {
        if (loan != null && loan.getStatus().equals("Pending")) {

            System.out.println("You already have a pending loan.");
            
            return;
        
        }

        System.out.print("Enter loan amount: ");
        
        double amt = sc.nextDouble();
        
        System.out.print("Enter term in months: ");
        
        int term = sc.nextInt(); sc.nextLine();
        
        String loanId = "LN" + System.currentTimeMillis();
        
        Loan newLoan = new Loan(loanId, amt, term, "Pending", this);
        
        pendingLoans.add(newLoan);
        
        this.loan = newLoan;
        
        System.out.println("Loan application submitted. ID: " + loanId);
    
    }

    public void viewLoanStatus() {

        if (loan == null) {
        
            System.out.println("No loan applications.");
        }
        
        else {

            System.out.println("Loan ID     : " + loan.getLoanId());
            System.out.printf("Amount      : RM %.2f%n", loan.getAmount());
            System.out.println("Term (mths) : " + loan.getTermMonths());
            System.out.println("Status      : " + loan.getStatus());
        }
    }

    public void createTabung(Scanner sc) {
       
        System.out.print("Enter name for Tabung: ");
        String name = sc.nextLine();
        System.out.print("Enter target amount: ");
        double target = sc.nextDouble(); sc.nextLine();
        Tabung tb = new Tabung(name, target);
        tabungs.add(tb);
        System.out.printf("Tabung '%s' created with target RM %.2f%n", name, target);
    
    }

    public void allocateMoneyTabung(Scanner sc) {

        if (tabungs.isEmpty()) {
            System.out.println("No Tabung found.");
            return;
        }
        
        System.out.println("Select Tabung to allocate:");
        
        for (int i = 0; i < tabungs.size(); i++) {
            Tabung t = tabungs.get(i);
            System.out.printf("%d. %s (%.2f/%.2f)%n", i+1, t.getName(), t.getCurrentAmount(), t.getTargetAmount());
        }
        
        int idx = sc.nextInt(); 
        
        sc.nextLine();
        
        if (idx < 1 || idx > tabungs.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Tabung sel = tabungs.get(idx-1);
        
        System.out.print("Enter amount to allocate: ");
        
        double amt = sc.nextDouble(); sc.nextLine();
        
        if (amt <= 0 || amt > account.getBalance()) {
        
            System.out.println("Invalid amount or insufficient balance.");
        
            return;
        }
        
        account.setBalance(account.getBalance() - amt);
        sel.deposit(amt);
        transactions.add(new Transaction("TXN" + System.currentTimeMillis(), "Tabung Allocation", amt, sel.getName()));
        System.out.printf("Allocated RM %.2f to '%s'.%n", amt, sel.getName());
    
    }

    public void withdrawFromTabung(Scanner sc) {
    
        if (tabungs.isEmpty()) {
            System.out.println("No Tabung found.");
            return;
        }
    
        System.out.println("Select Tabung to withdraw from:");
        for (int i = 0; i < tabungs.size(); i++) {
            Tabung t = tabungs.get(i);
            System.out.printf("%d. %s (%.2f/%.2f)%n", i+1, t.getName(), t.getCurrentAmount(), t.getTargetAmount());
        }

        int idx = sc.nextInt(); 
        sc.nextLine();
        
        if (idx < 1 || idx > tabungs.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        
        Tabung sel = tabungs.get(idx-1);
        
        System.out.print("Enter amount to withdraw: ");
        double amt = sc.nextDouble(); sc.nextLine();
        
        if (amt <= 0 || amt > sel.getCurrentAmount()) {
            System.out.println("Invalid amount.");
            return;
        }
        
        sel.withdraw(amt);
        account.setBalance(account.getBalance() + amt);
        transactions.add(new Transaction("TXN" + System.currentTimeMillis(), "Tabung Withdrawal", amt, sel.getName()));
        System.out.printf("Withdrawn RM %.2f from '%s'.%n", amt, sel.getName());
    }

    public void viewTabungStatus() {
        
        if (tabungs.isEmpty()) {
            System.out.println("No Tabung found.");
            return;
        }
        for (Tabung t : tabungs) {
            
            System.out.printf("%s: %.2f/%.2f (%.1f%%)%n",
            
                t.getName(), t.getCurrentAmount(), t.getTargetAmount(), t.progressPercent());
        }
    }
}



class BankOfficer extends User {
    
    public BankOfficer(String id, String name, String username, String password) {
        super(id, name, username, password);
    }

    @Override
    public void displayMenu() {
        System.out.println("\n---- Bank Officer Menu ----");
        System.out.println("1. Register New Account");
        System.out.println("2. View Account Details");
        System.out.println("3. Logout");
        System.out.print("Enter choice: ");
    }

    public void registerNewAccount(Scanner sc, List<AccountHolder> accountHolders) {
        
        System.out.print("Enter ID: "); String id = sc.nextLine();
        System.out.print("Enter full name: "); String name = sc.nextLine();
        System.out.print("Enter username: "); String uname = sc.nextLine();
        System.out.print("Enter password: "); String pwd = sc.nextLine();
        System.out.print("Enter initial deposit: "); double depo = sc.nextDouble(); sc.nextLine();
        System.out.print("Enter account number: "); String accNum = sc.nextLine();
        BankAccount ba = new BankAccount(accNum, depo);
        AccountHolder ah = new AccountHolder(id, name, uname, pwd, ba);
        accountHolders.add(ah);
        System.out.println("Account created for " + name);
    }

    public void viewAccountDetails(Scanner sc, List<AccountHolder> accountHolders) {
        
        System.out.print("Enter account username: "); String uname = sc.nextLine();
        
        for (AccountHolder ah : accountHolders) {
        
            if (ah.getUsername().equals(uname)) {
                System.out.println("ID: " + ah.id);
                System.out.println("Name: " + ah.name);
                System.out.println("Account #: " + ah.getAccount().getAccountNumber());
                System.out.printf("Balance : RM %.2f%n", ah.getAccount().getBalance());
                return;
            }
        }
        
        System.out.println("Account not found.");
    }
}



class LoanOfficer extends User {
   
    public LoanOfficer(String id, String name, String username, String password) {
        super(id, name, username, password);
    }

    @Override
    public void displayMenu() {
       
        System.out.println("\n---- Loan Officer Menu ----");
        System.out.println("1. Approve/Reject Loans");
        System.out.println("2. Logout");
        System.out.print("Enter choice: ");
    
    }

    public void processLoans(Scanner sc, List<Loan> pendingLoans) {
    if (pendingLoans.isEmpty()) {
        System.out.println("No pending loans.");
        return;
    }

    for (Loan ln : new ArrayList<>(pendingLoans)) {
        System.out.printf("Loan ID: %s, Amount: RM %.2f, Term: %dm, Borrower: %s%n",
            ln.getLoanId(), ln.getAmount(), ln.getTermMonths(), ln.getBorrower().getName()
        );

        System.out.print("Approve (A) / Reject (R)? ");
        String ans = sc.nextLine().trim().toUpperCase();

        if (ans.equals("A")) {
            ln.setStatus("Approved");

            AccountHolder borrower = ln.getBorrower();
            BankAccount account = borrower.getAccount();

            // 💰 Deposit the loan amount
            account.setBalance(account.getBalance() + ln.getAmount());

            // 🧾 Record the transaction
            account.getTransactions().add(
                new Transaction(
                    "TXN" + System.currentTimeMillis(),
                    "Loan Disbursement",
                    ln.getAmount(),
                    "Loan ID: " + ln.getLoanId()
                )
            );

            System.out.println("Loan approved and amount deposited to borrower's account.");

        } else {
            ln.setStatus("Rejected");
            System.out.println("Loan rejected.");
        }

        pendingLoans.remove(ln);
    }
}
}



class FinanceOfficer extends User {

    private static double dividendRate = 0;

    public FinanceOfficer(String id, String name, String username, String password) {

        super(id, name, username, password);
   
    }

    @Override
    public void displayMenu() {
   
        System.out.println("\n---- Finance Officer Menu ----");
        System.out.println("1. Set Dividend Rate");
        System.out.println("2. Distribute Dividends");
        System.out.println("3. Logout");
        System.out.print("Enter choice: ");
   
    }

    public void setDividendRate(Scanner sc) {
   
        System.out.print("Enter dividend rate (%) : ");
        dividendRate = sc.nextDouble(); sc.nextLine();
        System.out.printf("Dividend rate set to %.2f%%%n", dividendRate);
   
    }

    public void distributeDividends(List<AccountHolder> accountHolders) {
   
        System.out.println("Distributing dividends...");
        for (AccountHolder ah : accountHolders) {
   
            double div = ah.getAccount().getBalance() * dividendRate / 100;
            ah.getAccount().setBalance(ah.getAccount().getBalance() + div);
            ah.getAccount().getTransactions().add(
                             new Transaction("TXN" + System.currentTimeMillis(), "Dividend", div, "Dividend payout")
            );
            
            System.out.printf("Paid RM %.2f to %s%n", div, ah.name);
        }
    }
}


class SystemAdmin extends User {
    public SystemAdmin(String id, String name, String username, String password) {
        super(id, name, username, password);
    }

    @Override
    public void displayMenu() {
        
        System.out.println("\n---- System Admin Menu ----");
        System.out.println("1. Generate Daily Report");
        System.out.println("2. Reset User Password");
        System.out.println("3. Logout");
        System.out.print("Enter choice: ");
    }

    public void generateReport(List<AccountHolder> accountHolders) {  
        
        System.out.println("\n--- Daily Report ---");
        for (AccountHolder ah : accountHolders) {
            System.out.printf("ID: %s, Name: %s, Acc#: %s, Balance: RM %.2f%n",
                ah.id, ah.name, ah.getAccount().getAccountNumber(), ah.getAccount().getBalance()
            );
        }
    }

    public void resetUserPassword(Scanner sc, List<AccountHolder> accountHolders) {
        
        System.out.print("Enter account username: "); String uname = sc.nextLine();
        
        for (AccountHolder ah : accountHolders) {
            if (ah.getUsername().equals(uname)) {
                System.out.print("Enter new password: ");
                ah.password = sc.nextLine();
                System.out.println("Password updated for " + ah.name);
                return;
            }
        }
        
        System.out.println("Account not found.");
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<AccountHolder> accountHolders = new ArrayList<>();
        List<Loan> pendingLoans = new ArrayList<>();

        
        AccountHolder defaultAH = new AccountHolder( "A24CS", "MOAYED", "M47", "Ma123", new BankAccount("ACc1221", 10000));
        AccountHolder defaultAH2 = new AccountHolder( "A25CS", "AHMED", "AH18", "Ah123", new BankAccount("ACc2112", 5000));


        accountHolders.add(defaultAH);
        accountHolders.add(defaultAH2);

        

        BankOfficer bo = new BankOfficer("A24CS0017", "MOAYED", "MOAYED", "off123");
        LoanOfficer lo = new LoanOfficer("A24CS0027", "OMAR", "OMAR", "loan123");
        FinanceOfficer fo = new FinanceOfficer("A24CS", "HOSAM", "HOSAM", "fin123");
        SystemAdmin sa = new SystemAdmin("A24CS", "MOHAMED", "MOHAMED", "admin123");

        boolean running = true;
        
        while (running) {
        
            System.out.println("\n=== Welcome to Banking System ===");
            System.out.println("1. Account Holder");
            System.out.println("2. Bank Officer");
            System.out.println("3. Loan Officer");
            System.out.println("4. Finance Officer");
            System.out.println("5. System Admin");
            System.out.println("6. Exit");
            System.out.print("Select role: ");
            int role = sc.nextInt(); 
            sc.nextLine();

            switch (role) {
        
                case 1:
                    System.out.print("Username: "); String u1 = sc.nextLine();
                    System.out.print("Password: "); String p1 = sc.nextLine();
                    AccountHolder current = null;
        
                    for (AccountHolder ah : accountHolders) {
                        if (ah.getUsername().equals(u1) && ah.getPassword().equals(p1)) {
                            current = ah; break;
                        }
                    }
        
                    if (current == null) {
                        System.out.println("Login failed.");
                        break;
                    }
        
                    boolean ahMenu = true;
                    while (ahMenu) {
                        current.displayMenu();
                        int c = sc.nextInt(); sc.nextLine();
        
                        switch (c) {
        
                            case 1: 
                            
                            current.viewAccountDetails();
                            break;
                            
                            case 2: 
                            current.depositMoney(sc);
                             break;
                            
                             case 3:
                             current.withdrawMoney(sc);
                             
                             break;
                            
                             case 4:
                             current.transferMoney(sc, accountHolders);
                              break;
                            
                            case 5:
                             current.viewTransactionHistory();
                              break;
                            
                            case 6:
                            
                            current.applyForLoan(sc, pendingLoans); 
                            
                            break;
                            
                            case 7:
                             current.viewLoanStatus(); 
                             
                             break;
                            
                            case 8: 
                            current.createTabung(sc);
                            
                            break;
                            
                            case 9: 
                            current.allocateMoneyTabung(sc); 
                            
                            break;
                            
                            case 10:
                            
                            current.withdrawFromTabung(sc);
                            
                            break;
                            
                            case 11:
                             
                            current.viewTabungStatus();
                             
                             break;
                            
                            case 12:
                             ahMenu = false; 
                            break;
                            
                            default:
                             System.out.println("Invalid choice.");
        
                        }
        
                    }
        
                    break;

                case 2:
        
                bo.displayMenu();
                int boC = sc.nextInt(); sc.nextLine();
                if (boC == 1) bo.registerNewAccount(sc, accountHolders);
                else if (boC == 2) bo.viewAccountDetails(sc, accountHolders);
                
                break;

                case 3:
                
                lo.displayMenu();
                int loC = sc.nextInt(); sc.nextLine();
                if (loC == 1) lo.processLoans(sc, pendingLoans);
                
                break;

                case 4:
               
                fo.displayMenu();
                int foC = sc.nextInt(); sc.nextLine();
               
                if (foC == 1) fo.setDividendRate(sc);
                     else if (foC == 2) fo.distributeDividends(accountHolders);
                    break;

                case 5:
                
                sa.displayMenu();
                int saC = sc.nextInt(); 
                sc.nextLine();
                if (saC == 1) sa.generateReport(accountHolders);
                else if (saC == 2) sa.resetUserPassword(sc, accountHolders);
                
                break;

                case 6:
                
                running = false;
                System.out.println("Thanks For visiting our system. Ciao!!!!");
                
                break;

                default:
                
                System.out.println("Invalid role. Choose from 1-6 please");
            
            }
        }

        sc.close();
    }
}

 