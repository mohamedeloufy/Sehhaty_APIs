package Dependent;


import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;


import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApproveDependentRequestTest extends TestBase {

    String request_approval_to_add_dependent= loadPropertiesEndPoints.
            endPoint.getProperty("Request_approval_to_add_dependent_EndPoint");


    @DataProvider(name = "usersToApprove")
    public static Object[][] User() {
        return new Object[][]
                {
                        {"1013178395","Qwerty123"},
                        {"1066700764","Qwerty123"}
                };
    }


    @Epic("Dependent")
    @Feature("Retrieve  dependent request approval")
    @Story("as a user i want to get my request approval")
    @Description("Verify that user can approve dependent request")
    @Severity(SeverityLevel.BLOCKER)
    @Link("www.google.com")
    @Test(dataProvider ="usersToApprove")
    public void Verify_That_User_Can_Approve_Dependent_Request(long nationalID,String password)
    {

        Map<String,String> jsonMap = new HashMap<>();
        jsonMap.put("national_id","1031794629");
        jsonMap.put("birth_date","1978-09-22");
        jsonMap.put("relation","2");
        JSONObject obj = new JSONObject(jsonMap);


        given().log().all()
                .spec(createRequestSpecifications(nationalID,password,"",URI.create("")))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON).body(obj)
                .post(request_approval_to_add_dependent)
                .then()
                .log().all()
                .statusCode(200);

    }
}
