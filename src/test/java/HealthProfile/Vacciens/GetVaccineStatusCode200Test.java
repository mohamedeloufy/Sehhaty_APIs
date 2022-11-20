package HealthProfile.Vacciens;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;

public class GetVaccineStatusCode200Test extends TestBase {
    String getVaccine = loadPropertiesEndPoints.endPoint.getProperty("GetCovidVaccine");

    @DataProvider(name = "profileData_StatusCode200")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Test(dataProvider = "profileData_StatusCode200")
    public void covidVaccine(long national_id, String password) {
        given()
                .log().all()
                .spec(createRequestSpecifications(national_id, password,
                        sehatayApiKey, URI.create(sehatayQABaseURI)))
                .get(getVaccine).then()
                .log().all().
                statusCode(200);
    }


}
