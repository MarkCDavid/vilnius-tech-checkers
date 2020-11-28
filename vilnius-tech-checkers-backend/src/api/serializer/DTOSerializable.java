package api.serializer;

public interface DTOSerializable<B, D> {

    D serialize(B value);

}
