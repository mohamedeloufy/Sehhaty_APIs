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

public class DependentInformationStatusCode200Test extends TestBase {


    String getDependentInfoFromYakeenEndPoint = loadPropertiesEndPoints.
            endPoint.getProperty("Get_dependent_info_from_yakeen_EndPoint");


    @DataProvider(name = "relations")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "Sheet1");
    }


    @Epic("Dependent")
    @Feature("Retrieve common dependent info (son,daughter,father,mother,sponsor,other)")
    @Story("as a user i want to get the my dependent info ")
    @Description("Verify that user can retrieve son daughter father mother other sponsor dependent information")
    @Severity(SeverityLevel.BLOCKER)
    @Link("www.google.com")
    @Test(dataProvider = "relations")
    public void DependentInformation_StatusCode200Test(long national_id, String password, String relations) {

        given()
                .log().all()
                .spec(createRequestSpecifications
                        (national_id, password, sehatayApiKey,
                                URI.create(sehatayQABaseURI)))
                .queryParam("relation", relations)
                .get(getDependentInfoFromYakeenEndPoint)
                .then()
                .log().all()
                .statusCode(200)
                .body("data", notNullValue());
    }
}
