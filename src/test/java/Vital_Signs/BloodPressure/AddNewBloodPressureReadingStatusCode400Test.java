package Vital_Signs.BloodPressure;

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

public class AddNewBloodPressureReadingStatusCode400Test extends TestBase {
    String AddBloodPressureEndPoint = loadPropertiesEndPoints.endPoint.getProperty("AddBloodPressure");

    @DataProvider(name = "addNewRecord_StatusCode200")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "InValidBloodPressure");
    }

    @Epic("vital signs")
    @Feature("profile info")
    @Story("as a user i want my profile data")
    @Link("www.google.com")
    @Severity(SeverityLevel.BLOCKER)
    @Description("verify that user profile api return user data")
    @Test(dataProvider = "addNewRecord_StatusCode200")
    public void addNewRecord_StatusCode400(long national_id, String password, long diastolic, long systolic) {

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("diastolic", diastolic);
        hashMap.put("systolic", systolic);


        given()
                // Log Request
                .log().all()
                // Request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications(national_id, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                // valid reading
                .body(hashMap)
                .when()
                // Vital Signs end point
                .post(AddBloodPressureEndPoint)
                .then()
                // log response
                .log().all()
                // verify status code =200
                .statusCode(400)
                // verify Reading added successfully
                .body("message", is("Entered reading values are incorrect"));

    }
}
