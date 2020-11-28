package backend;

import api.serializer.DTODeserializable;
import api.serializer.DTOSerializable;
import backend.manager.exceptions.IllegalCoordinateException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {

    public int getIndex(int size) {
         return row * size + column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isOdd() { return (row + column) % 2 == 0; }
    public boolean isEven() { return !isOdd(); }

    public Coordinate move(Direction direction){
        return move(direction, 1);
    }

    public Coordinate move(Direction direction, int size){
        return new Coordinate(this.column + direction.getX() * size, this.row + direction.getY() * size);
    }

    @Override
    public String toString() {
        return String.format("%s%d", toStringIndex(column), row);
    }

    public Coordinate(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Coordinate(String column, int row) {
        this.column = ofStringIndex(column);
        this.row = row;
    }

    public static Coordinate ofIndex(int index, int size) {
        return new Coordinate(index / size, index % size);
    }

    public static int ofStringIndex(String string) {
        string = string.toUpperCase();
        int index = 0;
        for(char symbol: string.toCharArray()) {
            index += alphabet.indexOf(symbol);
        }
        return index;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Coordinate that = (Coordinate) other;
        return column == that.column && row == that.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    private final int column;
    private final int row;

    public static final Pattern letterNumberCoordinate = Pattern.compile("([A-Z]+)([0-9]+)");
    public static final Pattern numberLetterCoordinate = Pattern.compile("([0-9]+)([A-Z]+)");
    public static Coordinate ofString(String coordinate) {
        coordinate = coordinate.toUpperCase();
        Matcher numberLetterMatcher = numberLetterCoordinate.matcher(coordinate);
        if(numberLetterMatcher.find())
            return new Coordinate(numberLetterMatcher.group(2), Integer.parseInt(numberLetterMatcher.group(1)));

        Matcher letterNumberMatcher = letterNumberCoordinate.matcher(coordinate);
        if(letterNumberMatcher.find())
            return new Coordinate(letterNumberMatcher.group(1), Integer.parseInt(letterNumberMatcher.group(2)));

        throw new IllegalCoordinateException(coordinate);
    }


    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String toStringIndex(int index) {
        StringBuilder stringBuilder = new StringBuilder();
        for(index += 1; index > 0; index /= alphabet.length()) {
            stringBuilder.append(alphabet.charAt((index - 1) % alphabet.length()));
        }
        return stringBuilder.toString();
    }

}
