package test;

import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    DashboardPage dashboardPage;


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);

    }


    @Test
    void shouldTransferMoneyBetweenOwnCardsFromFirstToSecond() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = DataHelper.transferValidAmount(firstCardBalance);
        var transferPage = dashboardPage.deposit2();
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        transferPage.makeTransfer(String.valueOf(amount), String.valueOf(firstCard));
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        assertEquals(expectedFirstCardBalance, actualBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsFromSecondToFirst() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = DataHelper.transferValidAmount(secondCardBalance);
        var transferPage = dashboardPage.deposit1();
        var expectedFirstCardBalance = firstCardBalance + amount;
        var expectedSecondCardBalance = secondCardBalance - amount;
        transferPage.makeTransfer(String.valueOf(amount), String.valueOf(secondCard));
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        assertEquals(expectedFirstCardBalance, actualBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyBetweenOwnOverBalance() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = secondCardBalance + 1000; //Переводим на 1 карту сумму большую чем остаток на второй на 1000
        var transferPage = dashboardPage.deposit1();
        var expectedFirstCardBalance = firstCardBalance + amount;
        var expectedSecondCardBalance = secondCardBalance - amount;
        transferPage.makeTransfer(String.valueOf(amount), String.valueOf(secondCard));
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        assertEquals(expectedFirstCardBalance, actualBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, actualBalanceSecondCard);
    }
}
