package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void givesRightDirections(){
        //given
        String[] commands={"f","b","l","r"};
        List<MoveDirection> parsed;
        MoveDirection[] expected = {MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.LEFT,MoveDirection.RIGHT};
        //when
        parsed=OptionsParser.parseMoveDirections(commands);
        //then
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], parsed.get(i));
        }
    }

    @Test
    void throwsWhenCommandsContainInvalidArguments(){
        //given
        String[] commands={"j","l","r","f","b","k","r","h","b","p"};
        //then
        assertThrows(IllegalArgumentException.class,()->{OptionsParser.parseMoveDirections(commands);});
    }

    @Test
    void returnsEmptyArrayWhenGivenNoCommands(){
        //given
        String[] commands = {};
        List<MoveDirection> parsed;
        //then
        parsed=OptionsParser.parseMoveDirections(commands);
        //then
        assertTrue(parsed.isEmpty());
    }

    @Test
    void throwsWhenGivenOnlyInvalid(){
        //given
        String[] commands ={"k","p","i","q","w","e","y","t"};
        //then
        assertThrows(IllegalArgumentException.class,()->{OptionsParser.parseMoveDirections(commands);});
    }

}