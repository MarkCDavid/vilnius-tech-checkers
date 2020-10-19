package api.endpoints;

import api.dto.Piece;
import api.dto.Ruleset;

import java.util.List;

public interface PieceService {
    List<Piece> getRulesets();
}
