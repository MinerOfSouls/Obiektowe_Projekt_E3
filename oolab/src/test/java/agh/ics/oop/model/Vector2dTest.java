package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    //tests for Vector2d.equals
    @Test
    void equalsTrueForItself() {
        //given
        var vector = new Vector2d(1, 1);
        //then
        assertTrue(vector.equals(vector));
    }

    @Test
    void equalsTrueForSameVactor() {
        //given
        var vector1 = new Vector2d(1, 1);
        var vector2 = new Vector2d(1, 1);
        //then
        assertTrue(vector1.equals(vector2));
        assertTrue(vector2.equals(vector1));
    }

    @Test
    void equalsFalseForDiffrentVector() {
        //given
        var vector1 = new Vector2d(1, 1);
        var vector2 = new Vector2d(-1, -1);
        //then
        assertFalse(vector1.equals(vector2));
        assertFalse(vector2.equals(vector1));
    }

    @Test
    void equalsFalseForDiffrentObject() {
        //given
        Vector2d vector = new Vector2d(1, 1);
        String string = "";
        //then
        assertFalse(vector.equals(string));
    }

    @Test
    void toStringReturnsRightString() {
        //given
        Vector2d vector = new Vector2d(1, 1);
        //then
        assertEquals("(1,1)", vector.toString());
    }

    //tests precedes and follows
    @Test
    void precedesReturnsTrueWhenSmallerAndFalseWhenBigger() {
        //given
        var vector1 = new Vector2d(1, 1);
        var vector2 = new Vector2d(2, 2);
        var vector3 = new Vector2d(1, 2);
        //then
        assertTrue(vector1.precedes(vector2));
        assertFalse(vector2.precedes(vector1));
        assertTrue(vector1.precedes(vector3));
    }

    @Test
    void followsReturnsFalseWhenSmallerAndTrueWhenBigger() {
        //given
        var vector1 = new Vector2d(1, 1);
        var vector2 = new Vector2d(2, 2);
        var vector3 = new Vector2d(1, 2);
        //then
        assertTrue(vector2.follows(vector1));
        assertFalse(vector1.follows(vector2));
        assertTrue(vector2.follows(vector3));
    }

    //tests upperRight and lowerLeft
    @Test
    void upperRightReturnsSameWhenGivenSame() {
        //given
        var vector = new Vector2d(1, 1);
        //then
        var expectedvector = new Vector2d(1, 1);
        assertEquals(expectedvector, vector.upperRight(vector));
    }

    @Test
    void lowerLeftReturnsSameWhenGivenSame() {
        //given
        var vector = new Vector2d(1, 1);
        //then
        var expectedvector = new Vector2d(1, 1);
        assertEquals(expectedvector, vector.lowerLeft(vector));
    }

    @Test
    void upperRightReturnsMaximumCoordinates() {
        //given
        var vector1 = new Vector2d(1, 10);
        var vector2 = new Vector2d(10, 1);
        var expectedvector = new Vector2d(10, 10);
        //then
        assertEquals(expectedvector, vector1.upperRight(vector2));
    }

    @Test
    void lowerLeftReturnsMinimumCoordinates() {
        //given
        var vector1 = new Vector2d(1, 10);
        var vector2 = new Vector2d(10, 1);
        var expectedvector = new Vector2d(1, 1);
        //then
        assertEquals(expectedvector, vector1.lowerLeft(vector2));
    }

    //tests add and sub
    @Test
    void addAdds() {
        //given
        var vector1 = new Vector2d(1, 1);
        var vector2 = new Vector2d(2, 2);
        var expectedvector = new Vector2d(3, 3);
        //then
        assertEquals(expectedvector, vector1.add(vector2));
        assertEquals(expectedvector, vector2.add(vector1));
    }

    @Test
    void addAddsZero() {
        //given
        var vector1 = new Vector2d(1, 1);
        var vector2 = new Vector2d(0, 0);
        var expectedvector = new Vector2d(1, 1);
        //then
        assertEquals(expectedvector, vector1.add(vector2));
        assertEquals(expectedvector, vector2.add(vector1));
    }

    @Test
    void addSubtractsWhenGivenNegative() {
        //given
        var vector1 = new Vector2d(2, 2);
        var vector2 = new Vector2d(-1, -1);
        var expectedvector = new Vector2d(1, 1);
        //then
        assertEquals(expectedvector, vector1.add(vector2));
    }

    @Test
    void subSubtracts() {
        //given
        var vector1 = new Vector2d(1, 1);
        var vector2 = new Vector2d(2, 2);
        var expectedvector1 = new Vector2d(1, 1);
        var expectedvector2 = new Vector2d(-1, -1);
        //then
        assertEquals(expectedvector2, vector1.subtract(vector2));
        assertEquals(expectedvector1, vector2.subtract(vector1));
    }

    @Test
    void subtractZero() {
        //given
        var vector1 = new Vector2d(1, 1);
        var vector2 = new Vector2d(0, 0);
        var expectedvector1 = new Vector2d(1, 1);
        var expectedvector2 = new Vector2d(-1, -1);
        //then
        assertEquals(expectedvector1, vector1.subtract(vector2));
        assertEquals(expectedvector2, vector2.subtract(vector1));
    }

    @Test
    void subtractAddsWhenGivenNegative() {
        //given
        var vector1 = new Vector2d(2, 2);
        var vector2 = new Vector2d(-1, -1);
        var expectedvector = new Vector2d(3, 3);
        //then
        assertEquals(expectedvector, vector1.subtract(vector2));
    }

    //test opposite
    @Test
    void oppositeGivesTheOppositeVector() {
        //given
        var vector1 = new Vector2d(1, 1);
        var vector2 = new Vector2d(-1, -1);
        var vector3 = new Vector2d(1, -1);
        var vector4 = new Vector2d(-1, 1);
        //then
        assertEquals(vector1, vector2.opposite());
        assertEquals(vector2, vector1.opposite());
        assertEquals(vector3, vector4.opposite());
        assertEquals(vector4, vector3.opposite());
    }

    @Test
    void oppositeReturnZeroWhenGivenZero() {
        //given
        var vector1 = new Vector2d(0, 0);
        var vector2 = new Vector2d(1, 0);
        var vector3 = new Vector2d(0, 1);

        var expectedvector1 = new Vector2d(0, 0);
        var expectedvector2 = new Vector2d(-1, 0);
        var expectedvector3 = new Vector2d(0, -1);
        //then
        assertEquals(expectedvector1, vector1.opposite());
        assertEquals(expectedvector2, vector2.opposite());
        assertEquals(expectedvector3, vector3.opposite());

    }

}