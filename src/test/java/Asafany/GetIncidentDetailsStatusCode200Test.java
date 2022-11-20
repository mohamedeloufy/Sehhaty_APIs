package Asafany;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static io.restassured.RestAssured.given;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static org.hamcrest.Matchers.*;

public class GetIncidentDetailsStatusCode200Test extends TestBase {
    String getIncidentDetails= loadPropertiesEndPoints.endPoint.getProperty("GetIncidentDetails");
    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Test//(dataProvider = "Users")
    public void getIncidentDetails_StatusCode200()
    {
        given()
                .log().all()
                .spec(createRequestSpecifications(1013178395l,"Qwerty123",
                        sehatayApiKey, URI.create(sehatayQABaseURI))).pathParam("id","286618")
                .get(getIncidentDetails +"/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("requestInfo.creationTime",notNullValue())
                .body("requestInfo.incidentTypeID",notNullValue())
                .body("requestInfo.incidentLng",not(emptyOrNullString()))
                .body("requestInfo.incidentLat",not(emptyOrNullString()));
    }
}
