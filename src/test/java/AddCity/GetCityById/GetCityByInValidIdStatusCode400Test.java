package AddCity.GetCityById;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetCityByInValidIdStatusCode400Test extends TestBase {

    String getCityListEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetCitiesEndPoint");

    @DataProvider(name = "InvalidId")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "InValidCityId");
    }


    @Epic("Add City")
    @Feature("Add address manually")
    @Story("As A user i want to add city to be my location")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that if user enter invalid city id API return Status code : 400")
    @Link("Test case link in jira")
    @Test(dataProvider = "InvalidId")
    public void getCityByInValidId_StatusCode400(long IdValue, String errorMessage) {
        long national_id = 1013178395L;
        String password = "Qwerty123";


        given()
                //Log request
                .log().all()
                // Request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications(national_id, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                // path parameters id and its value
                .pathParam("id", "id").pathParam("IdValue", IdValue)
                // get city end point
                .get(getCityListEndPoint + "/{id}/{IdValue}").then()
                // log response
                .log().all()
                // Verify that status code == 400
                .statusCode(400)
                // Verify that error message == No results found
                .body("Message", equalTo(errorMessage));
    }
}