package Objects;

import java.util.HashMap;

public class House {

    private HashMap<String, String> attributes;

    public House(String address, String postcode, String bedrooms, String dwellingType, String rent) {
        attributes = new HashMap<>();
        attributes.put("houseAddress", address);
        attributes.put("housePostcode", postcode);
        attributes.put("bedrooms", bedrooms);
        attributes.put("dwellingType", dwellingType);
        attributes.put("rent", rent);
        if (rent.isBlank()) {
            rent = "900";
        }
        attributes.put("totalBond", Integer.toString(Integer.parseInt(rent)*4));
    }

    public HashMap<String, String> attributes() {
        return attributes;
    }
}
