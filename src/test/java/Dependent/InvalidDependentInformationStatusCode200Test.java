package Dependent;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class InvalidDependentInformationStatusCode200Test extends TestBase {
    String getDependentInfoFromYakeenEndPoint = loadPropertiesEndPoints.
            endPoint.getProperty("Get_dependent_info_from_yakeen_EndPoint");

    @DataProvider(name = "InvalidRelations")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "InvalidDependentInformation");
    }


    @Epic("Dependent")
    @Feature("Retrieve  dependent info (wife or husband)")
    @Story("as a user i want to get the my (wife husband) dependent info ")
    @Description("Verify that user canNot enter same gender type husband husband or wife wife")
    @Severity(SeverityLevel.BLOCKER)
    @Link("www.google.com")
    @Test(dataProvider = "InvalidRelations")
    public void getInvalidDependentInformation_StatusCode400(long national_id, String password, String relations)
    {

         given()
                 .log().all()
                 .spec(createRequestSpecifications(national_id,password,
                         sehatayApiKey, URI.create(sehatayQABaseURI)))
                .queryParam("relation",relations)
                .get(getDependentInfoFromYakeenEndPoint)
                .then()
                 .log().all()
                .statusCode(400)
                .body("Message",equalTo("An unexpected error occurred"));

    }
}


