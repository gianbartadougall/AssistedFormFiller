package Forms;

import Enums.Enum;
import Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class RefundRentalBondForm extends Form {

    Utils utils;

    public RefundRentalBondForm() {
        utils = new Utils();
    }

    @Override
    protected int createABNFN() {
        return 6;
    }

    @Override
    protected HashMap<String, String> createData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("houseAddress","70,168");
        map.put("housePostcode","520,193");
        map.put("rentalBondNumber", "600,188");
        //distance between t1 and t2 is 184
        map.put("tenantName","140,350");
        map.put("tenantLastName","380,350");
        map.put("tenantRtaID","680,350");
        map.put("tenantDateOfBirth","128,375");
        map.put("tenantPhone","280,375");
        map.put("tenantMobile","550,375");
        map.put("tenantEmail","100,425");
        map.put("tenantCurrentDate","610,1067");
        map.put("tenantForwardingAddress", "860,1007");
        map.put("tenantPostcode", "100,100");
        map.put("tenantAccountName", "200,477");
        map.put("tenantBSB", "117,502");
        map.put("tenantAccountNumber", "355,502");

        map.put("ownerFullName", "200,760");
        map.put("ownerRtaID", "645,785");
        map.put("ownerPhone", "100,785");
        map.put("ownerMobile", "360,785");
        map.put("ownerPostalAddress", "150,810");
        map.put("ownerEmail", "100,837");
        map.put("ownerPostcode", "695,810");
        map.put("ownerAccountName", "200,888");
        map.put("ownerBSB", "120,912");
        map.put("ownerAccountNumber", "354,912");

        map.put("details", "100,100");
        map.put("bondHeldByRTA", "100,100");
        map.put("expiryDate", "100,100");
        map.put("dateVacated", "100,100");
        map.put("newWeeklyRent", "100,100");
        return map;
    }

    @Override
    protected int createName() {
        return Enum.REFUND_RENTAL_BOND;
    }

    @Override
    protected String[] createSpecialValues() {
        return null;
    }

    @Override
    public String convertSpecialValue(String key, String value) {
        System.out.println("KEY " + key);
        switch (key) {
            case "tenantReceiveEmails": return Integer.parseInt(value) == 0 ? "0,0" : "537,1099";
            case "ownerReceiveEmails": return Integer.parseInt(value) == 0 ? "0,0" : "537,770";
            case "tenantReceivesSubsidy": return Integer.parseInt(value) == 0 ? "0,0" : "556,942";
            case "tenantPaymentMethod": return Integer.parseInt(value) == 0 ? "71,830" : "259,830";
            case "newBond": return Integer.parseInt(value) == 0 ? "666,213" : "666,190";
            default: return "0,0";
        }
    }

    @Override
    public String formatABN(String value, int spaces) {
        StringBuilder newValue = new StringBuilder();
        int index = 0;
        for (String c : value.split("")) {
            newValue.append(index %2==0 ? c + utils.space(spaces) : c + utils.space(spaces-1));
            index++;
        }
        return newValue.toString();
    }

    @Override
    public String[] XYPositionsForCurrentDate(Boolean secondTenant) {
        return secondTenant ? new String[] {"495,452", "495,638", "495,863"} : new String[] {"495,452", "495,638"};
    }

    @Override
    protected ArrayList<String> createCriticalInformation() {
        return null;
    }

    @Override
    public String formatAccountNumber(String value, int spaces) {
        return null;
    }

    @Override
    protected int createCorrectionAmountForSecondTenant() {
        return 184;
    }
}
