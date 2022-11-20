package HealthProfile.Labs;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;

public class GetLabsStatusCode200Test extends TestBase {
    String getLabs =
            loadPropertiesEndPoints.endPoint.getProperty("GetLabs");


    @DataProvider(name = "Users")
    public static Object[][] excelDP() {
        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }



    @Test(dataProvider = "Users")
    public void getLabs_StatusCode200(long national_id,String password) {
        given()
                .log().all()
                .spec(createRequestSpecifications(
                        national_id,password,sehatayApiKey, URI.create(sehatayQABaseURI)))
                .queryParam("page_number",1)
                .get(getLabs)
                .then()
                .log().all()
                .statusCode(200);

    }

}
