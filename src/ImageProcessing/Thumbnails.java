package ImageProcessing;

import javafx.scene.image.ImageView;

public class Thumbnails {

    public Thumbnails() {
    }

    public ImageView createThumbnail(ImageView imageView, int width, int height) {
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }
}
