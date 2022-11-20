package Mawid;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class MyAppointmentWithValidDateFromDateToTest extends TestBase {

    String getAppointmentEndPoint= loadPropertiesEndPoints.endPoint.getProperty("Get_appointment_EndPoint");

    @DataProvider(name = "getAppointmentData",parallel = true)
    public static Object[][] User() {
        return new Object[][]
                {
                        {"1013178395","Qwerty123","1900-01-10","2022-01-24"},{"1066700764","Qwerty123","1900-01-10","2022-07-01"},
                        {"1066116672","Qwerty123","2022-07-01","2022-08-01"},{"1082885854","Qwerty123","2023-01-10","2023-02-28"}

                };
    }


    @Epic("Mawid")
    @Feature("view my  appointment")
    @Story("As a user i want to view my appointment")
    @Description("as a user i want to view my appointment")
    @Severity(SeverityLevel.CRITICAL)
    @Link("www.google.com")
    @Test(dataProvider = "getAppointmentData")
    public void Verify_That_User_Can_See_His_Appointment_With_valid_Date_From_To_Date_To(Long national_id,String password,String date_from,String date_to)
    {
        given()
                .log().all()
                .spec(requestSpec)
                .headers("Authorization","Bearer "
                        + verifyOTPToGetAccessToken(national_id,password))
                .queryParam("dateFrom",date_from)
                .queryParam("dateTo",date_to)

                .get(getAppointmentEndPoint)
                .then()
                .log().all()
                .statusCode(200)
                .body("data.previousAppointment",notNullValue())
                .body("data.upcomingAppointment",notNullValue());

    }

}
