package Vital_Signs.GetVitalSigns;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetVitalSignsStatusCode200Test extends TestBase {

    String VitalSignsEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetVitalSignsEndPoint");

    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Epic("vital signs")
    @Feature("profile info")
    @Story("as a user i want see my vital signs")
    @Link("www.google.com")
    @Severity(SeverityLevel.BLOCKER)
    @Description("verify that user can view vital signs")
    @Test(dataProvider = "Users")
    public void getVitalSigns_StatusCode200(long national_id, String password) {

        given()
                // Log Request
                .log().all()
                // Request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications(national_id, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                // Vital Signs end point
                .get(VitalSignsEndPoint)
                .then()
                // log response
                .log().all()
                // verify status code =200
                .statusCode(200)
                // verify body message
                .body("message", equalTo("Success"))
                // verify national id not null value
                .body("data.nationalId", notNullValue())
                // verify national id returned equal to login national id
                .body("data.nationalId", equalTo((int) national_id))
                // verify data latest not empty
                .body("data.latest", not(empty()))
                // verify data latest blood pressure not empty
                .body("data.latest.bloodPressure", not(empty()))
                // verify data latest glucose not empty
                .body("data.latest.glucose", not(empty()))
                // verify data latest waistLine not empty
                .body("data.latest.waistLine", not(empty()))
                // verify data latest bmi not empty
                .body("data.latest.bmi", not(empty()))
                // verify data diseases not null value
                .body("data.diseases", notNullValue())
                // verify data.allergies not null value
                .body("data.allergies", notNullValue())
                // verify data.familyDiseases not null value
                .body("data.familyDiseases", notNullValue())
                // verify other diseases not empty
                .body("otherDiseases", not(empty()))
                // verify other Allergies not empty
                .body("otherAllergies", not(empty()));


    }

}
