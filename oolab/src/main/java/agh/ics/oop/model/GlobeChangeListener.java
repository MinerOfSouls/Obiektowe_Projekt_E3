package agh.ics.oop.model;

import agh.ics.oop.model.maps.AbstractGlobeMap;
import agh.ics.oop.model.maps.Globe;

public interface GlobeChangeListener {
    void mapChanged(AbstractGlobeMap globe, String message);

}
