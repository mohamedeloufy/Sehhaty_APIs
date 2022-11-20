package NationalAddressFromSPL.GetNationalAddress;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetNationalAddressStatusCode200Test extends TestBase {
    String GetNationalAddressEndPoint = loadPropertiesEndPoints.
            endPoint.getProperty("GetNationalAddressEndPoint");

    @DataProvider(name = "UserHasNationalAddress")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserHasNationalAddress");
    }

    @Epic("Add Address in sehhaty application")
    @Feature("Add National Address From SPL")
    @Story("as a user i can get the national address if exist in spl")
    @Description("Verify that user can get national address from spl")
    @Severity(SeverityLevel.BLOCKER)
    @Link("testcase link")
    @Test(dataProvider = "UserHasNationalAddress")
    public void getNationalAddress__StatusCode200(long nationalID, String password) {
        given()
                // log request 
                .log().all()
                // request specification >> (national address , password , Api key , Base URL)
                .spec(createRequestSpecifications
                        (nationalID, password, sehatayApiKey,
                                URI.create(sehatayDevBaseURI)))
                // get national address endpoint 
                .get(GetNationalAddressEndPoint)
                .then()
                // log response 
                .log().all()
                // verify that api return status code 200
                .statusCode(200)
                // verify that national address exist
                .body("message", equalTo("National Address exists"))
                // verify that national address exists in Arabic language
                .body("data.SubscriptionType", equalTo("العنوان الوطنى"))
                // verify that city id is mandatory field
                .body("data.CityId", not(emptyOrNullString()))
                // verify that Arabic city name is mandatory field
                .body("data.CityNameAR", not(emptyOrNullString()))
                // verify that English city name is mandatory field
                .body("data.CityNameEN", not(emptyOrNullString()))
                // verify that DistrictAreaAR is mandatory field
                .body("data.DistrictAreaAR", not(emptyOrNullString()))
                // verify that DistrictAreaEN is mandatory field
                .body("data.DistrictAreaEN", not(emptyOrNullString()))
                // Verify is defaultAddress should equal true
                //.body("data.IsDefaultAddress", is(true))
                // verify that StreetNameAR is mandatory field
                .body("data.StreetNameAR", not(emptyOrNullString()))
                // verify that StreetNameEN is mandatory field
                .body("data.StreetNameEN", not(emptyOrNullString()))
                // verify that AdditionalNumber is mandatory field
                .body("data.AdditionalNumber", not(emptyOrNullString()))
                // verify that BuildingNumber is mandatory field
                .body("data.BuildingNumber", not(emptyOrNullString()))
                // verify that ZipCode is mandatory field
                .body("data.ZipCode", not(emptyOrNullString()))
                // verify that UnitNo is mandatory field
                .body("data.UnitNo", not(emptyOrNullString()))
                // verify that ShortAddress is mandatory field
                .body("data.ShortAddress", notNullValue());
    }

}
