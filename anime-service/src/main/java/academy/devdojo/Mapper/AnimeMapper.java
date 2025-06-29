package academy.devdojo.Mapper;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Request.AnimePostRequest;
import academy.devdojo.Request.AnimePutRequest;
import academy.devdojo.Response.AnimeGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimeMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1000))")
    @Mapping(target = "name", source = "name")
    Anime toAnime(AnimePostRequest animePostRequest);

    Anime toAnime(AnimePutRequest anime);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    List<AnimeGetResponse> toListAnimeGetResponse(List<Anime> anime);

}
