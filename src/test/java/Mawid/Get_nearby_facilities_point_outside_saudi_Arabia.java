package Mawid;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class Get_nearby_facilities_point_outside_saudi_Arabia extends TestBase {



    String getNearbyFacilities= loadPropertiesEndPoints.endPoint.getProperty("Get_nearby_facilities");


    @DataProvider(name = "GetNearbyFacilities",parallel = true)
    public static Object[][] User() {
        return new Object[][]
                {
                        {"1013178395","Qwerty123","23.30102","53.95794","100"},

                };
    }


    @Epic("Mawid")
    @Feature("Get nearby facilities")
    @Story("Get nearby facilities")
    @Description("Get nearby facilities")
    @Severity(SeverityLevel.CRITICAL)
    @Link("www.google.com")
    @Test(dataProvider = "GetNearbyFacilities")
    public void get_nearby_facilities(Long National_id,String password,String latitude,String longitude,String distance)
    {





        given()
                .log().all()
                .spec(requestSpec)
                .headers("Authorization","Bearer "
                        + verifyOTPToGetAccessToken(National_id,password))
                .headers("X-Language","EN")
                .queryParam("latitude",latitude)
                .queryParam("longitude",longitude)
                .queryParam("distance",distance)
                .log().all()
                .get(getNearbyFacilities)
                .then().log().all()
                .statusCode(404);

    }
}
