package bacit.web.Modules;

public class Certificate {

    private int certificateID;
    private String certificateName;

    public Certificate(int certificateID, String certificateName) {
        this.certificateID = certificateID;
        this.certificateName = certificateName;
    }

    public int getCertificateID() {
        return certificateID;
    }

    public String getCertificateName() {
        return certificateName;
    }
}
