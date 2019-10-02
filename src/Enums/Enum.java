package Enums;

public class Enum {

    public static final String FALSE = "0,0", TRUE = "1", AMBIGUOUS_POSITION = "3";
    public static final int BOND_LODGMENT = 0;
    public static final int REFUND_RENTAL_BOND = 1;
    public static final String[] DWELLING_TYPES = new String[] {"House", "Town House", "Apartment"};
    public static final String dataPath = "res/Data/data.txt";
    public static final String[] INVALID_CHARACTERS = {",", "/=/", ":", ">", "=", "<", ";"};

    // THE ORDER THAT THESE ARE IN MATTER, DO NOT CHANGE THEM OTHERWISE YOU WILL STUFF UP A LOT OF STUFF
    public static final String[] TENANT_LABEL_ATTRIBUTES = {"Name", "Last Name", "Address", "Phone", "Mobile", "Email", "RTA ID", "Date of Birth (dd/mm/yyyy)",
            "Bond Payment", "startDate (dd/mm/yyyy)", "endDate (dd/mm/yyyy)", "Account Name", "BSB", "Account Number", "Payment Method", "Receive RTA Emails", "Receives Subsidy", "New Bond" ,"Bond Number"};
    public static final String[] TENANT_OFFICIAL_ATTRIBUTES = {"tenantName", "tenantLastName", "tenantAddress", "tenantPhone", "tenantMobile", "tenantEmail", "tenantRtaID", "tenantDateOfBirth",
            "bondPayment", "startDate", "endDate", "tenantAccountName", "tenantBSB", "tenantAccountNumber", "tenantPaymentMethod", "tenantReceiveEmails", "tenantReceivesSubsidy", "newBond" ,"rentalBondNumber"};
    public static final String[] OWNER_lABEL_ATTRIBUTES = {"Name", "Last Name", "Address", "Phone", "Mobile", "Email", "RTA ID", "ABN", "Postcode", "Postal Address", "Account Name", "Account BSB", "Account Number", "Receive RTA Emails"};
    public static final String[] OWNER_OFFICIAL_ATTRIBUTES = {"ownerName", "ownerLastName", "ownerAddress", "ownerPhone", "ownerMobile", "ownerEmail", "ownerRtaID", "ownerABN",
            "ownerPostcode", "ownerPostalAddress", "ownerAccountName", "ownerBSB", "ownerAccountNumber", "ownerReceiveEmails"};
    public static final String[] HOUSE_LABEL_ATTRIBUTES = {"Address", "Postcode", "Bedrooms", "Rent", "Dwelling Type"};
    public static final String[] HOUSE_OFFICIAL_ATTRIBUTES = {"houseAddress", "housePostcode", "bedrooms", "rent", "dwellingType"};
    public static final String[] VALID_PAYMENT_METHODS = {"Cheque/Money Order", "BPAY"};
}
