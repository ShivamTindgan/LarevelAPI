package api.tests;

import api.endpoints.teacherEndpoints;
import api.payload.login;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class teacherTests {

    login loginPayload;
    String teacher_token;
    String teacher_id;
    public static int sessionId;
    public static int summaryId;


    @BeforeClass
    public void setup() {

        loginPayload = new login();

        loginPayload.setMobile("8295802444");
        loginPayload.setIsd("+91");
        loginPayload.setOtp("2444");

        Response res = teacherEndpoints.login(loginPayload);

        teacher_token = res.jsonPath().getString("data.token");
        teacher_id = res.jsonPath().getString("data.user.id");

        //get summary
        Response response2 =teacherEndpoints.getSessionSummary(teacher_token, teacher_id);
        sessionId= response2.jsonPath().getInt("data[0].id");
        summaryId =response2.jsonPath().getInt("data[0].summary_id");
    }

    @Test(priority=1)
    public void testOtp()
    {
        Response response = teacherEndpoints.Otp(loginPayload);

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

        Response response = teacherEndpoints.login(loginPayload);

        response.then().log().all(); // debug

        // 3. Validate Status Code
        Assert.assertEquals(response.getStatusCode(), 200);

        // 4. Validate response body fields
        int status = response.jsonPath().getInt("status");
        String message = response.jsonPath().getString("message");

        Assert.assertEquals(status, 1);
        Assert.assertEquals(message, "success");

    }

    @Test(priority = 3, dependsOnMethods = "testLogin")
    public void testGetStudents()
    {
        // 1. Call API
        Response response = teacherEndpoints.getTeacherStudents(teacher_token, teacher_id);

        response.then().log().all();

        // 3. Validate Status Code
        Assert.assertEquals(response.getStatusCode(), 200);

        // 4. Validate response body
        int status = response.jsonPath().getInt("status");
        String message = response.jsonPath().getString("message");

        Assert.assertEquals(status, 1);
        Assert.assertEquals(message, "students");

        // 5. Validate data is not empty
        int size = response.jsonPath().getList("data").size();
        Assert.assertTrue(size > 0, "Student list is empty");

        // 6. Validate first student fields (basic validation)
        String studentName = response.jsonPath().getString("data[0].name");
        int studentId = response.jsonPath().getInt("data[0].id");

        Assert.assertNotNull(studentName);
        Assert.assertTrue(studentId > 0);
    }

    @Test(priority = 4, dependsOnMethods = "testLogin")
    public void testGetSummary()
    {
        Response response =teacherEndpoints.getSessionSummary(teacher_token, teacher_id);

        response.then().log().all();


        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("message"),"Session Summary");

    }

    @Test(priority=5, dependsOnMethods = "testGetSummary")
    public void testApproveSummary()
    {
        Response response= teacherEndpoints.approveSessionSummary(teacher_token, teacher_id, sessionId, summaryId);

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("message"),"Session Summary Approved");
        Assert.assertEquals(response.jsonPath().getString("data"),"Session Summary updated carefully");

    }

    @Test(priority=6, dependsOnMethods = "testLogin")
    public void testFutureSession()
    {
        Response response= teacherEndpoints.getFutureSessions(teacher_token,teacher_id);

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"Sessions");

    }

    @Test(priority=7, dependsOnMethods = "testLogin")
    public void testClassSchedule()
    {
        Response response= teacherEndpoints.getClassSchedule(teacher_token,teacher_id);

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"Sessions");

    }

    @Test(priority=8, dependsOnMethods = "testLogin")
    public void testUserprofile()
    {
        Response response =teacherEndpoints.userProfile(teacher_token,teacher_id);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getInt("status"),1);
        Assert.assertEquals(response.jsonPath().getString("message"),"success");
    }

    @Test(priority=8, dependsOnMethods = "testLogin")
    public void testInfinityToken()
    {
        Response response =teacherEndpoints.getInfinityToken(teacher_token,teacher_id, sessionId);
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
