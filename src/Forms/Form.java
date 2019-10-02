package Forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Form {

    private Map<String, String> xyDataSet;
    private int name;
    private String[] specialValues;
    private int ABN_FORMAT_NUMBER;
    private int ACCOUNT_FORMAT_NUMBER;
    private ArrayList<String> criticalInformation;
    private int correctionForSecondTenat;


    public Form() {
        this.xyDataSet = createData();
        this.name = createName();
        this.specialValues = createSpecialValues();
        this.ABN_FORMAT_NUMBER = createABNFN();
        this.ACCOUNT_FORMAT_NUMBER = 5;
        this.correctionForSecondTenat = createCorrectionAmountForSecondTenant();
        this.criticalInformation = createCriticalInformation();
    }

    public Map<String, String> xyDataSet() {
        return xyDataSet;
    }

    public int name() {
        return name;
    }

    public int ABN_FORMAT_NUMBER() {
        return ABN_FORMAT_NUMBER;
    }

    public int ACCOUNT_FORMAT_NUMBER() {
        return ACCOUNT_FORMAT_NUMBER;
    }

    public ArrayList<String> getCriticalInformation() {
        return criticalInformation;
    }

    public int getCorrectionForSecondTenant() {
        return correctionForSecondTenat;
    }

    protected abstract int createCorrectionAmountForSecondTenant();
    protected abstract int createABNFN();
    protected abstract HashMap<String, String> createData();
    protected abstract int createName();
    protected abstract String[] createSpecialValues();
    protected abstract ArrayList<String> createCriticalInformation();
    public abstract String convertSpecialValue(String key, String value);
    public abstract String formatABN(String value, int spaces);
    public abstract String formatAccountNumber(String value, int spaces);
    public abstract String[] XYPositionsForCurrentDate(Boolean secondTenant);
}
