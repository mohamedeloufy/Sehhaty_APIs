package HealthProfile.PersonalInformation;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class PatchPersonalInformationTest extends TestBase {

    String getProfileEndPoint = loadPropertiesEndPoints.endPoint.getProperty("GetProfileDataEndPoint");
    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UpdatePersonalInformation");
    }

    @Test(dataProvider = "Users")
    public void updatePersonalInformation(long nationalId,String password,String email,long maritalStatus)
    {

        String randomEmail=randomEmail();
        HashMap<Object,Object>hasMap=new HashMap<>();
        hasMap.put("marital_status_id",(int)maritalStatus);
        hasMap.put("email",randomEmail);


        given()
                .log().all()
                .spec(createRequestSpecifications(nationalId,password,sehatayApiKey,
                        URI.create(sehatayQABaseURI)))
                .body(hasMap)
                .patch(getProfileEndPoint).then()
                .log().all()
                .statusCode(200)
                .body("marital_status_id",is((int)maritalStatus))
                .body("email",is(randomEmail));
    }
}
