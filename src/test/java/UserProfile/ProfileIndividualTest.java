package UserProfile;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ProfileIndividualTest extends TestBase {


    String getProfileDataEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetProfileDataEndPoint");


    @DataProvider(name = "profileData_StatusCode200")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }


    @Epic("profile individuals")
    @Feature("profile info")
    @Story("as a user i want my profile data")
    @Link("www.google.com")
    @Severity(SeverityLevel.BLOCKER)
    @Description("verify that user profile api return user data")
    @Test(dataProvider = "profileData_StatusCode200")
    public void profileData_StatusCode200(long national_id, String password) {

        given()
                .log().all()
                .spec(createRequestSpecifications
                        (national_id, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                .get(getProfileDataEndPoint)
                .then()
                .log().all()
                .statusCode(200)
                .body("data.second_name_arabic", notNullValue())
                .body("data.full_name_arabic", notNullValue())
                .body("data.nationality.code", notNullValue())
                .body("data.nationality.id", not(notANumber()))
                .body("data.nationality.name", notNullValue())
                .body("data.nationality.name_arabic", notNullValue())
                .body("data.date_of_birth", notNullValue())
                .body("data.is_underaged", notNullValue())
                .body("data.gender", not(emptyOrNullString()))
                .body("data.phone_number", notNullValue())
                .body("data.full_name_english", not(emptyOrNullString()));
    }

}