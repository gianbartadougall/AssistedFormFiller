package Display;

import DataTransfer.Data;
import Forms.BondLodgmentForm;
import Forms.Form;
import Forms.RefundRentalBondForm;
import ImageProcessing.ImageLoader;
import ImageProcessing.Thumbnails;
import Objects.House;
import Objects.Owner;
import Objects.Tenant;
import Scenes.HouseScenes.NewHouse;
import Scenes.HouseScenes.ViewHousesInDatabase;
import Scenes.OwnerScenes.NewOwner;
import Scenes.OwnerScenes.ViewOwnersInDatabase;
import Scenes.TenantScenes.NewTenant;
import Scenes.TenantScenes.ViewTenantsInDatabase;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Display {

    private Stage stage;
    private Scene scene;
    private Data data;
    private ImageLoader imgEditor;
    private NewHouse newHouseScene;
    private NewTenant newTenantScene;
    private NewOwner newOwnerScene;
    private ViewOwnersInDatabase viewOwnersScene;
    private ViewTenantsInDatabase viewTenantsScene;
    private ViewHousesInDatabase viewHouseScene;
    private BorderPane borderPane;
    private GridPane forms;
    private ScrollPane scrollPane;
    private HBox optionBar, createNewFormBar, createObjects;
    private int width, height;
    private ComboBox<String> ownerDropDown, tenant1DropDown, tenant2DropDown, houseDropDown;
    private String title;
    private Button createFormButton, createHouse, newTenant, newOwner, viewHouses, viewTenants, viewOwners;
    private Node selectedNode;
    private ArrayList<String> names = new ArrayList<>();

    public Display(int width, int height, String title, Data data) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.data = data;
        imgEditor = new ImageLoader();
        createDisplay1();
    }

    private void createDisplay1() {
        stage = new Stage();
        stage.setMaxWidth(width);
        borderPane = new BorderPane();

        configureForms();
        scrollPane = new ScrollPane(forms);

        optionBar = new HBox();
        createNewFormBar = new HBox();
        createObjects = new HBox();
        VBox box = new VBox();
        box.getChildren().add(createObjects);
        box.getChildren().add(optionBar);
        configureBorderPane(box);
        addFormImages();
        configureAllNodes();

        //Creating scenes
        scene = new Scene(borderPane, width, height);

        newHouseScene = new NewHouse(stage, width, height, this, data, scene);
        newOwnerScene = new NewOwner(stage, this, scene, width, height, data);

        newTenantScene = new NewTenant(stage, this, scene, width, height, data);
        viewOwnersScene = new ViewOwnersInDatabase(stage, scene, data, width, height, this);
        viewTenantsScene = new ViewTenantsInDatabase(data, stage, scene, width, height, this);
        viewHouseScene = new ViewHousesInDatabase(data, stage, scene, width, height, this);

        stage.setScene(scene);
        stage.setX(2000);
        stage.show();
    }

    private void configureBorderPane(VBox box) {
        borderPane.setTop(box);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(createNewFormBar);
    }


    private void configureForms() {
        forms = new GridPane();
        forms.setPadding(new Insets(20,20,20,20));
        forms.setHgap(50);
        forms.setVgap(0);
        forms.setStyle("-fx-background-color:  #b0e0e6; -fx-border-color: #b0e0e6; -fx-focus-color: #40e0d0; -fx-background-insets: -2;");
    }

    private void configureAllNodes() {
        configureOptionBar();
        createOptionBarDropDowns();
        configureNewFormBar();
        configurCreateObjects();
    }

    public void createGridPaneListner() {
        forms.setOnMouseClicked(e -> {
            double x = e.getX(), y = e.getY();
            for (Node node : forms.getChildren()) {
                if (node.getBoundsInParent().contains(new Point2D(x, y))) {
                    selectNode(node);
                }
            }
        });
    }

    private void selectNode(Node node) {
        if (selectedNode != null) {
            selectedNode.setStyle("-fx-border-color: transparent;");
        }
        node.setStyle("-fx-border-color: blue;");
        selectedNode = node;
    }

    private void createOptionBarDropDowns() {
        ownerDropDown = new ComboBox<>();
        tenant1DropDown = new ComboBox<>();
        tenant2DropDown = new ComboBox<>();
        houseDropDown = new ComboBox<>();
        optionBar.getChildren().addAll(new Label("Owner"),ownerDropDown, new Label("Tenant 1"), tenant1DropDown,
                new Label("Tenant 2"), tenant2DropDown, new Label("Address"), houseDropDown);
        optionBar.setSpacing(20);
        optionBar.setStyle("-fx-background-color: #b0e0e6; -fx-border-color: #b0e0e6; -fx-border-width: 3; -fx-border-insets: -2;");
    }

    private void configureOptionBar() {
        optionBar.setAlignment(Pos.CENTER);
        optionBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        optionBar.setMinHeight(60);
    }

    private void configurCreateObjects() {
        newOwner = new Button("New Owner");
        styleButton(newOwner);
        newTenant = new Button("New Tenant");
        styleButton(newTenant);
        createHouse = new Button("New House");
        styleButton(createHouse);
        viewOwners = new Button("View Owners");
        styleButton(viewOwners);
        viewTenants = new Button("View Tenants");
        styleButton(viewTenants);
        viewHouses = new Button("View Houses");
        styleButton(viewHouses);
        createObjects.getChildren().addAll(newOwner, newTenant, createHouse, viewOwners, viewTenants, viewHouses);
        createObjects.setSpacing(20);
        createObjects.setStyle("-fx-background-color:   #dcdcdc;");
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color:  #dcdcdc; -fx-background-radius: 0,0,0,0;");
    }

    private void configureNewFormBar() {
        createFormButton = new Button("Create");
        createNewFormBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        createNewFormBar.setMinHeight(60);
        createNewFormBar.getChildren().add(createFormButton);
        createNewFormBar.setAlignment(Pos.CENTER);
        if (names.size() == 0) {
            createFormButton.setDisable(true);
            displayFormError();
        }
    }

    private void displayFormError() {
        System.out.println("NO FORMS WERE FOUND");
    }

    private void addFormImages() {
        // forms dir is just to get names of the directory to find the names of all the forms
        String formsDir = Paths.get("").toAbsolutePath().toString()+"/res/Forms/";
        //Path name is for loading images -> needs toURI to load images
        String pathName = Paths.get("").toAbsolutePath().toUri().toString()+"/res/Forms/";

        File files = new File(formsDir);

        if (files.listFiles() == null) {
            //need to have error, unable to locate forms
            return;
        }

        for (File file : files.listFiles()) {
            if (file.isFile()) {
                names.add(file.getName());
            }
        }

        Thumbnails tmb = new Thumbnails();
        int j=0, p=0;
        for (int i=0; i<names.size(); i++) {
            if (i%2==0) {
                j++;
                p=0;
            }
            ImageView image = new ImageView(imgEditor.loadImage(pathName+names.get(i)));
            image = tmb.createThumbnail(image, 700, 725);

            Label label = new Label(createTitle(names.get(i)));
            label.setGraphic((tmb.createThumbnail(image, 700, 725)));
            label.setId(names.get(i));
            label.setFont(new Font("Arial", 30));
            label.setContentDisplay(ContentDisplay.BOTTOM);

            GridPane.setConstraints(label, p, j);
            forms.getChildren().add(label);
            p++;
        }
        if (forms.getChildren().size() > 0) {
            selectNode(forms.getChildren().get(0));
        }
    }

    private String createTitle(String name) {
        String[] plainText = name.split("\\.");
        String[] words = plainText[0].split("(?=\\p{Upper})");
        StringBuilder title = new StringBuilder();
        for (String s : words) {
            title.append(s+" ");
        }
        return title.toString();
    }

    public void populateDropDowns(Map<String,Owner> owners, Map<String,Tenant> tenants, Map<String,House> houses) {
        populateOwnerDropDown(owners);
        populateTenant1and2DropDown(tenants);
        populateHouseDropDown(houses);
    }

    private void populateOwnerDropDown(Map<String,Owner> owners) {
        for (Map.Entry<String, Owner> e : owners.entrySet()) {
            ownerDropDown.getItems().add(e.getKey());
        }
        ownerDropDown.getSelectionModel().select(0);
    }

    private void populateTenant1and2DropDown(Map<String,Tenant> tenants) {
        for (Map.Entry<String, Tenant> e : tenants.entrySet()) {
            tenant1DropDown.getItems().add(e.getKey());
            tenant2DropDown.getItems().add(e.getKey());
        }
        tenant2DropDown.getItems().add(0, "None");
        tenant1DropDown.getSelectionModel().select(0);
        tenant2DropDown.getSelectionModel().select(0);
    }

    private void populateHouseDropDown(Map<String, House> houses) {
        for (Map.Entry<String,House> e : houses.entrySet()) {
            houseDropDown.getItems().add(e.getKey());
        }
        houseDropDown.getSelectionModel().select(0);
    }

    public String getSelectedOwner() {
        return ownerDropDown.getSelectionModel().getSelectedItem();
    }

    public String getTenant1Selection() {
        return tenant1DropDown.getSelectionModel().getSelectedItem();
    }

    public String getTenant2Selection() {
        return tenant2DropDown.getSelectionModel().getSelectedItem();
    }

    public String getSelectedHouse() {
        return houseDropDown.getSelectionModel().getSelectedItem();
    }

    public Button getCreateFormButton() {
        return createFormButton;
    }

    public Form getSelectedForm() {
        return indexToForm(selectedNode.getId());
    }

    public String getPathForSelectedForm() {
        String path = Paths.get("").toAbsolutePath().toUri().toString()+"/res/Forms/";
        if (selectedNode.getId() == null) {
            return null;
        } else return path+selectedNode.getId();
    }

    private Form indexToForm(String id) {
        switch (id) {
            case "BondLodgement.png": return new BondLodgmentForm();
            case "SAFixedTerm1.png" : return new BondLodgmentForm();
            case "SAFixedTerm2.png" : return new BondLodgmentForm();
            case "SAFixedTerm3.png" : return new BondLodgmentForm();
            default: return new RefundRentalBondForm();
        }
    }

    public void createNewHouseScene() {
        stage.setScene(newHouseScene.getScene());
    }

    public void createNewTenantScene() {
        stage.setScene(newTenantScene.getScene());
    }

    public void createNewOwnerScene() {
        stage.setScene(newOwnerScene.getScene());
    }

    public Button newHouseButton() {
        return createHouse;
    }

    public Button newTenantButton() {
        return newTenant;
    }

    public Button newOwnerButton() {
        return newOwner;
    }

    public Button viewHousesButton() {
        return viewHouses;
    }

    public Button viewTenantsButton() {
        return viewTenants;
    }

    public Button viewOwnersButton() {
        return viewOwners;
    }

    public void createListOwnersScene() {
        stage.setScene(viewOwnersScene.getScene());
    }

    public void createListTenantsScene() {
        stage.setScene(viewTenantsScene.getScene());
    }

    public void createListHouseScene() {
        stage.setScene(viewHouseScene.getScene());
    }

    public void reloadOptionsBar() {
        ownerDropDown.getItems().clear();
        tenant1DropDown.getItems().clear();
        tenant2DropDown.getItems().clear();
        houseDropDown.getItems().clear();
        populateDropDowns(data.getOwners(), data.getTenants(), data.getHouses());
    }
}
