package Dependent;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ValidDependentInformationStatusCode200Test extends TestBase {
    String getDependentInfoFromYakeenEndPoint = loadPropertiesEndPoints.
            endPoint.getProperty("Get_dependent_info_from_yakeen_EndPoint");

    @DataProvider(name = "validRelations")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "ValidDependentInformation");
    }




    @Epic("Dependent")
    @Feature("Retrieve  dependent info (wife or husband)")
    @Story("as a user i want to get the my (wife husband) dependent info ")
    @Description("Verify that user can get his or her dependent")
    @Severity(SeverityLevel.BLOCKER)
    @Link("www.google.com")
    @Test(dataProvider = "validRelations")
    public void getValidDependentInformation_StatusCode200(long national_id, String password, String relations)
    {

        ValidatableResponse validatableResponse = given()
                .log().all()
                .spec(createRequestSpecifications(national_id,password,
                        sehatayApiKey, URI.create(sehatayQABaseURI)))
                .queryParam("relation",relations)
                .get(getDependentInfoFromYakeenEndPoint)
                .then()
                .log().all()
                .statusCode(200);
        if(relations.equals("HU"))
                {
                    validatableResponse.body("data.size()",not(greaterThan(1)));
                }else if (relations.equals("WI"))
                {
                    validatableResponse.body("data.size()",not(greaterThan(4)));
                }

    }
}


