package Register;

import TestBases.TestBase;
import org.testng.annotations.Test;

import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyOrNullString;

public class trys extends TestBase {

    @Test
    public void verifyThatUserGetAllCitiesInSaudiArabiaByValidNationalIdStatusCode200(Long national_id, String password)
    {
        given()
                .log().all()
                .spec(createRequestSpecifications
                        (national_id,password,sehatayApiKey,
                                URI.create(sehatayQABaseURI)))
                .get("/sehhaty/health-summary/get-insurance-approval")
                .then()
                .log().all()
                // Verify that status code == 200
                .statusCode(200)
                // Verify that data array contain 100 item
                .body("data",hasSize(100))
                // Verify that total numbers of ids == 100
                .body("data.id",hasSize(100))
                // Verify that total number of cities name in english language == 100
                .body("data.name",hasSize(100))
                // Verify that total number of cities name in arabic language == 100
                .body("data.name_arabic",hasSize(100))
                // Verify that value of id is a number only
                .body("data.id",not(notANumber()))
                // Verify that id value not null
                .body("data.id",notNullValue())
                // Verify that value of city name in english language not empty or null value
                .body("data.name",not(emptyOrNullString()))
                // Verify that value of city name in arabic language not empty or null value
                .body("data.name_arabic",not(emptyOrNullString()));

    }
}
