package api.endpoints;

import api.payload.login;
import api.payload.otp;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class studentEndpoints {

    public static Response getStudentOtp(login payload)
    {
        Response response =given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("user-type","student")
                .body(payload)

                .when()
                .post(Routes.otpUrl);
        return response;

    }


    public static Response login(login payload)
    {
        Response response =given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("user-type","student")
                .body(payload)

                .when()
                .post(Routes.loginUrl);
        return response;
    }

    public static Response getStudentSubscription(String student_token, String student_id)
    {
        Response response =given()
                .header("token",student_token)
                .header("user-id",student_id)
                .header("user-type","student")
                .when()
                .get(Routes.studentSubscriptionsUrl);
        return response;
    }

    public static Response getCoursesSubjects(String student_token, String student_id)
    {
        Response response =given()
                .header("token",student_token)
                .header("user-id",student_id)
                .header("user-type","student")
                .when()
                .get(Routes.studentCoursesSubjectsUrl);
        return response;
    }

    public static Response getCambridgeAssessment(String student_token, String student_id)
    {
        Response response =given()
                .header("token",student_token)
                .header("user-id",student_id)
                .header("user-type","student")
                .when()
                .get(Routes.studentCambridgeAssessmentsUrl);
        return response;
    }

    public static Response getAssignmentNotification(String student_token, String student_id)
    {
        Response response =given()
                .header("token",student_token)
                .header("user-id",student_id)
                .header("user-type","student")
                .when()
                .get(Routes.studentAssignmentNotificationUrl);
        return response;
    }

    public static Response submitAssignment(String student_token, String student_id, int assignment_id)
    {
        File file1 = new File(".//Image_one.png");
        File file2 = new File(".//image_two.png");

        String fileMeta = "[{\"page\":\"1\",\"file_name\":\"Image_one.png\"},{\"page\":\"2\",\"file_name\":\"image_two.png\"}]";

//        int id =(Integer) context.getSuite().getAttribute("assignment_id");


        Response response =given()
                .multiPart("files[]", file1, "image/png")
                .multiPart("files[]", file2, "image/png")
                .multiPart("file_meta", fileMeta, "application/json")


                .header("user-type", "student")
                .header("user-id", student_id)
                .header("token", student_token)
//
                .pathParam("assignment_id", assignment_id)

                .when()
                .post(Routes.studentAssignmentSubmitUrl);

        return response;
    }

    public static Response getFutureSessions(String student_token, String student_id)
    {
        Response response =given()
                .header("token",student_token)
                .header("user-id",student_id)
                .header("user-type","student")
                .queryParam("limit","5")
                .queryParam("time_frame","future")


                .when()
                .get(Routes.sessionsURL);
        return response;
    }

    public static Response getPastSessions(String student_token, String student_id)
    {
        Response response =given()
                .header("token",student_token)
                .header("user-id",student_id)
                .header("user-type","student")
                .queryParam("limit","1")
                .queryParam("time_frame","past")
                .queryParam("sort_order","desc")
                .queryParam("subject_id","4")

                .when()
                .get(Routes.sessionsURL);
        return response;
    }


    public static Response getAllSubjectSessions(String student_token, String student_id)
    {
        Response response =given()
                .header("token",student_token)
                .header("user-id",student_id)
                .header("user-type","student")
                .queryParam("limit","1000")
                .queryParam("time_frame","all")
                .queryParam("page","1")
                .queryParam("subject_id","4")

                .when()
                .get(Routes.sessionsURL);
        return response;
    }

    public static Response userProfile(String student_token, String student_id)
    {
        Response response = given()
                .header("token",student_token)
                .header("user-id",student_id)
                .header("user-type","student")
                .when()
                .get(Routes.profileUrl);
        return response;
    }

    public static Response getStudentInfinityToken(String student_token, String student_id, int sessionId)
    {
        Response response =given()
                .header("token",student_token)
                .header("user-id",student_id)
                .header("user-type","student")
                .pathParams("session_id",sessionId)
                .when()
                .get(Routes.tokenUrl);
        return response;
    }






}
