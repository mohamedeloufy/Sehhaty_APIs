package Asafany;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.HashMap;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;

public class InsertIncidentStatusCode201Test extends TestBase {

    String PostIncident= loadPropertiesEndPoints.endPoint.getProperty("InsertIncident");

    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "InsertIncident");
    }

    @Test(dataProvider = "Users")
    public void insertIncident_StatusCode200(long national_id,String password,
                                             boolean isForOther,double age,double sex,String callerName,
                                             String incidentDetails,double lat,double patientsCount,
                                             String lang,boolean isAndroid,double lng,double incidentTypeID)
    {

        HashMap<Object,Object> request =new HashMap<>();
        request.put("national_id",national_id);
        request.put("isForOther",isForOther);
        request.put("age",(int)age);
        request.put("sex",(int)sex);
        request.put("callerName",callerName);
        request.put("incidentDetails",incidentDetails);
        request.put("lat",lat);
        request.put("patientsCount",(int)patientsCount);
        request.put("lang",lang);
        request.put("isAndroid",isAndroid);
        request.put("lng",lng);
        request.put("incidentTypeID",(int)incidentTypeID);


        given()
                .log().all()
                .spec(createRequestSpecifications(national_id,password,
                        sehatayApiKey, URI.create(sehatayQABaseURI)))
                .body(request)
                .post(PostIncident)
                .then()
                .log().all()
                .statusCode(200);
    }
}
