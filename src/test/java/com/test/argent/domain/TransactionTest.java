package com.test.argent.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionTest {

    private static final LocalDate DATE = LocalDate.of(2026, 9, 23);

    private static Transaction transaction(String amount, TransactionType type, String category, String accountId) {
        return new Transaction("tx-1", new BigDecimal(amount), type, category, DATE, accountId);
    }

    @Test
    void createsTransactionWithGivenValues() {
        Transaction transaction = transaction("42.00", TransactionType.EXPENSE, "Courses", "acc-1");

        assertThat(transaction.id()).isEqualTo("tx-1");
        assertThat(transaction.amount()).isEqualByComparingTo("42.00");
        assertThat(transaction.type()).isEqualTo(TransactionType.EXPENSE);
        assertThat(transaction.category()).isEqualTo("Courses");
        assertThat(transaction.date()).isEqualTo(DATE);
        assertThat(transaction.accountId()).isEqualTo("acc-1");
    }

    @Test
    void rejectsZeroAmount() {
        assertThatThrownBy(() -> transaction("0", TransactionType.INCOME, "Salaire", "acc-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNegativeAmount() {
        assertThatThrownBy(() -> transaction("-5", TransactionType.INCOME, "Salaire", "acc-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsMissingType() {
        assertThatThrownBy(() -> transaction("10", null, "Salaire", "acc-1"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void rejectsBlankCategory() {
        assertThatThrownBy(() -> transaction("10", TransactionType.INCOME, "", "acc-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsMissingDate() {
        assertThatThrownBy(() -> new Transaction("tx-1", BigDecimal.TEN, TransactionType.INCOME, "Salaire", null, "acc-1"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void rejectsMissingAccount() {
        assertThatThrownBy(() -> transaction("10", TransactionType.INCOME, "Salaire", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void isExpenseOnlyForExpenseType() {
        assertThat(transaction("10", TransactionType.EXPENSE, "Courses", "acc-1").isExpense()).isTrue();
        assertThat(transaction("10", TransactionType.INCOME, "Salaire", "acc-1").isExpense()).isFalse();
    }

    @Test
    void belongsToItsAccountOnly() {
        Transaction transaction = transaction("10", TransactionType.EXPENSE, "Courses", "acc-1");

        assertThat(transaction.belongsTo(new Account("acc-1", "Courant", BigDecimal.ZERO))).isTrue();
        assertThat(transaction.belongsTo(new Account("acc-2", "Épargne", BigDecimal.ZERO))).isFalse();
    }
}
