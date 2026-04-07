//package api.endpoints;
//
//
//import api.payload.login;
//import api.payload.otp;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//
//import java.util.Map;
//
//import static io.restassured.RestAssured.given;
//
//public class commonEndpoints {
//
//    String student_token;
//    String student_id;
//    String teacher_token;
//    String teacher_id;
//    String user_type;
//
//    public static Response Otp(otp payload)
//    {
//        Response response =given()
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .body(payload)
//
//                .when()
//                .post(Routes.otpUrl);
//        return response;
//
//    }
//
//
//    public static Response login(login payload)
//    {
//        Response response =given()
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .header()
//                .body(payload)
//
//                .when()
//                .post(Routes.loginUrl);
//        return response;
//    }
//
//
//
//}
