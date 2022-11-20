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


public class DependentListStatusCode200Test extends TestBase {

    String getDependentListEndPoint = loadPropertiesEndPoints.
            endPoint.getProperty("Get_dependent_list_EndPoint");

    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Epic("Dependent")
    @Feature("Get dependent list")
    @Story("As a user i want get my dependent")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test how to get my dependent")
    @Link("www.facebook.com")
    @Test(dataProvider = "Users")
    public void getDependentList_StatusCode200(long national_id, String password) {

        given()
                .log().all()
                .spec(createRequestSpecifications
                        (national_id, password, sehatayApiKey,
                                URI.create(sehatayQABaseURI)))
                .get(getDependentListEndPoint)
                .then()
                .log().all()
                // Verify on status code ==200
                .statusCode(200)
                // Verify that current page not null value
                .body("current_page", notNullValue())
                // Verify that data not null value
                .body("data", notNullValue())
                // Verify that total not null value
                .body("total", notNullValue())
                // Verify that page size not null value
                .body("page_size", notNullValue())
                // verify that pages not null value
                .body("pages", notNullValue());

    }
}
