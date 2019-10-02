package ImageProcessing;

import Forms.Form;
import Utilities.Utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static Enums.Enum.AMBIGUOUS_POSITION;
import static Enums.Enum.FALSE;

public class ImageLoader {

    Utils utils = new Utils();

    public Image loadImage(String path) {
        System.out.println(path);
        return new Image(path);
    }

    public void previewImage(Canvas canvas) {
        Stage stage = new Stage();
        HBox menuBar = setUpMenuBar(canvas);
        stage.setMaxWidth(canvas.getWidth()+20);
        VBox vBox = new VBox(new ScrollPane(new Pane(canvas)));
        vBox.getChildren().add(0, menuBar);
        Scene scene = new Scene(vBox, stage.getMaxWidth(), 1000);
        stage.setScene(scene);
        stage.setX(2000);
        stage.setY(0);
        stage.show();
    }

    private void shareImage() {
        System.out.println("sharing image");
    }

    private HBox setUpMenuBar(Canvas canvas) {
        HBox menuBar = new HBox();

        Button save = new Button("Save");
        save.setOnAction(e -> saveImage(canvas));
        Button share = new Button("Share");
        share.setOnAction(e -> shareImage());
        menuBar.getChildren().addAll(save, share);
        menuBar.setSpacing(20);
        return menuBar;
    }

    private void saveImage(Canvas canvas) {

        WritableImage image = new WritableImage((int)canvas.getWidth(), (int) canvas.getHeight());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*png", "png"));
        fileChooser.setDialogTitle("Save File");
        int userSelection = fileChooser.showSaveDialog(null);

        if(userSelection == JFileChooser.APPROVE_OPTION){
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null, image), null), "png",
                        (new File(fileChooser.getSelectedFile().getAbsoluteFile() + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("unable to save image");
        }
    }

    public Canvas addInformation(Image image, Map<String,String> formLayoutsForKeys, ArrayList<Map<String,String>> maps, Form form) {

        Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(image, 0, 0);

        String currentDate = utils.spaceDate(getCurrentDate());
        //140
        int i=0, correctionAmount = form.getCorrectionForSecondTenant();
        boolean secondTenant = false;

        for (Map<String, String> map : maps) {
            for (Map.Entry<String,String> entry : map.entrySet()) {
                //System.out.println("\n\nentering for object " + map.values());
                String[] data = getData(entry.getKey(), entry.getValue(), formLayoutsForKeys.get(entry.getKey()), form, i);
                if (data != null) {
                    int x = Integer.parseInt(data[1]), y = Integer.parseInt(data[2]);
                    //System.out.println("writing " + data[0] + "to the screen");
                    writeToImage(graphics, data[0], x, i==3 ? y+addCorrection(entry.getKey(), x, y, correctionAmount) : y);
                }
            }

            secondTenant = i==3;
            i++;
        }

        for (String s : form.XYPositionsForCurrentDate(secondTenant)) {
            String[] positions = s.split(",");
            writeToImage(graphics, currentDate, Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
        }

        return canvas;
    }

    private int addCorrection(String key, int x, int y, int correctionAmount) {
        //System.out.println("KEY: " + key + "X: " + x + " Y: " + y);
        return key.equals("tenantPaymentMethod") || key.equals("tenantReceivesSubsidy") ||
                (x == 0 && y == 0) ? 0 : correctionAmount;
    }

    private String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    private String[] getData(String key, String val, String keyLayout, Form form, int objectNumber) {
        if (keyLayout == null || key.equals(FALSE)) {
            return null;
        }
        String[] xyPosition = keyLayout.split(",");
        //System.out.println("KEY: " + key + " VAL: " + val + " KEYLAYOUT: " + keyLayout);
        if (conflictExists(key, objectNumber)) {
            return new String[] {"", "0", "0"};
        }

        if (keyLayout.equals(AMBIGUOUS_POSITION)) {

            if (!val.isBlank()) {
                //System.out.println("KEYVALUE: " + key + " is being made an x" );
                xyPosition = form.convertSpecialValue(key, val).split(",");
                val = "X";
            } else {
                xyPosition = FALSE.split(",");
            }
        }
        return new String[] {format(key, val, form), xyPosition[0], xyPosition[1]};
    }

    private boolean conflictExists(String key, int objectNumber) {
        //System.out.println("CHECKING FOR CONFLIC " + "KEY: " + key + " NUM " + objectNumber);

        if (key.equals("newBond") && objectNumber == 3) {
            return true;
        }

        if (key.equals("startDate") && objectNumber == 3) {
            return true;
        }

        if (key.equals("endDate") && objectNumber == 3) {
            return true;
        }

        if (key.equals("rentalBondNumber") && objectNumber == 3) {
            return true;
        }

        if (key.equals("tenantPaymentMethod") && objectNumber == 3) {
            return true;
        }
        return false;
    }

    private void writeToImage(GraphicsContext g, String val, double x, double y) {
        //System.out.println(" VAL: " + val + "x " + x + " y " + y);
        g.setFill(Color.BLACK);
        g.setFont(new Font("Arial", 16));
        g.fillText(val, x, y);
    }

    private String format(String key, String value, Form form) {

        if (key.contains("Mobile") || key.contains("Phone")) {
            return utils.spaceText(value, 2, 4, 3);
        }

        if (key.contains("ABN")) {
            return form.formatABN(value, form.ABN_FORMAT_NUMBER());
        }

        if (key.contains("Date")) {
            return utils.spaceDate(value);
        }

        if (key.contains("BSB")) {
            return utils.spaceText(value, 4);
        }

        if (key.contains("AccountNumber")) {
            return form.formatABN(value, form.ACCOUNT_FORMAT_NUMBER());
        }

        return value;
    }

}
