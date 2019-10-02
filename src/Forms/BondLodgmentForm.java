package Forms;

import Enums.Enum;
import Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BondLodgmentForm extends Form {

    public static final String FLAT_OR_UNIT = "207,385", HOUSE = "309,385", TOWN_HOUSE = "402,385",
            MOVEABLE_DWELLING_SITE = "207,411", MOVEABLE_DWELLING_WITH_ELECTRICITY = "384,411", BOARDING_HOUSE = "207,445",
            SUPPORTED_ACCOMODATION = "359,445", STUDENT_ROOMING_ACCOMODATION = "588,445", RESIDENT_TENANCY_OWNER = "207,515",
            RESIDENT_TENANCY_MANAGER = "300,515", MOVEABLE_DWELLING_MANAGER = "467,515", SOCIAL_HOUSING_ORGANISATION = "207,540",
            RESIDENT_TENANCY_OTHER = "435,540", ROOMING_ACCOMODATION_OWNER = "207,573", ROOMING_ACCOMODATION_MANAGER = "300,573",
            REAL_ESTATE_AGENT = "465,573", ROOMING_ACCOMODATION_OTHER = "629,573";
    public static final String CHEQUE_OR_MONEY = "71,831", BPAY = "259,831", TENANT_RECEIVES_SUBSIDY = "555,943", OWNER_RTA_EMAILS = "537,770",
            TENANT_RTA_EMAILS = "537,1100", TENANT2_RTA_EMAILS = "537,1239";
    public static final int FABN = 6;

    Utils utils = new Utils();

    public BondLodgmentForm() {
    }

    @Override
    public Map<String, String> xyDataSet() {
        return super.xyDataSet();
    }

    @Override
    protected HashMap<String, String> createData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("houseAddress","80,212");
        map.put("housePostcode","552,244");
        map.put("bedrooms","242,327");
        map.put("dwellingType","3");
        map.put("totalBond", "95,916");
        map.put("rent", "235,916");

        map.put("tenantName","160,1007");
        map.put("tenantLastName","560,1007");
        map.put("bondPayment", "860,1007");
        map.put("tenantPhone","350,1037");
        map.put("tenantDateOfBirth","180,1037");
        map.put("tenantMobile","610,1037");
        map.put("tenantEmail","130,1099");
        map.put("tenantRtaID","195,1067");
        map.put("tenantCurrentDate","610,1067");
        map.put("tenantReceiveEmails", "3");
        map.put("bondPaidWithForm", "320,916");
        map.put("tenantReceivesSubsidy", "3");
        map.put("tenantPaymentMethod", "3");
        map.put("newBond", "3");
        map.put("rentalBondNumber", "700,244");
        map.put("startDate", "225,290");
        map.put("endDate", "535,290");

        map.put("ownerFullName", "232,647");
        map.put("ownerABN", "119,678");
        map.put("ownerRtaID", "605,678");
        map.put("ownerPostalAddress", "182,708");
        map.put("ownerPostcode", "875,708");
        map.put("ownerPhone", "130,740");
        map.put("ownerReceiveEmails", "3");
        map.put("ownerEmail", "130,770");
        return map;
    }

    @Override
    protected int createName() {
        return Enum.BOND_LODGMENT;
    }

    @Override
    protected String[] createSpecialValues() {
        return new String[] {"dwellingType", "receiveRTAEmails"};
    }

    @Override
    public String[] XYPositionsForCurrentDate(Boolean secondTenant) {
        return secondTenant ? new String[] {"615,740", "612,1067", "612,1207"} : new String[] {"615,740", "612,1067"};
    }

    @Override
    public String convertSpecialValue(String key, String value) {
        System.out.println("KEY " + key);
        switch (key) {
            case "dwellingType": return getDwellingPosition(Integer.parseInt(value));
            case "tenantReceiveEmails": return Integer.parseInt(value) == 0 ? "0,0" : "537,1099";
            case "ownerReceiveEmails": return Integer.parseInt(value) == 0 ? "0,0" : "537,770";
            case "tenantReceivesSubsidy": return Integer.parseInt(value) == 0 ? "0,0" : "556,942";
            case "tenantPaymentMethod": return Integer.parseInt(value) == 0 ? "71,830" : "259,830";
            case "newBond": return Integer.parseInt(value) == 0 ? "666,213" : "666,190";
            default: return "0,0";
        }
    }

    private String getDwellingPosition(int value) {
        switch (value) {
            case 0: return FLAT_OR_UNIT;
            case 1: return HOUSE;
            case 2: return TOWN_HOUSE;
            case 3: return MOVEABLE_DWELLING_SITE;
            case 4: return MOVEABLE_DWELLING_WITH_ELECTRICITY;
            case 5: return BOARDING_HOUSE;
            case 6: return SUPPORTED_ACCOMODATION;
            case 7: return STUDENT_ROOMING_ACCOMODATION;
            case 8: return RESIDENT_TENANCY_OWNER;
            case 9: return RESIDENT_TENANCY_MANAGER;
            case 10: return MOVEABLE_DWELLING_MANAGER;
            case 11: return SOCIAL_HOUSING_ORGANISATION;
            case 12: return RESIDENT_TENANCY_OTHER;
            case 13: return ROOMING_ACCOMODATION_OWNER;
            case 14: return ROOMING_ACCOMODATION_MANAGER;
            case 15: return REAL_ESTATE_AGENT;
            case 16: return ROOMING_ACCOMODATION_OTHER;
            default: return "10,10";
        }
    }

    @Override
    protected int createABNFN() {
        return 6;
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
    protected ArrayList<String> createCriticalInformation() {
        ArrayList<String> list = new ArrayList<>();
        list.add("startLease");
        list.add("endLease");
        return list;
    }

    @Override
    public String formatAccountNumber(String value, int spaces) {
        return null;
    }

    @Override
    protected int createCorrectionAmountForSecondTenant() {
        return 140;
    }
}
