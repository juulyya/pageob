package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id='dashboard']");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р. ";

    SelenideElement card1Info = $("[data-test-id= '92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    SelenideElement card2Info = $("[data-test-id= '0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    public DashboardPage() {

        heading.shouldBe(visible);
        card1Info.shouldBe(visible);
        card2Info.shouldBe(visible);

    }

    public TransferPage deposit1() {
        $("[data-test-id= '92df3f1c-a033-48e6-8390-206f6b1f56c0'] button").click();
        return new TransferPage();
    }

    public TransferPage deposit2() {
        $("[data-test-id= '0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button").click();
        return new TransferPage();
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo){
        var text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);
    }


    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo){
        cards.findBy(attribute(cardInfo.getCardID())).$("button").click();
        return new TransferPage();
    }



}