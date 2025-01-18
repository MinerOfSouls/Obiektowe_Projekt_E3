package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.MapDirection;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class SimulationPresenter {
    public Button defaultValueButton;
    public Button submitValuesButton;

    @FXML
    public GridPane settings;

    public BorderPane content;
    public TextField configNameTextField;
    public ComboBox<String> configComboBox;
    public Label loadingErrorLabel;

    @FXML private SettingsPresenter settingsController;

    public static Map<MapDirection, Image> animalTextures = Map.of(
            MapDirection.NORTH, new Image("north.png"),
            MapDirection.NORTHEAST, new Image("northeast.png"),
            MapDirection.EAST, new Image("east.png"),
            MapDirection.SOUTHEAST, new Image("southeast.png"),
            MapDirection.SOUTH, new Image("south.png"),
            MapDirection.SOUTHWEST, new Image("southwest.png"),
            MapDirection.WEST, new Image("west.png"),
            MapDirection.NORTHWEST, new Image("northwest.png")
    );

    public static Image grassTexture = new Image("grass.png");
    public static Image groundTexture = new Image("ground.png");

    public void initialize(){
        content.centerProperty().setValue(settings);
        configComboBox.setItems(new ObservableListWrapper<>(settingsController.getConfigNames()));
    }

    private void showAvailableConfigurations(){

    }

    public void insertDefaultValuesClicked(ActionEvent actionEvent) {
        settingsController.insertDefaults();
    }

    public void submitValues(ActionEvent actionEvent) {
        try{
            Simulation simulation = settingsController.getSelectedSimulation();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("globe.fxml"));
            BorderPane viewRoot = loader.load();
            GlobePresenter presenter = loader.getController();
            simulation.getMap().addListener(presenter);
            presenter.setSimulation(simulation);
            presenter.mapChanged(simulation.getMap(), "");
            configureStage(stage, viewRoot);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }


    public void saveCurrentConfig(ActionEvent actionEvent) {
        if (configNameTextField.getCharacters().isEmpty()){
            loadingErrorLabel.setText("Nazwa konfiguracji pusta");
            return;
        }
        else {
            loadingErrorLabel.setText("");
        }
        settingsController.saveCurrentConfiguration(configNameTextField.getCharacters().toString());
        configComboBox.setItems(new ObservableListWrapper<>(settingsController.getConfigNames()));
    }

    public void loadChosenConfig(ActionEvent actionEvent) {
        settingsController.readConfiguration(configComboBox.getSelectionModel().getSelectedItem());
    }
}
