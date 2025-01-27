package agh.ics.oop.view;

import agh.ics.oop.presenter.MainWindowPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GlobeApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane viewRoot = loader.load();
            MainWindowPresenter presenter = loader.getController();
            configureStage(primaryStage, viewRoot);
            primaryStage.show();
            presenter.initialize();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An IO exception has occurred");
        }
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
