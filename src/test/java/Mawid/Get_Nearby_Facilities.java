package Mawid;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class Get_Nearby_Facilities extends TestBase {



    String getNearbyFacilities= loadPropertiesEndPoints.endPoint.getProperty("Get_nearby_facilities");


    @DataProvider(name = "GetNearbyFacilities",parallel = true)
    public static Object[][] User() {
        return new Object[][]
                {
                        {"1013178395","Qwerty123","25.30102","45.95794","100"},{"1066700764","Qwerty123","25.30102","45.95794","0"},
                        {"1066116672","Qwerty123","25.30102","45.95794","50"},{"1082885854","Qwerty123","25.30102","45.95794","150"},
                        {"1078011283","Qwerty123","25.30102","45.95794","200"},{"1035505401","Qwerty123","25.30102","45.95794","250"},
                        {"1007913542","Qwerty123","25.30102","45.95794","300"},{"1012890065","Qwerty123","25.30102","45.95794","1000"},
                        {"1000555860","Qwerty123","25.30102","45.95794","2000"},{"1064608076","Qwerty123","25.30102","45.95794","10000"}

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
                .get(getNearbyFacilities)
                .then()
                .log().all()
                .statusCode(200);

    }
}
