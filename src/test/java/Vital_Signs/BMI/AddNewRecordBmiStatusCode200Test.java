package Vital_Signs.BMI;

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

public class AddNewRecordBmiStatusCode200Test extends TestBase {


    String AddBMIEndPoint = loadPropertiesEndPoints.endPoint.getProperty("AddBMIEndpoint");

    @DataProvider(name = "bmi")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "bmi");
    }


    @Epic("vital signs")
    @Feature("Add BMI")
    @Story("as a user i want add weight and height")
    @Link("www.google.com")
    @Severity(SeverityLevel.BLOCKER)
    @Description("verify that user can add weight and height")
    @Test(dataProvider = "bmi")
    public void addNewBMI_StatusCode200(long national_id, String password, long height, long weight) {

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("height", height);
        hashMap.put("weight", weight);

        given()
                .log().all()
                .spec(createRequestSpecifications(national_id, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                // valid reading
                .body(hashMap)
                .when()
                // Vital Signs end point
                .post(AddBMIEndPoint)
                .then()
                // log response
                .log().all()
                // verify status code =200
                .statusCode(200)
                .body("message", is("Reading added successfully"));
        // verify average


    }
}
