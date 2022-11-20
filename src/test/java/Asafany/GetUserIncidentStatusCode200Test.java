package Asafany;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetUserIncidentStatusCode200Test extends TestBase {

    String getUserIncident=loadPropertiesEndPoints.endPoint.getProperty("GetUserIncident");


    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Test(dataProvider = "Users")
    public void getUserIncident(long nationalId,String password)
    {
        given()
                .log().all()
                .spec(createRequestSpecifications(nationalId,password,
                        sehatayApiKey, URI.create(sehatayQABaseURI)))
                .get(getUserIncident)
                .then().log().all()
                .statusCode(200)
                .body("status",is(1))
                .body("incidentsList",notNullValue());
    }


}
