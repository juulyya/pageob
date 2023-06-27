package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amount = $("[data-test-id= 'amount'] input");
    private SelenideElement fromAmount = $("[data-test-id='from'] input");

    private SelenideElement to = $("[data-test-id= 'to'] input");
    private SelenideElement transferHead = $(byText("Пополнение карты"));

    private SelenideElement amountButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $("[data-test-id='error-notification']");
    public TransferPage(){
        transferHead.shouldBe(visible);
        fromAmount.shouldBe(visible);
        fromAmount.shouldBe(visible);
        to.shouldBe(visible);
        amountButton.shouldBe(visible);
    }
    public void makeTransfer (String transferAmount, String cardInfo){
        amount.setValue(transferAmount);
        fromAmount.setValue(cardInfo);
        amountButton.click();

    }
    public DashboardPage makeValidAmount(String transferAmount, String cardInfo) {
        makeTransfer(transferAmount, cardInfo);
        return new DashboardPage();
    }

    public void ErrorMessageFind(String expectedText){
        errorMessage.shouldHave(Condition.exactText(expectedText), Duration.ofSeconds(10)).shouldBe(visible);

    }
}