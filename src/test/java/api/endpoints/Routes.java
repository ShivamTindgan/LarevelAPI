package api.endpoints;

public class Routes {

    /* laravel URLs:
    1)Teacher/Student otp:'https://api.sparkl.ac/api/v1/users/otp'
    2)Teacher/Student login : curl 'https://api.sparkl.ac/api/v1/users/login'
    3)sessions
    On teacher side
    4)https://api.sparkl.ac/api/v1/sessions?time_frame=future&limit=1&page=1&sort_order=asc
    5)https://api.sparkl.ac/api/v1/sessions?time_frame=range&sort_order=desc&fromDate=2026-03-30&toDate=2026-03-30&page=1&limit=20
    On student side
    6) https://api.sparkl.ac/api/v1/sessions?subject_id=4&page=1&limit=1000&timeFrame=all
    7)https://api.sparkl.ac/api/v1/sessions?time_frame=past&limit=1&sort_order=desc&subject_id=4
    8)https://api.sparkl.ac/api/v1/sessions?limit=5&time_frame=future


     */
    public static String baseUrlStaging="https://api.sparkl.ac/api/v1/";


    //Common Urls
    public static String otpUrl=baseUrlStaging+"users/otp";
    public static String loginUrl =baseUrlStaging+"users/login";
    public static String sessionsURL=baseUrlStaging+"sessions";
    public static String profileUrl=baseUrlStaging+"users/me";
    public static String tokenUrl=baseUrlStaging+"sessions/{session_id}/token";

    //Teacher specific Urls
    public static String teacherStudentsUrl=baseUrlStaging+"teachers/{teacher_id}/students";
    public static String sessionSummaryListUrl=baseUrlStaging+"sessions/summary";
    public static String approveSessionSummaryUrl=baseUrlStaging+"sessions/{session_id}/summary-approve/{session_summary_id}";


    //Student specific Urls
    public static String studentSubscriptionsUrl =baseUrlStaging+"students/subscriptions";
    public static String studentCoursesSubjectsUrl=baseUrlStaging+"students/course-subjects";
    public static String studentAssignmentNotificationUrl=baseUrlStaging+"assignments/notifications";
    public static String studentCambridgeAssessmentsUrl=baseUrlStaging+"students/assessments";
    public static String studentAssignmentSubmitUrl=baseUrlStaging+"assignments/{assignment_id}/submit";

}
