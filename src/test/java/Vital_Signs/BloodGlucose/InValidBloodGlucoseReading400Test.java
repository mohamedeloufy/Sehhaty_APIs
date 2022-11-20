package Vital_Signs.BloodGlucose;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class InValidBloodGlucoseReading400Test extends TestBase {

    String AddBloodGlucoseEndPoint = loadPropertiesEndPoints.endPoint.getProperty("AddBloodGlucose");
    String VitalSignsEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetVitalSignsEndPoint");

    @DataProvider(name = "AddNewGlucose")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "InvalidGlucoseReading");
    }


    @Epic("vital signs")
    @Feature("profile info")
    @Story("as a user i want my profile data")
    @Link("www.google.com")
    @Severity(SeverityLevel.BLOCKER)
    @Description("verify that user profile api return user data")
    @Test(dataProvider = "AddNewGlucose")
    public void addNewGlucoseRecordDiabetic_StatusCode200(long national_id, String password, long reading, boolean fasting, boolean afterTwoHour) {


        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("glucose", (int) reading);
        hashMap.put("fasting", fasting);
        hashMap.put("takenTwoHoursAfterMeal", afterTwoHour);

        HashMap<Object, Object> hashMap2 = new HashMap<>();
        hashMap2.put("hasHypertension", false);
        hashMap2.put("hasDiabetes", true);


        given()
                // Log Request
                .log().all()
                // Request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications(national_id, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                .body(hashMap2)
                .when()
                .post(VitalSignsEndPoint)
                .then()
                .statusCode(200)
                .body("message", is("Success"));
        given()
                .log().all()
                .spec(createRequestSpecifications(national_id, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                // valid reading
                .body(hashMap)
                .when()
                // Vital Signs end point
                .post(AddBloodGlucoseEndPoint)
                .then()
                // log response
                .log().all()
                // verify status code =200
                .statusCode(400);
    }
}
