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


    Anime toAnime(AnimePostRequest animePostRequest);

    Anime toAnime(AnimePutRequest anime);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    List<AnimeGetResponse> toListAnimeGetResponse(List<Anime> anime);

}
