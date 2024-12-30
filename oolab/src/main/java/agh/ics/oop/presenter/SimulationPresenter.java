package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.WorldElement;
import agh.ics.oop.model.maps.Boundary;
import agh.ics.oop.model.maps.GrassField;
import agh.ics.oop.model.maps.WorldMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;

    @FXML
    private TextField moveList;
    @FXML
    private Label moveInfoLabel;
    @FXML
    private GridPane mapGrid;

    public void setMap(WorldMap map) {
        this.map = map;
    }

    private void drawMap(){
        clearGrid();
        Boundary bounds = map.getCurrentBounds();
        int xZero = bounds.lowerLeft().getX()-1;
        int yZero = bounds.upperRight().getY()-1;
        for (int i = bounds.lowerLeft().getX(); i < bounds.upperRight().getX()+2; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(30));
        }
        for (int i = bounds.lowerLeft().getY(); i < bounds.upperRight().getY()+1; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(30));
        }
        for (int i = xZero+1; i < bounds.upperRight().getX() + 1 ; i++) {
            var label = new Label(String.valueOf(i));
            mapGrid.add(label, Math.abs(i-xZero),0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = bounds.lowerLeft().getY(); i < yZero + 1; i++) {
            var label = new Label(String.valueOf(i));
            mapGrid.add(label, 0, Math.abs(i-yZero)+1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        var scaleLabel = new Label("y\\x");
        mapGrid.add(scaleLabel, 0, 0);
        GridPane.setHalignment(scaleLabel, HPos.CENTER);
        for (WorldElement element: map.getElements()){
            var label = new Label(element.toString());
            mapGrid.add(label,Math.abs(element.getPosition().getX()-xZero),Math.abs(element.getPosition().getY()-yZero)+1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        System.out.println(message);
        Platform.runLater(() -> {
            drawMap();
            if (moveInfoLabel.getText() == ""){
                moveInfoLabel.setText(message);
            } else {
                String newText = String.format("%s\n%s",moveInfoLabel.getText(),message);
                moveInfoLabel.setText(newText);
            }
        });
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        String[] commands = moveList.getText().split(" ");
        List<MoveDirection> directions = OptionsParser.parseMoveDirections(commands);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        GrassField field = new GrassField(10,1);
        this.setMap(field);
        field.registerListener(this);
        Simulation simulation =  new Simulation(positions, directions, field);
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        engine.runAsync();
    }
}
