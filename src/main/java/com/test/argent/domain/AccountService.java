package com.test.argent.domain;

public class AccountService {

    public void applyTransaction(Account account, Transaction transaction) {
        if (!transaction.belongsTo(account)) {
            throw new IllegalArgumentException("La transaction n'appartient pas au compte " + account.getId());
        }
        if (transaction.isExpense()) {
            account.debit(transaction.amount());
        } else {
            account.credit(transaction.amount());
        }
    }
}
