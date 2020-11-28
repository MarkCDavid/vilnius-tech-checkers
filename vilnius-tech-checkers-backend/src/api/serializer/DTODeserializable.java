package api.serializer;

public interface DTODeserializable<B, D> {
    B deserialize(D value);
}
