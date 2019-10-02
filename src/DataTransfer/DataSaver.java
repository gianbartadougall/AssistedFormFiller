package DataTransfer;

import Objects.House;
import Objects.Owner;
import Objects.Tenant;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class DataSaver {

    Data data;

    public DataSaver(Data data) {
        this.data = data;
    }

    public void saveData(String path) {
        //System.out.println("SAVING DATA!!!!!!!!!!!!!!");

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(path));
            int i = 0;
            for (Map.Entry<String, Owner> e : data.getOwners().entrySet()) {
                pw.println(e.getKey()+">");
                int j=0;
                for (Map.Entry<String,String> e1 : e.getValue().attributes().entrySet()) {
                    String val = checkForNull(e1.getValue());
                    pw.println(j==0? e1.getKey()+":"+val : "/=/"+e1.getKey()+":"+val);
                    j++;
                }
                pw.println(i<data.getOwners().size()-1 ? "<" : ";");
                i++;
            }
            i=0;
            for (Map.Entry<String, Tenant> e : data.getTenants().entrySet()) {
                //System.out.println("name: " + e.getKey());
                pw.println(e.getKey()+">");
                int j=0;
                for (Map.Entry<String,String> e1 : e.getValue().attributes().entrySet()) {
                    String val = checkForNull(e1.getValue());
                    pw.println(j==0? e1.getKey()+":"+val : "/=/"+e1.getKey()+":"+val);
                    j++;
                }
                pw.println(i<data.getTenants().size()-1 ? "<" : ";");
                i++;
            }
            i=0;
            for (Map.Entry<String, House> e : data.getHouses().entrySet()) {
                pw.println(e.getKey()+">");
                int j=0;
                for (Map.Entry<String,String> e1 : e.getValue().attributes().entrySet()) {
                    String val = checkForNull(e1.getValue());
                    pw.println(j==0? e1.getKey()+":"+val : "/=/"+e1.getKey()+":"+val);
                    j++;
                }
                pw.println(i<data.getHouses().size()-1 ? "<" : ";");
                i++;
            }

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String checkForNull(String val) {
        return val == null ? "" : val;
    }
}
