package SearchForDrug;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ValidDrugNameTest extends TestBase {

    long national_id = 1013178395;
    String password = "Qwerty123";
    String drugsEndpoint = loadPropertiesEndPoints.endPoint.getProperty("Get_Drug_in_search_EndPoint");


    @DataProvider(name = "drugs")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "Drugs");
    }

    @Epic("Drugs")
    @Feature("search for drug")
    @Story("as a user i want to search for drug and get information")
    @Description()
    @Link()
    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "drugs")
    public void SearchWithValidDrugNameStatusCode200(String drugName, String GenericName, String package_type) {

        given()
                .log().all()
                .spec(createRequestSpecifications(national_id, password,
                        leanAPIKEY, URI.create(leanBaseURI)))
                .queryParam("name", drugName)
                .get(drugsEndpoint)
                .then().log().all()
                // Verify on status code == 200
                .statusCode(200)
                // Verify that data not equal null value
                .body("data", notNullValue())
                // Verify that strength value not equal null value
                .body("data.strength_value", notNullValue())
                // Verify that generic name equal expected result
                .body("data[0].generic_name", equalTo(GenericName.toUpperCase()))
                // verify that package type equal to the expected result
                .body("data[0].package_type", equalTo(package_type))
                .body("data.name", notNullValue())
                .body("data.manufacturer_name", not(emptyOrNullString()));

    }
}
