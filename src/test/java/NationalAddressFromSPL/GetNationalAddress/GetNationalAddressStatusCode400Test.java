package NationalAddressFromSPL.GetNationalAddress;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetNationalAddressStatusCode400Test extends TestBase {

    String getNationalAddressEndpoint = loadPropertiesEndPoints.
            endPoint.getProperty("GetNationalAddressEndPoint");

    @DataProvider(name = "userHasNoNationalAddress")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "userHasNoNationalAddress");
    }


    @Epic("Add Address in sehhaty application")
    @Feature("Add National Address From SPL")
    @Story("as a user i cannot get the national address if exist in spl")
    @Description("Verify that user cannot get national address if it does not exist in spl")
    @Severity(SeverityLevel.BLOCKER)
    @Link("testcase link")
    @Test(dataProvider = "userHasNoNationalAddress")
    public void getNationalAddress__StatusCode400(long nationalID, String password) {

        given()
                //Log request
                .log().all()
                //  request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications
                        (nationalID, password, sehatayApiKey,
                                URI.create(sehatayQABaseURI)))
                // get national address endpoint
                .get(getNationalAddressEndpoint)
                .then()
                //Log Response
                .log().all()
                // verify that status code 400
                .statusCode(400)
                // verify that message appears no national address exists
                .body("Message", equalTo("No National Address exists"));
    }

}
