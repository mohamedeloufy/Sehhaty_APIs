package Mawid;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MyAppointmentWithInValidDateFromDateToTest extends TestBase {

    String getAppointmentEndPoint= loadPropertiesEndPoints.endPoint.getProperty("Get_appointment_EndPoint");

    @DataProvider(name = "getAppointmentData",parallel = true)
    public static Object[][] User() {
        return new Object[][]
                {
                        {"1013178395","Qwerty123","2023-02-29","2021-01-10"},
                        {"1013178395","Qwerty123","2021-01-10","2023-02-29"},
                        {"1066700764","Qwerty123","20-01-10","2022-12-50"},
                        {"1066700764","Qwerty123","20O1-01-58","2022-12-05"},
                        {"1066116672","Qwerty123","2021-01-10","2023-02-50"},
                        {"1078011283","Qwerty123","2025-01-10","2024-02-28"},
                        {"1007913542","Qwerty123","","2020-01-10"},
                        {"1007913542","Qwerty123","2020-01-10",""},
                        {"1000555860","Qwerty123","2000-01-10","2026-24-01"},

                };
    }






    @Epic("Mawid")
    @Feature("view my  appointment")
    @Story("As a user i want to view my appointment")
    @Description("invalid id send to check the error of view my appointment")
    @Severity(SeverityLevel.CRITICAL)
    @Link("www.google.com")
    @Test(dataProvider = "getAppointmentData")
    public void get_my_appointment(Long national_id,String password,String date_from,String date_to)
    {
        ValidatableResponse validatableResponse=given()
                .log().all()
                .spec(requestSpec)
                .headers("Authorization","Bearer "
                        + verifyOTPToGetAccessToken(national_id,password))
                .queryParam("dateFrom",date_from)
                .queryParam("dateTo",date_to)
                .get(getAppointmentEndPoint)
                .then()
                .log().all()
                .statusCode(400);
        if (date_from.equals("2025-01-10"))
        {
            validatableResponse.body("Message",equalTo("Server Side Validation Failed, DateComparisonValue : The End date should be after the starting date.."));
        }else if (date_from.equals(""))
        {
            validatableResponse.body("Message",equalTo("'Date From' must not be empty."));
        } else if (date_to.equals(""))
        {
            validatableResponse.body("Message",equalTo("'Date To' must not be empty."));
        } else if (date_from.equals("2023-02-29")|| date_to.equals("2023-02-29"))
        {
            validatableResponse.body("Message",equalTo("Server Side Validation Failed, Required request body is missing : Required request body is missing."));
        } else
        {
            validatableResponse.body("Message",equalTo("GeneralError"));
        }


    }

}
