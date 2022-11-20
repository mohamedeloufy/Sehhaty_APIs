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

public class AddNewBloodPressureReadingForHypertensiveStatusCode200Test extends TestBase {
    String AddBloodPressureEndPoint = loadPropertiesEndPoints.endPoint.getProperty("AddBloodPressure");
    String VitalSignsEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetVitalSignsEndPoint");

    @DataProvider(name = "addNewRecord_StatusCode200")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "ValidBloodPressureHyper");
    }

    @Epic("vital signs")
    @Feature("profile info")
    @Story("as a user i want my profile data")
    @Link("www.google.com")
    @Severity(SeverityLevel.BLOCKER)
    @Description("verify that user profile api return user data")
    @Test(dataProvider = "addNewRecord_StatusCode200")
    public void addNewRecord_StatusCode200(long national_id, String password, long diastolic, long systolic, String messageCode) {

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("diastolic", diastolic);
        hashMap.put("systolic", systolic);

        HashMap<Object, Object> hashMap2 = new HashMap<>();
        hashMap2.put("hasHypertension", true);
        hashMap2.put("hasDiabetes", false);


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
                //.statusCode(200)
                // verify Reading added successfully
                .body("message", is("Reading added successfully"))
                // verify the systolic value that  we entered it
                .body("data.systolic", is((int) systolic))
                // verify the diastolic value that  we entered it
                .body("data.diastolic", is((int) diastolic))
                // verify message code low normal high very high blood pressure
                .body("data.messageCode", is(messageCode))
                // verify who enter this reading
                .body("data.enteredBy", is("patient"))
                // verify normal range
                .body("data.normalRangeFrom", is("90/60"))
                // verify normal range
                .body("data.normalRangeTo", is("129/84"))
                // verify systolic community average
                .body("data.communityAverageSystolic", is(132))
                // verify diastolic community average
                .body("data.communityAverageDiastolic", is(81));


    }
}
