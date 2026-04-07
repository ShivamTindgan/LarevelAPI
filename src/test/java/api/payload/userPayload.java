package api.payload;

public class userPayload {
    String teacher_id ;
    String teacher_token ;
    String student_id ;
    String student_token ;


    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_token() {
        return teacher_token;
    }

    public void setTeacher_token(String teacher_token) {
        this.teacher_token = teacher_token;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_token() {
        return student_token;
    }

    public void setStudent_token(String student_token) {
        this.student_token = student_token;
    }

}
