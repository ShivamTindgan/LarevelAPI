package api.payload;
//{"type":"login","mobile":"8295802444","isd":"+91"}
public class otp {

    String mobile;
    String isd;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsd() {
        return isd;
    }

    public void setIsd(String isd) {
        this.isd = isd;
    }
}
