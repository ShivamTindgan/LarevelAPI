package api.tests;

import api.endpoints.Routes;
import api.endpoints.studentEndpoints;
import api.endpoints.teacherEndpoints;
import api.payload.login;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class studentTests {
    login loginPayload;
    String student_token;
    String student_id;
    int assignment_id;
    int sessionId ;



    @BeforeClass
    public void setup() {

        loginPayload = new login();

        loginPayload.setMobile("8295802444");
        loginPayload.setIsd("+91");
        loginPayload.setOtp("2444");

        Response res = studentEndpoints.login(loginPayload);

        student_token = res.jsonPath().getString("data.token");
        student_id = res.jsonPath().getString("data.user.id");

        Response response =studentEndpoints.getPastSessions(student_token, student_id);

        sessionId=response.jsonPath().get("data[0].id");
    }

    @Test(priority=1)
    public void testOtp()
    {
        Response response = studentEndpoints.getStudentOtp(loginPayload);

        response.then().log().all(); // debug

        // 3. Validate Status Code
        Assert.assertEquals(response.getStatusCode(), 200);

        // 4. Validate response body fields
        int status = response.jsonPath().getInt("status");
        String message = response.jsonPath().getString("message");

        Assert.assertEquals(status, 1);
        Assert.assertEquals(message, "OTP has been sent to your mobile number");

    }

    @Test(priority=2)
    public void testLogin()
    {

        Response response = studentEndpoints.login(loginPayload);

        response.then().log().all(); // debug

        // 3. Validate Status Code
        Assert.assertEquals(response.getStatusCode(), 200);

        // 4. Validate response body fields
        int status = response.jsonPath().getInt("status");
        String message = response.jsonPath().getString("message");

        Assert.assertEquals(status, 1);
        Assert.assertEquals(message, "success");

    }

    @Test(priority=3, dependsOnMethods = "testLogin")
    public void testSubscriptions()
    {
        Response response =studentEndpoints.getStudentSubscription(student_token, student_id);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"Student Subscriptions");

    }

    @Test(priority=4, dependsOnMethods = "testLogin")
    public void testGetCourseSubjects()
    {
        Response response =studentEndpoints.getCoursesSubjects(student_token, student_id);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"course subjects");
    }

    @Test(priority=5, dependsOnMethods = "testLogin")
    public void testGetCambridgeAssessment()
    {
        Response response =studentEndpoints.getCambridgeAssessment(student_token, student_id);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"assessments");
    }

    @Test(priority=6, dependsOnMethods = "testLogin")
    public void testAssignmentNotification()
    {
        Response response =studentEndpoints.getAssignmentNotification(student_token, student_id);
        response.then().log().all();


        assignment_id = response.jsonPath().getInt("data[0].assignment_id");

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"Notifications");
    }

    @Test(priority=7, dependsOnMethods = "testAssignmentNotification")
    public void testSubmitAssignment()
    {
        Response response =studentEndpoints.submitAssignment(student_token, student_id, assignment_id);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"Assignment uploaded successfully.");
    }

    @Test(priority=8, dependsOnMethods = "testLogin")
    public void testFutureSessions()
    {
        Response response =studentEndpoints.getFutureSessions(student_token, student_id);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"Sessions");
    }

    @Test(priority=9, dependsOnMethods = "testLogin")
    public void testPastSessions()
    {
        Response response =studentEndpoints.getPastSessions(student_token, student_id);

        sessionId=response.jsonPath().get("data[0].id");
        System.out.println("Session id is :"+sessionId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"Sessions");
    }

    @Test(priority=10, dependsOnMethods = "testLogin")
    public void testAllSessionsBySubject()
    {
        Response response =studentEndpoints.getAllSubjectSessions(student_token, student_id);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"Sessions");
    }

    @Test(priority=11, dependsOnMethods = "testLogin")
    public void testUserprofile()
    {
        Response response = studentEndpoints.userProfile(student_token,student_id);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"success");
    }

    @Test(priority=8, dependsOnMethods = "testLogin")
    public void testInfinityToken()
    {
        System.out.println("Session id is :"+sessionId);
        Response response =studentEndpoints.getStudentInfinityToken(student_token,student_id, sessionId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"Token");

        //check data object exists or not
        Assert.assertNotNull(response.jsonPath().get("data"));
        //token validations
        String token = response.jsonPath().getString("data.token");
        Assert.assertNotNull(token);
        Assert.assertFalse(token.trim().isEmpty());
        Assert.assertTrue(token.startsWith("NETLESSROOM_"));

        //check agora tokens
        String rtm = response.jsonPath().getString("data.rtm_token");
        String rtc = response.jsonPath().getString("data.rtc_token");

        Assert.assertNotNull(rtm);
        Assert.assertNotNull(rtc);
        Assert.assertFalse(rtm.isEmpty());
        Assert.assertFalse(rtc.isEmpty());

    }
}
