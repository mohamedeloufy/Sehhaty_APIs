package AddCity.GetCityByPoint;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetCityByPointInsideStatusCode200Test extends TestBase {


    long nationalId = 1013178395L;
    String password = "Qwerty123";
    String getCitiesListEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetCitiesEndPoint");

    @DataProvider(name = "Points")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "PointInside");
    }

    @Epic("Add City")
    @Feature("Add address manually")
    @Story("As A user i want to add city to be my location")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that if user enter point inside saudi arabia api return status code 200 ")
    @Link("www.google.com")
    @Test(dataProvider = "Points", dataProviderClass = GetCityByPointInsideStatusCode200Test.class)
    public void pointInsideSaudiArabia_StatusCode200(String LongLat, String cityEnglishName, String cityArabicName, long districtSize) {

        given()

                //log request
                .log().all()
                // Request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications(nationalId, password, sehatayApiKey, URI.create(sehatayQABaseURI))).urlEncodingEnabled(false)
                // path parameter point and its lat and long
                .pathParam("point", "point").pathParam("Long", LongLat)
                // get city list end point
                .get(getCitiesListEndPoint + "/{point}/{Long}").then()
                // log response
                .log().all()
                // Verify that status code == 200
                .statusCode(200)
                // Verify that the english city name of this point
                .body("name", equalTo(cityEnglishName))
                // Verify that the arabic city name of this point
                .body("name_arabic", equalTo(cityArabicName))
                // Verify that the total number of district in this city
                .body("districts", hasSize((int) districtSize))
                // Verify that total number of district ids equal to the total number of district
                .body("districts.id", hasSize((int) districtSize))
                // Verify that district id is number only
                .body("districts.id", not(notANumber()))
                // Verify that district id not null value
                .body("districts.id", notNullValue())
                // Verify that total number of district english name equal to the total number of district
                .body("districts.name", hasSize((int) districtSize))
                // Verify that district english name not empty or null value
                .body("districts.name", not(emptyOrNullString()))
                // Verify that district arabic name not empty or null value
                .body("districts.name_arabic", not(emptyOrNullString()))
                // Verify that total number of district arabic name equal to the total number of district
                .body("districts.name_arabic", hasSize((int) districtSize));


    }
}
