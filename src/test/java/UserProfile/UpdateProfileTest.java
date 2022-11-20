package UserProfile;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UpdateProfileTest extends TestBase {


    String get_profile_data= loadPropertiesEndPoints.endPoint.getProperty("GetProfileDataEndPoint");


    @DataProvider(name = "updateProfileData_StatusCode200")
    public static Object[][] User() {
        return new Object[][]
                {
                        {1013178395l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1066700764l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1066116672l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1082885854l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1078011283l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1035505401l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1007913542l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1012890065l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1000555860l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1064608076l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1048050171l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1004147474l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1094709340l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1032735688l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1013605314l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1034854040l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1062041973l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1087309819l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1072689498l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1089449928l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1128406160l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1095283469l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1069407243l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1096229750l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1077877221l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1056506023l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1088603095l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1065522391l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1078436159l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1097840647l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1037983192l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1089722639l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1032199810l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1030092256l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1090774017l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1067880011l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1072590720l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1084493418l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1077152179l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1084580537l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1013796535l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1063613473l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1084551728l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1095953699l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"},
                        {1090884501l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}, {1066297324l, "Qwerty123",1,2,3,"AD DAMMAM","TUMAIR","Al-Sahafa"}

                };

    }



    @Epic("profile individuals")
    @Feature("update profile info")
    @Story("as a user i want to update my profile data")

    @Test(dataProvider = "updateProfileData_StatusCode200")
    public void updateProfileData_StatusCode200(long national_id, String password, int city_id, int district_id, int healthcare_center_id, String cityName , String districtName, String healthcare_center_name)
    {

        Map<String,Object> jsonMap=new HashMap<>();
        jsonMap.put("city_id",city_id);
        jsonMap.put("district_id",district_id);
        jsonMap.put("healthcare_center_id",healthcare_center_id);
        jsonMap.put("city",cityName);
        jsonMap.put("district",districtName);
        jsonMap.put("healthcare_center",healthcare_center_name);
        JSONObject obj=new JSONObject(jsonMap);




        given()
                .log().all()
                .spec(createRequestSpecifications(national_id,password,sehatayApiKey, URI.create(sehatayQABaseURI)))
                .body(obj)
                .patch(get_profile_data)
                .then()
                .log().all()
                .statusCode(200);
    }
}
