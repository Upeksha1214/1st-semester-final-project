package view.TM;

public class RefTm {
    private String refID;
    private String refNIC;
    private String refName;
    private String refAddress;
    private String refPhoneNumber;

    public RefTm() {
    }

    public RefTm(String refID, String refNIC, String refName, String refAddress, String refPhoneNumber) {
        this.refID = refID;
        this.refNIC = refNIC;
        this.refName = refName;
        this.refAddress = refAddress;
        this.refPhoneNumber = refPhoneNumber;
    }

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public String getRefNIC() {
        return refNIC;
    }

    public void setRefNIC(String refNIC) {
        this.refNIC = refNIC;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefAddress() {
        return refAddress;
    }

    public void setRefAddress(String refAddress) {
        this.refAddress = refAddress;
    }

    public String getRefPhoneNumber() {
        return refPhoneNumber;
    }

    public void setRefPhoneNumber(String refPhoneNumber) {
        this.refPhoneNumber = refPhoneNumber;
    }

    @Override
    public String toString() {
        return "RefTm{" +
                "refID='" + refID + '\'' +
                ", refNIC='" + refNIC + '\'' +
                ", refName='" + refName + '\'' +
                ", refAddress='" + refAddress + '\'' +
                ", refPhoneNumber='" + refPhoneNumber + '\'' +
                '}';
    }
}
