package agh.ics.oop.model;

import agh.ics.oop.model.maps.Globe;

public interface GlobeChangeListener {
    void mapChanged(Globe globe, String message);
}
