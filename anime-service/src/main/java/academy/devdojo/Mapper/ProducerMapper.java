package academy.devdojo.Mapper;

import academy.devdojo.Domain.Producer;
import academy.devdojo.Request.ProducerPostResquest;
import academy.devdojo.Response.ProducerGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProducerMapper {

    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1000))")
    Producer toProducer(ProducerPostResquest postResquest);

    ProducerGetResponse toProducerGetResponse(Producer producer);
}
