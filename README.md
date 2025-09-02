Banking Management System in Java

A role-based banking management system implemented in Java. This project simulates real-world banking operations and supports multiple user roles with distinct functionalities.

Features:

Account Holder: View account details, deposit/withdraw funds, transfer money, manage personal Tabung (savings goals), apply for loans, and track transaction history.

Bank Officer: Register new accounts and view account details.

Loan Officer: Approve or reject loans and disburse approved amounts.

Finance Officer: Set dividend rates and distribute dividends to account holders.

System Admin: Generate daily reports and reset user passwords.

Core Components:

User hierarchy with AccountHolder, BankOfficer, LoanOfficer, FinanceOfficer, and SystemAdmin.

BankAccount for managing balances and transactions.

Transaction for logging financial activities.

Tabung for goal-based savings management.

Loan for loan applications and processing.

Technologies:

Java SE

Object-Oriented Programming (OOP) concepts: inheritance, abstraction, encapsulation

Usage:
Run Main.java to start the console-based menu system and interact with the banking application according to your role.
