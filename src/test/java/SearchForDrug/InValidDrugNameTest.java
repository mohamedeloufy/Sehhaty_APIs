package SearchForDrug;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class InValidDrugNameTest extends TestBase {

    long national_id = 1013178395;
    String password = "Qwerty123";
    String drugs_endpoint = loadPropertiesEndPoints.endPoint.getProperty("Get_Drug_in_search_EndPoint");

    @DataProvider(name = "InValidDrugs")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "InValidDrugNames");
    }

    @Epic("Drugs")
    @Feature("search for drug")
    @Story("as a user i want to search for drug and get information")
    @Description()
    @Link()
    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "InValidDrugs")
    public void SearchWithInvalidDrugNameStatusCode200(String drugName) {
        given()
                .log().all()
                .spec(createRequestSpecifications
                        (national_id, password, leanAPIKEY, URI.create(leanBaseURI)))
                .queryParam("name", drugName)
                .get(drugs_endpoint)
                .then()
                .log().all()
                // Verify that status code equal 200
                .statusCode(200)
                // Verify that marketing status == undefined
                .body("data[0].marketing_status", equalTo("undefined"))
                // Verify that shelf life == undefined
                .body("data[0].shelf_life", equalTo("undefined"))
                // Verify that shelf life == undefined
                .body("data[0].legal_status", equalTo("undefined"));
    }
}
