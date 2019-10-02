package Utilities;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Utils {

    public String space(int numSpaces) {
        StringBuilder spaces = new StringBuilder();
        for (int i=0; i<numSpaces; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }

    public String spaceText(String value, int spaces, int intervalStart, int interval) {
        StringBuilder newValue = new StringBuilder();
        int index = 0;
        for (String s : value.split("")) {
            if (index == intervalStart-1) {
                newValue.append(s + space(spaces));
            } else if (index > intervalStart) {
                newValue.append(index % interval == 0 ? s + space(spaces) : s);
            } else newValue.append(s);
            index++;
        }
        return newValue.toString();
    }

    public String spaceText(String value, int spaces) {
        StringBuilder newValue = new StringBuilder();
        for (String s : value.split("")) {
            newValue.append(s + space(spaces));
        }
        return newValue.toString();
    }

    public String spaceDate(String value) {
        StringBuilder newDate = new StringBuilder();
        int i = 0;
        for (String s : value.split("/")) {
            newDate.append((i==0 || i==1) ? s+space(4) : s);
            i++;
        }
        return newDate.toString();
    }

    public Button createButton(String title, int width, int heigth) {
        Button button = new Button(title);
        button.setMinWidth(width);
        button.setMinHeight(heigth);
        button.setStyle("-fx-background-color: #b0e0e6; -fx-background-radius: 10;");
        return button;
    }

    public HBox createHbox(int inset, int spacing, Pos position) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(inset, inset, inset, inset));
        hBox.setSpacing(spacing);
        hBox.setAlignment(position);
        return hBox;
    }

    public VBox createVbox(int width, int height) {
        VBox box = new VBox();
        box.setMinHeight(height);
        return box;
    }


}
