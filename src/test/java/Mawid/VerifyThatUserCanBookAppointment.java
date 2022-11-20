package Mawid;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class VerifyThatUserCanBookAppointment extends TestBase {

    String Book_AppointmentEndpoint = loadPropertiesEndPoints.endPoint.getProperty("Book_Appointment_EndPoint");

    @DataProvider(name = "bookAppointmentData",parallel = true)
    public static Object[][] User() {
        return new Object[][]
                {
                        {"1013178395", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},
                        /*{"1066700764", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},
                        {"1066116672", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},
                        {"1082885854", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},
                        {"1078011283", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},
                        {"1035505401", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},
                        {"1007913542", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},
                        {"1012890065", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},
                        {"1000555860", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},
                        {"1064608076", "Qwerty123", "SMS", 1,"Fever and Headache", 12, 20172886, true, "", "2021-12-24", "08:40:10", "08:55:10"},*/

                };
    }

    @Epic("Mawid")
    @Feature("Book appointment")
    @Story("As a user i want to book appointment")
    @Description("book appointment")
    @Severity(SeverityLevel.CRITICAL)
    @Link("www.google.com")
        @Test(dataProvider = "bookAppointmentData")
        public void book_appointment (Long national_id, String password, String communicationMode,
        int facilityId, String reasonForAppointment,int serviceId, int slotId
            ,boolean waitListFlag, String hisSlotId, String date, String startTime, String endTime)
        {


            Map<String,Object> body = new HashMap<>();
            body.put("communicationMode",communicationMode);
            body.put("facilityId",facilityId);
            body.put("reasonForAppointment",reasonForAppointment);
            body.put("serviceId",serviceId);
            body.put("slotId",slotId);
            body.put("waitListFlag",waitListFlag);
            body.put("hisSlotId",hisSlotId);
            body.put("date",date);
            body.put("startTime",startTime);
            body.put("endTime",endTime);


            given()
                    .log().all()
                    .spec(requestSpec)
                    .headers("Authorization","Bearer "
                            + verifyOTPToGetAccessToken(national_id,password))
                    .body(body)
                    .post(Book_AppointmentEndpoint)
                    .then()
                    .log().all()
                    .statusCode(200);


        }


}
