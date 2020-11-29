package api.serializer.concrete;

import api.dto.Side;
import api.serializer.DTODeserializable;
import api.serializer.DTOSerializable;
import backend.Coordinate;
import backend.Piece;

public class PieceSerializer implements DTOSerializable<Piece, api.dto.Piece>{

    @Override
    public api.dto.Piece serialize(Piece value) {
        if(value == null)
            return null;
        return new api.dto.Piece(new Side(value.getSide().getIndex()), value.getPromotionLevel());
    }
}
