package Forms;

import java.util.ArrayList;
import java.util.HashMap;

public class SAFixedTermForm extends Form {


    public SAFixedTermForm() {
    }

    @Override
    protected int createCorrectionAmountForSecondTenant() {
        return 0;
    }

    @Override
    protected int createABNFN() {
        return 0;
    }

    @Override
    protected HashMap<String, String> createData() {
        return null;
    }

    @Override
    protected int createName() {
        return 0;
    }

    @Override
    protected String[] createSpecialValues() {
        return new String[0];
    }

    @Override
    protected ArrayList<String> createCriticalInformation() {
        return null;
    }

    @Override
    public String convertSpecialValue(String key, String value) {
        return null;
    }

    @Override
    public String formatABN(String value, int spaces) {
        return null;
    }

    @Override
    public String formatAccountNumber(String value, int spaces) {
        return null;
    }

    @Override
    public String[] XYPositionsForCurrentDate(Boolean secondTenant) {
        return new String[0];
    }
}
