package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void previousDirectionIsCounterclockwise(){
        //given
        var directions=MapDirection.values();
        //when
        for(int i=0; i<4; i++){
            directions[i]=directions[i].previous();
        }
        //then
        MapDirection[] excpectedarray = {MapDirection.WEST,MapDirection.EAST,MapDirection.SOUTH,MapDirection.NORTH};
        assertArrayEquals(excpectedarray,directions);
    }

    @Test
    void nextDirectionIsCounterclockwise(){
        //given
        var directions=MapDirection.values();
        //when
        for(int i=0; i<4; i++){
            directions[i]=directions[i].next();
        }
        //then
        MapDirection[] excpectedarray = {MapDirection.EAST,MapDirection.WEST,MapDirection.NORTH,MapDirection.SOUTH};
        assertArrayEquals(excpectedarray,directions);
    }
}