package AddCity.GetCityList;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetCityListStatusCode200Test extends TestBase {

    // Get city list end point
    String getCityListEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetCitiesEndPoint");


    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Epic("Add City")
    @Feature("Add address manually")
    @Story("As A user i want to add city to be my location")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can get all cities in saudi arabia ")
    @Link("Test case link in jira")

    @Test(dataProvider = "Users", dataProviderClass = GetCityListStatusCode200Test.class)
    public void getCityList__StatusCode200(long nationalId, String password) {

        given()
                // Log request
                .log().all()
                // Request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications(nationalId, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                // Get city list endpoint
                .get(getCityListEndPoint).then()
                // Log response
                .log().all()
                // Verify that status code == 200
                .statusCode(200)
                // Verify that data key contain 100 item
                .body("data", hasSize(100))
                // Verify that total numbers of ids == 100
                .body("data.id", hasSize(100))
                // Verify that total number of cities name in english language == 100
                .body("data.name", hasSize(100))
                // Verify that total number of cities name in arabic language == 100
                .body("data.name_arabic", hasSize(100))
                // Verify that value of id is a number only
                .body("data.id", not(notANumber()))
                // Verify that id value not null
                .body("data.id", notNullValue())
                // Verify that value of city name in english language not empty or null value
                .body("data.name", not(emptyOrNullString()))
                // Verify that value of city name in arabic language not empty or null value
                .body("data.name_arabic", not(emptyOrNullString()))
                //verify city id number 2
                .body("data.id[1]", is(2))
                // Verify English city name number 11
                .body("data.name[10]", is("HAFAR AL BATIN"))
                // Verify arabic city name number 51
                .body("data.name_arabic[50]", is("الخرج"));

    }
}

