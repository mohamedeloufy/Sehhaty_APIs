package TestBases;

import ReadDataFromFiles.LoadProperties_BaseUrls;
import ReadDataFromFiles.LoadProperties_Verify_otp;
import ReadDataFromFiles.loadPropertiesEndPoints;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.net.URI;
import java.util.HashMap;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class TestBase {


    public static RequestSpecification requestSpec;


    // Those strings come from properties file to avoid enter data as a hard coded and help us to change it easily
    public String sehatayQABaseURI = LoadProperties_BaseUrls.BaseUrls.getProperty("QaBaseURI");
    public String sehatayDevBaseURI = LoadProperties_BaseUrls.BaseUrls.getProperty("DevBaseURI");
    public String leanBaseURI = LoadProperties_BaseUrls.BaseUrls.getProperty("LeanBaseURI");
    public String sehatayApiKey = LoadProperties_BaseUrls.BaseUrls.getProperty("APIKEY");
    public String leanAPIKEY = LoadProperties_BaseUrls.BaseUrls.getProperty("LeanAPIKey");
    public String loginEndpoint = loadPropertiesEndPoints.endPoint.getProperty("Login_Endpoint");
    public String verifyOtpEndPoint = LoadProperties_Verify_otp.verify_otp.getProperty("Verify_otp_EndPoint");

    public String code = LoadProperties_Verify_otp.verify_otp.getProperty("Code");


    // This Methode Used for getting the Identifier value by entering national id and password
    public String loginToGetIdentifier(long national_id, String password) {

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("national_id", national_id);
        hashMap.put("password", password);
        baseURI = sehatayQABaseURI;
        return given().log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(hashMap)
                .when()
                .post(loginEndpoint)
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("identifier");


    }

    //This Method Used for getting the value of access token by entering identifier id
    public String verifyOTPToGetAccessToken(long national_id, String password) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("code", code);
        hashMap.put("identifier", loginToGetIdentifier(national_id, password));
        baseURI = sehatayQABaseURI;
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(hashMap)
                .when()
                .post(verifyOtpEndPoint)
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("access_token");


    }

    public RequestSpecification createRequestSpecifications(long national_id, String password, String API_KEY, URI baseURI) {

        HashMap<String, Object> request = new HashMap<>();
        request.put("X-authorizationType", "JWT");
        request.put("Authorization", "Bearer " + verifyOTPToGetAccessToken(national_id, password));
        request.put("apikey", API_KEY);
        requestSpec = new RequestSpecBuilder()

                .setBaseUri(baseURI)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build().headers(request);
        return requestSpec;
    }

    public String randomEmail() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }

}
