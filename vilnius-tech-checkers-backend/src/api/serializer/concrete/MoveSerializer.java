package api.serializer.concrete;

import api.serializer.DTOSerializable;
import backend.moves.base.Move;

public class MoveSerializer implements DTOSerializable<Move, api.dto.Move> {

    private final CoordinateSerializer coordinateSerializer;

    public MoveSerializer() {
        this.coordinateSerializer = new CoordinateSerializer();
    }

    @Override
    public api.dto.Move serialize(Move value) {
        return new api.dto.Move(
                coordinateSerializer.serialize(value.getFrom()),
                coordinateSerializer.serialize(value.getTo())
        );
    }
}
