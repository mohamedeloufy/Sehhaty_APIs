package HealthProfile.MedicalReport;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;

public class GetMedicalReportStatusCode200Test extends TestBase {

    String medicalReport = loadPropertiesEndPoints.endPoint.getProperty("GetMedicalReport");


   /* @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }*/

    @Test//(dataProvider = "Users")
    public void getMedicalReport_StatusCode200() {
        given()
                .log().all()
                .spec(createRequestSpecifications(
                        1085733739l,"Qwerty123",sehatayApiKey, URI.create(sehatayQABaseURI)))
                .get(medicalReport)
                .then()
                .log().all()
                .statusCode(200);

    }
}
