package HealthProfile.Visits;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static io.restassured.RestAssured.given;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;

public class GetOutPatientStatusCode200Test extends TestBase {

    String getCounter= loadPropertiesEndPoints.endPoint.getProperty("GetEncounter");

    @DataProvider(name = "profileData_StatusCode200")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }


    @Test(dataProvider = "profileData_StatusCode200")
    public void getOutPatient_StatusCode200(long national_id,String Password)
    {
        given()
                .log().all()
                .spec(createRequestSpecifications(
                        national_id,Password,sehatayApiKey, URI.create(sehatayQABaseURI)))
                .queryParam("page_number",1)
                .queryParam("type","OutPatient")
                .get(getCounter)
                .then()
                .log().all()
                .statusCode(200);
    }
}
