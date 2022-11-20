package HealthProfile.prescriptions;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;

public class GetActivePrescriptionStatusCode200Test extends TestBase {

    String prescriptionEndPoint= loadPropertiesEndPoints.endPoint.getProperty("PrescriptionEndPoint");



    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Test(dataProvider = "Users")
    public void getActivePrescriptions_statusCode200(long nationalId,String password)
    {
        given()
                .log().all()
                .spec(createRequestSpecifications(nationalId,password,
                        sehatayApiKey, URI.create(sehatayQABaseURI)))
                .queryParam("isActive",1)
                .queryParam("page_number",1)
                .get(prescriptionEndPoint)
                .then()
                .log().all()
                .statusCode(200);
    }





}
