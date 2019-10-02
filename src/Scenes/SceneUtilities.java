package Scenes;

import Enums.Enum;
import Utilities.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SceneUtilities {

    private Utils utils = new Utils();

    public Button configureButton(String title, String color, int columnSpan, int width, int height) {
        Button button = new Button(title);
        button.setMinWidth(width);
        button.setMaxHeight(height);
        button.setStyle("-fx-background-color: " + color + ";");
        GridPane.setColumnSpan(button, columnSpan);
        return button;
    }

    public Boolean dataIsInvalid(ArrayList<TextField> list) {
        for (TextField textField : list) {
            if (dataIsInvalid(textField.getText())) {
                return true;
            }
        }
        return false;
    }

    public Boolean dataIsInvalid(TextField[] list) {
        for (TextField textField : list) {
            if (dataIsInvalid(textField.getText())) {
                return true;
            }
        }
        return false;
    }

    private Boolean dataIsInvalid(String val) {
        for (String s : Enum.INVALID_CHARACTERS) {
            if (val == null) {
                continue;
            }
            if (val.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public int getIndexInAlphabeticalOrder(ArrayList<GridPane> list, GridPane gridPane) {
        String address1 = removeInt(gridPane.getChildren().get(0).getId()).toLowerCase();
        for (int i=0; i<list.size(); i++) {
            String address2 = removeInt(list.get(i).getChildren().get(0).getId()).toLowerCase();
            if (address2.compareTo(address1) > 0) {
                return i;
            }
        }
        return list.size();
    }

    private String removeInt(String val) {
        StringBuilder newValue = new StringBuilder();
        System.out.println("val" + val);
        String[] list = val.split("");
        for (String s : list) {
            if (!s.matches("\\d")) {
                newValue.append(s);
            }
        }
        return newValue.toString().trim();
    }

    public int houseToInt(String val) {
        switch (val) {
            case "Flat/Unit": return 0;
            case "House": return 1;
            case "Town House": return 2;
            case "Movable Dwelling Site": return 3;
            default: return 5;
        }
    }

    public String intToType(String type) {
        if (type.isBlank()) {
            type = "5";
        }
        switch (Integer.parseInt(type)) {
            case 0: return "Flat/Unit";
            case 1: return "House";
            case 2: return "Town House";
            case 3: return "Movable Dwelling Site";
            default: return "unknown dwelling type";
        }
    }

    public void addSortedListToGridpane(GridPane gridpane, ArrayList<GridPane> sortedList) {
        int row = 0, col =0;
        for (int i=0; i<sortedList.size(); i++) {
            if (col == 4) {
                col = 0;
                row++;
            }
            gridpane.add(sortedList.get(i), col, row);
            col++;
        }
    }

    public void confirmObjectDelete(Stage stage, Button button) {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirm Delete");
        stage.setMinWidth(300);

        Label label = new Label("Delete this Tenant?");
        label.setPadding(new Insets(20, 20, 20, 20));
        label.setFont(new Font("Arial", 16));

        Button cancelButton = utils.createButton("Cancel", 80, 25);
        cancelButton.setOnAction(e -> stage.close());

        HBox hBox = utils.createHbox(20, 100, Pos.CENTER);
        hBox.getChildren().addAll(button, cancelButton);
        VBox vBox = utils.createVbox(100, 80);
        vBox.getChildren().addAll(label, hBox);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public Label newLabel(String title, String id, int height) {
        Label label = new Label(title);
        label.setId(id);
        label.setMinHeight(height);
        return label;
    }

    public GridPane setUpGridPane(GridPane pane, int vGap, int hGap, int insets, Pos position,
                                  int width, int height, String color, String borderColor) {
        pane.setMinSize(width, height);
        pane.setAlignment(position);
        pane.setPadding(new Insets(insets,insets,insets,insets));
        pane.setHgap(hGap);
        pane.setVgap(vGap);
        pane.setStyle("-fx-background-color:" + color +"; -fx-border-color:" + borderColor + ";");
        return pane;
    }


}
