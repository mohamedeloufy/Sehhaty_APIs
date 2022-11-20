package NationalAddressFromSPL.ConfirmNationalAddress;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ConfirmNationalAddressStatusCode400Test extends TestBase {

    String confirmNationalAddressEndpoint = loadPropertiesEndPoints.endPoint.getProperty("ConfirmNationalAddressEndPoint");

    @DataProvider(name = "userHasNoNationalAddress")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "userHasNoNationalAddress");
    }

    @Epic("Add Address in sehhaty application")
    @Feature("Add National Address From SPL")
    @Story("as a user i cannot confirm the national address if it does not exist in spl")
    @Description("Verify that user can not confirm national address if it does not exist in spl")
    @Severity(SeverityLevel.BLOCKER)
    @Link("confirm national address test case link")
    @Test(dataProvider = "userHasNoNationalAddress")
    public void confirmNationalAddress__StatusCode400(long nationalID, String password) {

        given()
                //Log request
                .log().all()
                // request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications(nationalID, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                // confirm national address endpoint
                .get(confirmNationalAddressEndpoint).then()
                //log response
                .log().all().statusCode(400).body("Message", equalTo("There is no Address registered in National Address"));
    }


}
