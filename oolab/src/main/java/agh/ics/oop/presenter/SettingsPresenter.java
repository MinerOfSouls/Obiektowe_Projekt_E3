package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.IncorrectPositionException;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.maps.AbstractGlobeMap;
import agh.ics.oop.model.maps.Boundary;
import agh.ics.oop.model.maps.CorpseGlobe;
import agh.ics.oop.model.maps.EquatorGlobe;
import agh.ics.oop.model.util.RandomPositionGenerator;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SettingsPresenter {

    public ComboBox<String> behaviourComboBox;
    public ComboBox<String> mapVariantComboBox;

    public Spinner<Integer> mapWidthSpinner;
    public Spinner<Integer> mapHeightSpinner;

    public Spinner<Integer> startingPlantAmountSpinner;
    public Spinner<Integer> energyPlantSpinner;
    public Spinner<Integer> dailyPlantAmountSpinner;

    public Spinner<Integer> startingAnimalsSpinner;
    public Spinner<Integer> genomeLengthSpinner;
    public Spinner<Integer> maxMutationAmountSpinner;
    public Spinner<Integer> minMutationAmountSpinner;
    public Spinner<Integer> birthCostSpinner;
    public Spinner<Integer> satisfiedEnergySpinner;
    public Spinner<Integer> startingEnergySpinner;

    public Label decayTimeLabel;
    public Spinner<Integer> decayTimeSpinner;
    public CheckBox statisticsSaveCheckBox;

    private int currentMapId = 0;

    public void insertDefaults(){
        mapWidthSpinner.getValueFactory().setValue(10);
        mapHeightSpinner.getValueFactory().setValue(10);

        startingPlantAmountSpinner.getValueFactory().setValue(5);
        energyPlantSpinner.getValueFactory().setValue(1);
        dailyPlantAmountSpinner.getValueFactory().setValue(5);
        startingAnimalsSpinner.getValueFactory().setValue(4);
        genomeLengthSpinner.getValueFactory().setValue(5);
        maxMutationAmountSpinner.getValueFactory().setValue(2);
        minMutationAmountSpinner.getValueFactory().setValue(0);
        decayTimeSpinner.getValueFactory().setValue(5);

        birthCostSpinner.getValueFactory().setValue(5);
        satisfiedEnergySpinner.getValueFactory().setValue(10);
        startingEnergySpinner.getValueFactory().setValue(10);

        statisticsSaveCheckBox.setSelected(false);
    }

    public void mapVariantSelected(javafx.event.ActionEvent actionEvent) {
        switch (mapVariantComboBox.getSelectionModel().getSelectedIndex()){
            case 0 -> {
                decayTimeLabel.visibleProperty().setValue(false);
                decayTimeLabel.managedProperty().setValue(false);
                decayTimeSpinner.visibleProperty().setValue(false);
                decayTimeSpinner.managedProperty().setValue(false);
            }
            case 1 -> {
                decayTimeLabel.visibleProperty().setValue(true);
                decayTimeLabel.managedProperty().setValue(true);
                decayTimeSpinner.visibleProperty().setValue(true);
                decayTimeSpinner.managedProperty().setValue(true);
            }
        }
    }

    private AbstractGlobeMap getSelectedMap(){
        AbstractGlobeMap map;

        boolean animalVariant;
        if (behaviourComboBox.getSelectionModel().getSelectedIndex() == 1 || behaviourComboBox.getValue().startsWith("2")){
            animalVariant = true;
        }
        else{
            animalVariant = false;
        }


        switch (mapVariantComboBox.getSelectionModel().getSelectedIndex()){
            case 0 -> {
                currentMapId++;
                map = new EquatorGlobe(currentMapId, mapWidthSpinner.getValue(), mapHeightSpinner.getValue(),
                        startingPlantAmountSpinner.getValue(),
                        satisfiedEnergySpinner.getValue(), birthCostSpinner.getValue(), minMutationAmountSpinner.getValue(),
                        maxMutationAmountSpinner.getValue(), animalVariant);
            }
            case 1 -> {
                currentMapId++;
                map = new CorpseGlobe(currentMapId, mapWidthSpinner.getValue(), mapHeightSpinner.getValue(),
                        startingPlantAmountSpinner.getValue(), decayTimeSpinner.getValue(),
                        satisfiedEnergySpinner.getValue(), birthCostSpinner.getValue(), minMutationAmountSpinner.getValue(),
                        maxMutationAmountSpinner.getValue(), animalVariant);
            }
            default -> {
                if(mapVariantComboBox.getValue().startsWith("2")){
                    currentMapId++;
                    map = new CorpseGlobe(currentMapId, mapWidthSpinner.getValue(), mapHeightSpinner.getValue(),
                            startingPlantAmountSpinner.getValue(), decayTimeSpinner.getValue(),
                            satisfiedEnergySpinner.getValue(), birthCostSpinner.getValue(), minMutationAmountSpinner.getValue(),
                            maxMutationAmountSpinner.getValue(), animalVariant);
                }
                else{
                    currentMapId++;
                    map = new EquatorGlobe(currentMapId, mapWidthSpinner.getValue(), mapHeightSpinner.getValue(),
                            startingPlantAmountSpinner.getValue(),
                            satisfiedEnergySpinner.getValue(), birthCostSpinner.getValue(), minMutationAmountSpinner.getValue(),
                            maxMutationAmountSpinner.getValue(), animalVariant);
                }
            }
        }
        return map;
    }

    private void placeAnimals(AbstractGlobeMap map){
        RandomPositionGenerator generator = new RandomPositionGenerator(map.getCurrentBounds(),
                startingAnimalsSpinner.getValue(), List.of(), null);
        Random random = new Random();
        boolean animalVariant;
        if (behaviourComboBox.getSelectionModel().getSelectedIndex() == 1){
            animalVariant = true;
        }
        else{
            animalVariant = false;
        }
        try {
            for (Vector2d position : generator) {
                List<Integer> genome = new ArrayList<>();
                for (int i = 0; i < genomeLengthSpinner.getValue(); i++) {
                    genome.add(random.nextInt(8));
                }

                map.place(new Animal(position, genome, startingEnergySpinner.getValue(), 0,
                        minMutationAmountSpinner.getValue(), maxMutationAmountSpinner.getValue(), animalVariant));
            }
        } catch (IncorrectPositionException e) {
            throw new RuntimeException(e);
        }
    }

    public Simulation getSelectedSimulation(){
        AbstractGlobeMap map = getSelectedMap();
        placeAnimals(map);
        return new Simulation(map, dailyPlantAmountSpinner.getValue());
    }

    private void createConfigFile(File file) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("configurations");
        document.appendChild(root);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.transform(source, new StreamResult(file));
    }

    public void saveCurrentConfiguration(String configName){
        try{
            File configs = new File("configurations.xml");
            if(!configs.exists()){
                createConfigFile(configs);
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(configs);
            Element root = document.getDocumentElement();

            Element configuration = document.createElement("configuration");
            configuration.setAttribute("name", configName);
            configuration.setAttribute("mapHeight", mapHeightSpinner.getValue().toString());
            configuration.setAttribute("mapWidth", mapWidthSpinner.getValue().toString());
            configuration.setAttribute("startingPlants", startingPlantAmountSpinner.getValue().toString());
            configuration.setAttribute("plantEnergy", energyPlantSpinner.getValue().toString());
            configuration.setAttribute("dailyPlants", dailyPlantAmountSpinner.getValue().toString());
            configuration.setAttribute("mapVariant", String.valueOf(mapVariantComboBox.getSelectionModel().getSelectedIndex()));

            configuration.setAttribute("startingAnimals", startingAnimalsSpinner.getValue().toString());
            configuration.setAttribute("startingEnergy", startingEnergySpinner.getValue().toString());
            configuration.setAttribute("fullEnergy", satisfiedEnergySpinner.getValue().toString());
            configuration.setAttribute("birthCost", birthCostSpinner.getValue().toString());
            configuration.setAttribute("minMutations", minMutationAmountSpinner.getValue().toString());
            configuration.setAttribute("maxMutations", maxMutationAmountSpinner.getValue().toString());
            configuration.setAttribute("genomeLength", genomeLengthSpinner.getValue().toString());
            configuration.setAttribute("animalVariant", String.valueOf(behaviourComboBox.getSelectionModel().getSelectedIndex()));

            configuration.setAttribute("decayTime", decayTimeSpinner.getValue().toString());

            configuration.setAttribute("saveStats", String.valueOf(statisticsSaveCheckBox.isSelected() ? 1 : 0));

            root.appendChild(configuration);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.transform(source, new StreamResult(configs));

        } catch (ParserConfigurationException | TransformerException | IOException | SAXException e) {
            //ignored
        }
    }

    public List<String> getConfigNames(){
        List<String> names = new ArrayList<>();
        try {
            File configs = new File("configurations.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(configs);

            NodeList list = document.getElementsByTagName("configuration");
            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                names.add(node.getAttributes().getNamedItem("name").getNodeValue());
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            //ignored
        }
        return names;
    }

    public void readConfiguration(String name){
        try {
            File configs = new File("configurations.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(configs);

            NodeList list = document.getElementsByTagName("configuration");
            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                if(node.getAttributes().getNamedItem("name").getNodeValue().equals(name)){
                    mapWidthSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("mapWidth").getNodeValue()));
                    mapHeightSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("mapHeight").getNodeValue()));

                    startingPlantAmountSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("startingPlants").getNodeValue()));
                    energyPlantSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("plantEnergy").getNodeValue()));
                    dailyPlantAmountSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("dailyPlants").getNodeValue()));
                    startingAnimalsSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("startingAnimals").getNodeValue()));
                    genomeLengthSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("genomeLength").getNodeValue()));
                    maxMutationAmountSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("maxMutations").getNodeValue()));
                    minMutationAmountSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("minMutations").getNodeValue()));
                    decayTimeSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("decayTime").getNodeValue()));

                    birthCostSpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("birthCost").getNodeValue()));
                    satisfiedEnergySpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("fullEnergy").getNodeValue()));
                    startingEnergySpinner.getValueFactory().setValue(Integer.valueOf(node.getAttributes().getNamedItem("startingEnergy").getNodeValue()));

                    statisticsSaveCheckBox.setSelected(Integer.valueOf(node.getAttributes().getNamedItem("saveStats").getNodeValue()).equals(1));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            //ignored
        }
    }
}
