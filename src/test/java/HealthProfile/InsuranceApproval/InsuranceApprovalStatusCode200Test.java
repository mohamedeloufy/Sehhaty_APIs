package HealthProfile.InsuranceApproval;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;

public class InsuranceApprovalStatusCode200Test extends TestBase {
    String insuranceApprovalEndpoint = loadPropertiesEndPoints.endPoint.getProperty("InsuranceApproval");


    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Epic("Insurance approval")
    @Feature("get insurance approval")
    @Story("view insurance approval")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User can view insurance approval")
    @Link("www.facebook.com")
    @Test(dataProvider = "Users")
    public void getInsuranceApproval_StatusCode200(long nationalId, String password) {
        given()
                .log().all()
                .spec(createRequestSpecifications
                        (nationalId, password, sehatayApiKey, URI.create(sehatayDevBaseURI)))
                .get(insuranceApprovalEndpoint).then().log().all().statusCode(200);

    }
}
