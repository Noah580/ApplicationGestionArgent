package com.test.argent.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    private static Account accountWithBalance(String balance) {
        return new Account("acc-1", "Compte courant", new BigDecimal(balance));
    }

    @Nested
    class Creation {

        @Test
        void createsAccountWithGivenValues() {
            Account account = accountWithBalance("100.00");

            assertThat(account.getId()).isEqualTo("acc-1");
            assertThat(account.getName()).isEqualTo("Compte courant");
            assertThat(account.getBalance()).isEqualByComparingTo("100.00");
        }

        @Test
        void acceptsZeroInitialBalance() {
            assertThat(accountWithBalance("0").getBalance()).isEqualByComparingTo("0");
        }

        @Test
        void rejectsBlankName() {
            assertThatThrownBy(() -> new Account("acc-1", " ", BigDecimal.TEN))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void rejectsNegativeInitialBalance() {
            assertThatThrownBy(() -> accountWithBalance("-1"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void rejectsNullInitialBalance() {
            assertThatThrownBy(() -> new Account("acc-1", "Compte courant", null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    class Credit {

        @Test
        void increasesBalance() {
            Account account = accountWithBalance("100.00");

            account.credit(new BigDecimal("50.25"));

            assertThat(account.getBalance()).isEqualByComparingTo("150.25");
        }

        @Test
        void rejectsNegativeAmount() {
            Account account = accountWithBalance("100.00");

            assertThatThrownBy(() -> account.credit(new BigDecimal("-10")))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThat(account.getBalance()).isEqualByComparingTo("100.00");
        }

        @Test
        void rejectsZeroAmount() {
            assertThatThrownBy(() -> accountWithBalance("100.00").credit(BigDecimal.ZERO))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class Debit {

        @Test
        void decreasesBalance() {
            Account account = accountWithBalance("100.00");

            account.debit(new BigDecimal("30.50"));

            assertThat(account.getBalance()).isEqualByComparingTo("69.50");
        }

        @Test
        void allowsDebitOfEntireBalance() {
            Account account = accountWithBalance("100.00");

            account.debit(new BigDecimal("100.00"));

            assertThat(account.getBalance()).isEqualByComparingTo("0");
        }

        @Test
        void rejectsDebitAboveBalanceAndKeepsBalanceUnchanged() {
            Account account = accountWithBalance("100.00");

            assertThatThrownBy(() -> account.debit(new BigDecimal("100.01")))
                    .isInstanceOf(InsufficientBalanceException.class);
            assertThat(account.getBalance()).isEqualByComparingTo("100.00");
        }

        @Test
        void rejectsNegativeAmount() {
            Account account = accountWithBalance("100.00");

            assertThatThrownBy(() -> account.debit(new BigDecimal("-50")))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThat(account.getBalance()).isEqualByComparingTo("100.00");
        }
    }
}
