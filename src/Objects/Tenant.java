package Objects;

import java.util.HashMap;

public class Tenant {

    private HashMap<String, String> attributes;

    public Tenant(String name, String lastName, String address, String phone, String mobile, String email, String rtaID,
                  String receiveRTANotices, String dateOfBirth, String bondPayment, String rentalBondNumber, String receivesSubsidy,
                  String paymentMethod, String newBond, String startDate, String endDate, String accountName,
                  String BSB, String accountNumber) {
        this.attributes = new HashMap<>();
        attributes.put("tenantName", name);
        attributes.put("tenantLastName", lastName);
        attributes.put("tenantAddress", address);
        attributes.put("tenantPhone", phone);
        attributes.put("tenantMobile", mobile);
        attributes.put("tenantEmail", email);
        attributes.put("tenantRtaID", rtaID);
        attributes.put("tenantReceiveEmails", receiveRTANotices);
        attributes.put("tenantDateOfBirth", dateOfBirth);
        attributes.put("bondPayment", bondPayment);
        attributes.put("rentalBondNumber", rentalBondNumber);
        attributes.put("tenantReceivesSubsidy", receivesSubsidy);
        attributes.put("tenantPaymentMethod", paymentMethod);
        attributes.put("newBond", newBond);
        attributes.put("startDate", startDate);
        attributes.put("endDate", endDate);
        attributes.put("tenantAccountName", accountName);
        attributes.put("tenantBSB", BSB);
        attributes.put("tenantAccountNumber", accountNumber);
    }

    public HashMap<String, String> attributes() {
        return attributes;
    }
}
