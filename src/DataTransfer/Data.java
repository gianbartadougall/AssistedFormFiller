package DataTransfer;

import Objects.House;
import Objects.Owner;
import Objects.Tenant;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Data {

    Map<String, Owner> owners = new HashMap<>();
    Map<String, Tenant> tenants = new HashMap<>();
    Map<String, House> houses = new HashMap<>();

    public Data(String path) {
        StringBuilder data = new StringBuilder();
        String line;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] dataSplit = data.toString().split(";");
        if (dataSplit.length == 1) {
            addData(dataSplit[0]);
        }
        if (dataSplit.length == 2) {
            addData(dataSplit[0]);
            addData(dataSplit[1]);
        }
        if (dataSplit.length == 3) {
            addData(dataSplit[0]);
            addData(dataSplit[1]);
            addData(dataSplit[2]);
        }
    }

    private HashMap<String,Owner> createOwners(HashMap<String,HashMap<String,String>> map) {
        HashMap<String,Owner> owners = new HashMap<>();
        for (HashMap.Entry<String,HashMap<String,String>> e : map.entrySet()) {
            owners.put(e.getKey(), new Owner(e.getValue().get("ownerName"), e.getValue().get("ownerLastName"), e.getValue().get("ownerAddress"),
                    e.getValue().get("ownerPhone"), e.getValue().get("ownerMobile"), e.getValue().get("ownerEmail"), e.getValue().get("ownerRtaID"),
                    e.getValue().get("ownerReceiveEmails"), e.getValue().get("ownerABN"), e.getValue().get("ownerPostcode"),
                    e.getValue().get("ownerPostalAddress"), e.getValue().get("ownerAccountName"), e.getValue().get("ownerBSB"), e.getValue().get("ownerAccountNumber")));
        }
        return owners;
    }

    private HashMap<String,Tenant> createTenants(HashMap<String,HashMap<String,String>> map) {
        HashMap<String, Tenant> tenants = new HashMap<>();
        for (HashMap.Entry<String,HashMap<String,String>> e : map.entrySet()) {
            tenants.put(e.getKey(), new Tenant(e.getValue().get("tenantName"), e.getValue().get("tenantLastName"), e.getValue().get("tenantAddress"),
                    e.getValue().get("tenantPhone"), e.getValue().get("tenantMobile"), e.getValue().get("tenantEmail"), e.getValue().get("tenantRtaID"),
                    e.getValue().get("tenantReceiveEmails"), e.getValue().get("tenantDateOfBirth"), e.getValue().get("bondPayment"), e.getValue().get("rentalBondNumber"),
                    e.getValue().get("tenantReceivesSubsidy"), e.getValue().get("tenantPaymentMethod"), e.getValue().get("newBond"),
                    e.getValue().get("startDate"), e.getValue().get("endDate"), e.getValue().get("tenantAccountName"), e.getValue().get("tenantBSB"), e.getValue().get("tenantAccountNumber")));
        }
        return tenants;
    }

    private HashMap<String,House> createHouses(HashMap<String,HashMap<String,String>> map) {
        HashMap<String,House> houses = new HashMap<>();
        for (HashMap.Entry<String,HashMap<String,String>> e : map.entrySet()) {
            houses.put(e.getKey(), new House(e.getValue().get("houseAddress"), e.getValue().get("housePostcode"),
                    e.getValue().get("bedrooms"), e.getValue().get("dwellingType"), e.getValue().get("rent")));
        }
        return houses;
    }

    private HashMap<String,HashMap<String,String>> disectData(String dataSplit) {
        String objectKey;
        HashMap<String,HashMap<String,String>> values = new HashMap<>();
        for (String value : dataSplit.split("<")) {
            String[] information = value.split(">");
            objectKey = information[0];
            HashMap<String,String> tempDict = new HashMap<>();
            if (information.length < 2) {
                continue;
            }
            for (String data : information[1].split("/=/")) {
                String[] splitData = data.split(":");
                if (splitData.length > 1) {
                    tempDict.put(splitData[0], splitData[1]);
                } else {
                    tempDict.put(splitData[0], "");
                }
            }
            values.put(objectKey, tempDict);
        }
        return values;
    }

    private void addData(String dataSplit) {
        String type = determineType(dataSplit);
        switch (type) {
            case "owner": owners.putAll(createOwners(disectData(dataSplit)));
                break;
            case "tenant": tenants.putAll(createTenants(disectData(dataSplit)));
                break;
            default: houses.putAll(createHouses(disectData(dataSplit)));
        }
    }

    private String determineType(String val) {
        if (val.contains("bedrooms")) {
            return "house";
        } else if (val.contains("tenantEmail")) {
            return "tenant";
        } else return "owner";
    }

    public Map<String, Owner> getOwners() {
        return owners;
    }

    public Map<String, Tenant> getTenants() {
        return tenants;
    }

    public Map<String, House> getHouses() {
        return houses;
    }
}
