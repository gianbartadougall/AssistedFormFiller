package Objects;

import java.util.HashMap;

public class Owner {

    private HashMap<String, String> attributes;

    public Owner(String name, String lastName, String address, String phone, String mobile, String email, String rtaID,
                  String receiveEmails, String abn, String postcode, String postalAddress, String accountName,
                 String BSB, String accountNumber) {
        this.attributes = new HashMap<>();
        attributes.put("ownerName", name);
        attributes.put("ownerLastName", lastName);
        attributes.put("ownerFullName", name + " " + lastName);
        attributes.put("ownerAddress", address);
        attributes.put("ownerPhone", phone);
        attributes.put("ownerMobile", mobile);
        attributes.put("ownerEmail", email);
        attributes.put("ownerRtaID", rtaID);
        attributes.put("ownerReceiveEmails", receiveEmails);
        attributes.put("ownerABN", abn);
        attributes.put("ownerPostcode", postcode);
        attributes.put("ownerPostalAddress", postalAddress);
        attributes.put("ownerAccountName", accountName);
        attributes.put("ownerBSB", BSB);
        attributes.put("ownerAccountNumber", accountNumber);
    }

    public HashMap<String, String> attributes() {
        return attributes;
    }


}
