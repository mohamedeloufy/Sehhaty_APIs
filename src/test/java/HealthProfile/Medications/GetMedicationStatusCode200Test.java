package HealthProfile.Medications;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;

public class GetMedicationStatusCode200Test extends TestBase {
    String medicationEndpoint = loadPropertiesEndPoints.endPoint.getProperty("MedicationEndPoint");


    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Test(dataProvider = "Users")
    public void getMedications_StatusCode200(long nationalId, String password) {
        given()
                .log().all()
                .spec(createRequestSpecifications(nationalId, password,
                        sehatayApiKey, URI.create(sehatayQABaseURI)))
                .get(medicationEndpoint).then()
                .log().all()
                .statusCode(200);
    }
}
