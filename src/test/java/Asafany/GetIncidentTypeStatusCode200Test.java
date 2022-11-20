package Asafany;

import ReadDataFromFiles.loadPropertiesEndPoints;
import TestBases.TestBase;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static ReadDataFromFiles.GetExcelData.getDataFromExcel;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class GetIncidentTypeStatusCode200Test extends TestBase {
    String getIncidentType = loadPropertiesEndPoints.endPoint.getProperty("GetIncidentType");

    @DataProvider(name = "Users")
    public static Object[][] excelDP() {

        return getDataFromExcel("./datafiles/AutomationData.xlsx", "UserCredentials");
    }

    @Epic("Asafny")
    @Feature("")
    @Story("")
    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "Users")
    public void getIncidentType_StatusCode200(long national_id, String password) {
        given()
                .log().all()
                .spec(createRequestSpecifications(national_id, password,
                        sehatayApiKey, URI.create(sehatayQABaseURI)))
                .get(getIncidentType)
                .then().log().all()
                .statusCode(200)
                .body("data.typeNameAr", allOf(hasItems("متفجرات بأنواعها", "حريق", "استعمال سلاح ناري", "حادث تصادم", "دهس", "انقلاب سيارة", "صعق كهربائي", "سقوط", "احتجاز", "بتر", "غرق", "انهيار مبنى", "اختناق غص بالطعام", "حروق", "ازمة قلبية", "غيبوبة نقص سكر", "اغماء", "ازمة تنفسية", "مريض صرع", "حالة نفسية", "مرض معدي", "نزيف", "نزيف مهبلي", "اجهاض", "ولادة", "تسمم بالابتلاع", "تسمم بالاستنشاق", "تسمم باللدغ", "مشاجرة", "ضربة شمس", "محاولة انتحار", "اغتصاب", "استعمال آلة حادة", "تسرب غاز", "اصابة عمل", "توقف قلب وتنفس", "اشتباه جلطة دماغية")))
                .body("data.typeNameEn", allOf(hasItems("Explosives", "Fire", "Gun Fire", "Collision Accident", "Run over", "Car Overturning", "electrical shocking", "Fall", "Detention", "Amputation", "Drowning", "Building collapse", "Suffocation", "Burns", "Heart Attack", "coma", "Fainting", "Respiratory Crisis", "Epilepsy", "Psychological Crisis", "Preparers Diseases", "Bleeding", "Vaginal Bleeding", "Abortion", "Childbirth", "Poisoning By Swallow", "Poisoning By Inhalation", "Poisoning By Stinging", "Quarrel", "Sunstroke", "Suicide Attempt", "Rape", "Knife", "Gas Leakage", "Work Injury", "cardiac arrest", "Stroke")))
                .body("data.id",allOf(hasItems(1,2,5,6,7,8,9,10,11,12,13,14,15,16,17,19,20,21,22,23,26,27,28,29,30,31,32,34,35,38,41,42,45,51,55,60,61)))
                .body("data.categoryNameAr",allOf(hasItems("حوادث","أمراض","نساء و ولادة","تسمم")))
                .body("data.categoryNameEn",allOf(hasItems("Accidents","Diseases","Women and childbirth","Poisoning")));


    }
}
