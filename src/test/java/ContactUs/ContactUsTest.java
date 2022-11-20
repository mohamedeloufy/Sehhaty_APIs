package ContactUs;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ContactUsTest extends TestBase {

    String contactUs= loadPropertiesEndPoints.endPoint.getProperty("Contact_Us_EndPoint");

    @DataProvider(name = "ContactUs",parallel = true)
    public static Object[][]users()
    {
        return new Object[][]
                {
                        {"1013178395","Qwerty123","","","","","",""},
                        {"1066116672","Qwerty123","","","","","",""},
                        {"1078011283","Qwerty123","","","","","",""},
                        {"1007913542","Qwerty123","","","","","",""},
                        {"1000555860","Qwerty123","","","","","",""},
                        {"1048050171","Qwerty123","","","","","",""}
                };
    }

    @Test(dataProvider = "ContactUs")
    public void Verify_That_User_Can_Contact_Us(Long National_id,String password,String facility_name,String visit_date,String subject,String discharge_date,String medical_record_number,String description)
    {
        Map<String,Object> jsonMap=new HashMap<>();
        jsonMap.put("facility_name",facility_name);
        jsonMap.put("visit_date",visit_date);
        jsonMap.put("subject",subject);
        jsonMap.put("discharge_date",discharge_date);
        jsonMap.put("medical_record_number",medical_record_number);
        jsonMap.put("description",description);
        JSONObject obj=new JSONObject(jsonMap);

        given()
                .log().all()
                .spec(createRequestSpecifications(National_id,password,"",URI.create("")))
                .body(obj).log().all()
                .post(contactUs)
                .then()
                .log().all()
                .statusCode(200).log().all();



    }
}
