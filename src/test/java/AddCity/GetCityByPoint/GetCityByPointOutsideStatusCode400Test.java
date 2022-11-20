package AddCity.GetCityByPoint;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetCityByPointOutsideStatusCode400Test extends TestBase {

    String getCitiesListEndpoint = loadPropertiesEndPoints.endPoint.getProperty("GetCitiesEndPoint");

    @DataProvider(name = "PointsOutSide")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "PointOutSide");
    }


    @Epic("Add City")
    @Feature("Add address manually")
    @Story("As A user i want to add city to be my location")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that if user enter point outside saudi arabia API return status code 400 ")
    @Link("www.fast.com")
    @Test(dataProvider = "PointsOutSide", dataProviderClass = GetCityByPointOutsideStatusCode400Test.class)
    public void pointOutsideStatusCode400(String LongLat, String errorMessage) {
        long nationalId = 1013178395L;
        String Password = "Qwerty123";


        given()
                // log request
                .log().all().spec(createRequestSpecifications(nationalId, Password, sehatayApiKey, URI.create(sehatayQABaseURI))).urlEncodingEnabled(false)
                // path param point and its value lat and long
                .pathParam("point", "point").pathParam("Long", LongLat)
                // get city endpoint
                .get(getCitiesListEndpoint + "/{point}/{Long}").then()
                // log response
                .log().all().contentType(ContentType.JSON)
                // Verify that status code ==400
                .statusCode(400).statusLine("HTTP/1.1 400 Bad Request")
                // Verify that error message of point outside ==Point is outside Saudi Arabia, please try again
                .body("Message", equalTo(errorMessage));

    }

}
