package Dependent;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class DependentRequestListStatusCode200Test extends TestBase {

    String getDependentRequestListEndPoint = loadPropertiesEndPoints.
            endPoint.getProperty("Get_dependent_request_list_EndPoint");

    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Epic("Dependent")
    @Feature("Get dependent request list")
    @Story("As a user i want get my dependent request list")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can view his dependent request list")
    @Link("www.facebook.com")
    @Test(dataProvider = "Users")
    public void getDependentRequestList_StatusCode200(long national_id, String password) {
        given()
                .log().all()
                .spec(createRequestSpecifications
                        (national_id, password, sehatayApiKey,
                                URI.create(sehatayQABaseURI)))
                .get(getDependentRequestListEndPoint)
                .then()
                .log().all()
                // Verify status code == 200
                .statusCode(200)
                // Verify data not equal null value
                .body("data", notNullValue())
                // Verify data.sent not equal null value
                .body("data.sent", notNullValue())
                // Verify data.received not equal null value
                .body("data.received", notNullValue());
    }
}
