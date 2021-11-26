package bacit.web.Modules;

@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
public class CertificateModel {

    private int certificateID;
    private String certificateName;

    public CertificateModel(int certificateID, String certificateName) {
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
