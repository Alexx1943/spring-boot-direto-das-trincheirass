package academy.devdojo.controller;


import academy.devdojo.commons.FileUtils;
import academy.devdojo.config.IntegrationTestConfig;
import academy.devdojo.dto.post.ProfilePostRequest;
import academy.devdojo.dto.get.ProfileGetResponse;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileControllerIT extends IntegrationTestConfig {

    private static final String URL = "/v1/profiles";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FileUtils fileUtils;

    @Test
    @DisplayName("GET/v1/profiles returns  a list with all profiles")
    @Sql(value = "/sql/init_two_profiles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/delete_profile.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(1)
    void findAll_ReturnsListWithAllProfile_WhenSucessful() throws Exception {

        var typeReference = new ParameterizedTypeReference<List<ProfileGetResponse>>() {
        };

        var responseEntity = testRestTemplate.exchange(URL, HttpMethod.GET, null, typeReference);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotEmpty().doesNotContainNull();

        responseEntity.getBody()
                .forEach(profileGetResponse -> Assertions.assertThat(profileGetResponse).hasNoNullFieldsOrProperties());
    }

    @Test
    @Order(2)
    @DisplayName("GET/v1/profiles returns a empity lis when nothing is not found")
    void findAllReturnEmpityList_WhenNothingIsNotFound() {

        var typeReference = new ParameterizedTypeReference<List<ProfileGetResponse>>() {
        };

        var responseEntity = testRestTemplate.exchange(URL, HttpMethod.GET, null, typeReference);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEmpty();
    }

    @Test
    @Order(3)
    @DisplayName("POST/v1/profiles creates an profile")
    void saveCreateAnProfile() {

        var request = fileUtils.readResourceFile("profile/post/post-request-profile-save-201.json");

        var profileToSave = buildHttpEntity(request);

        var responseEntity = testRestTemplate.exchange(URL, HttpMethod.POST, profileToSave, ProfilePostRequest.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity.getBody()).isNotNull().hasNoNullFieldsOrProperties();
    }


    @ParameterizedTest
    @Order(4)
    @DisplayName("POST/v1/profiles returns bad request when fields are invalid")
    @MethodSource("postProfileBadResquest")
    void saveReturnBadRequest_WhenFieldsAreInvalid(String requestFile, String responseFile) {

        var request = fileUtils.readResourceFile("profile/%s".formatted(requestFile));
        var expectedResponse = fileUtils.readResourceFile("profile/%s".formatted(responseFile));

        var profileEntity = buildHttpEntity(request);

        var responseEntity = testRestTemplate.exchange(URL, HttpMethod.POST, profileEntity, String.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        JsonAssertions.assertThatJson(responseEntity.getBody())
                .whenIgnoringPaths("timestamp")
                .isEqualTo(expectedResponse);
    }


    private static Stream<Arguments> postProfileBadResquest() {

        return Stream.of(
                Arguments.of("post/post-request-producer-blank-fields-400.json", "post/post-response-producer-blank-fields-400.json"),
                Arguments.of("post/post-request-producer-empity-fields-400.json", "post/post-response-producer-empity-fields-400.json")
        );

    }

    private static HttpEntity<String> buildHttpEntity(String request) {

        var httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(request, httpHeaders);
    }
}
