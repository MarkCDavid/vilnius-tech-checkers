package api.serializer.concrete;

import api.serializer.DTODeserializable;
import api.serializer.DTOSerializable;
import backend.Coordinate;

public class CoordinateSerializer implements DTOSerializable<Coordinate, api.dto.Coordinate>, DTODeserializable<Coordinate, api.dto.Coordinate> {
    @Override
    public Coordinate deserialize(api.dto.Coordinate value) {
        return new Coordinate(value.getX(), value.getY());
    }

    @Override
    public api.dto.Coordinate serialize(Coordinate value) {
        return new api.dto.Coordinate(value.getRow(), value.getColumn());
    }
}
