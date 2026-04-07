package api.payload;
//{"isd":"+91","mobile":"8295802444","otp":"2444"}
public class login {
    String isd;
    String mobile;
    String otp;


    public String getIsd() {
        return isd;
    }

    public void setIsd(String isd) {
        this.isd = isd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}