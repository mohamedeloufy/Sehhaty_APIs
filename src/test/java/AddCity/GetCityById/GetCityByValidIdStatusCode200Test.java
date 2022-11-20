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
import static org.hamcrest.Matchers.hasSize;

public class GetCityByValidIdStatusCode200Test extends TestBase {
    String getCitiesListEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetCitiesEndPoint");
    Long nationalID = 1013178395L;
    String password = "Qwerty123";

    @DataProvider(name = "citiesData")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "cities1");
    }

    @Epic("Add City")
    @Feature("Add address manually")
    @Story("As A user i want to add city to be my location")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that if user enter valid id API return Status code 200")
    @Link("www.facebook.com")
    @Test(dataProvider = "citiesData")
    public void getCityByValidIdStatusCode200(long IdValue, String EnglishCityName, String arabicCityName, long districtSize) {

        given()
                //Log request
                .log().all()
                // Request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications(nationalID, password, sehatayApiKey, URI.create(sehatayQABaseURI)))
                // path param id and its values
                .pathParam("id", "id").pathParam("IdValue", IdValue)
                // get city list end point
                .get(getCitiesListEndPoint + "/{id}/{IdValue}").then()
                //log response
                .log().all()
                // Verify that status code = 200
                .statusCode(200)
                // Verify that each id contain the correct city name english and arabic
                .body("id", equalTo((int) IdValue))
                // Verify that correct english city name with correct id
                .body("name", equalTo(EnglishCityName))
                // Verify that correct arabic city name with correct id
                .body("name_arabic", equalTo(arabicCityName))
                // Verify that the total number of district in each city
                .body("districts", hasSize((int) districtSize));


    }
}
