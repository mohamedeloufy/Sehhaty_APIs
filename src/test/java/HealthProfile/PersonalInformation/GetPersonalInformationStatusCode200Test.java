package HealthProfile.PersonalInformation;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetPersonalInformationStatusCode200Test extends TestBase {

    String getProfileEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetProfileDataEndPoint");


    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Test(dataProvider = "Users")
    public void getPersonalInformation_StatusCode200(long nationalId, String password) {
        given()
                .log().all()
                .spec(createRequestSpecifications
                        (nationalId, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                .get(getProfileEndPoint).then().log().all()
                .statusCode(200)
                .body("data.id",notNullValue())
                .body("data.nationality.id",notNullValue())
                .body("data.nationality.name",notNullValue())
                .body("data.nationality.code",notNullValue())
                .body("data.nationality.name_arabic",notNullValue())
                .body("data.full_name_english",notNullValue())
                .body("data.full_name_arabic",notNullValue())
                .body("data.national_id",is(String.valueOf(nationalId)))
                .body("data.date_of_birth",notNullValue())
                .body("data.phone_number",notNullValue())
                .body("data.email",notNullValue())
                .body("data.marital_status_id",notNullValue());
    }
}
