package academy.devdojo.Mapper;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Request.AnimePostRequest;
import academy.devdojo.Response.AnimeGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnimeMapper {

    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1000))")
    @Mapping(target = "name", source = "name")
    Anime toAnime(AnimePostRequest animePostRequest);

    AnimeGetResponse toAnimeGetResponse(Anime anime);
    List<AnimeGetResponse> toListAnimeGetResponse(List<Anime> anime);

}
