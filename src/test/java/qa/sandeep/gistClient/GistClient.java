package qa.sandeep.gistClient;

//import io.qameta.allure.restassured.AllureRestAssured;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class GistClient {

    // The token for the authenticated user for testing
    private String token;

    public GistClient() {
        this.token = getToken();
    }

    private String getToken() {
        Properties prop = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/test/resources/credentials.properties");
            prop.load(fis);
            return prop.getProperty("token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public RequestSpecification getUnauthenticatedSpec(){
        String BASE_URL = "https://api.github.com";
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .addFilter(new ResponseLoggingFilter()) // for debugging
                .addFilter(new RequestLoggingFilter()) // for debugging
                .build();
    }

    public RequestSpecification getAuthenticatedSpec(){
        RequestSpecification spec = this.getUnauthenticatedSpec();
        return spec.header("Authorization", "Bearer " + this.token);
    }

    /**
     * Create a Gist
     * @param gistJson String representation of a gist JSON
     */
    @Step("Create a gist")
    public String createGist(String gistJson) {
        return given()
                .spec(this.getAuthenticatedSpec())
//                .filter(new AllureRestAssured())
                .body(gistJson)
                .post("/gists")
                .then()
                .statusCode(201)
                .extract().path("id");
    }

    @Step("Update Gist with id {id}")
    public void updateGist(String id, String gistJson) {
        given()
                .spec(this.getAuthenticatedSpec())
//                .filter(new AllureRestAssured())
                .body(gistJson)
                .patch("/gists/" + id)
                .then()
                .statusCode(200);
    }

    /**
     * Deletes a gist given its ID
     * Expects the authenticated user to be the owner of the gist
     * @param id Gist ID
     */
    @Step("Delete gist with id {id}")
    public void deleteGist(String id) {
        given()
            .spec(this.getAuthenticatedSpec())
                .delete("/gists/" + id)
                .then()
                .statusCode(204);
    }

    /**
     * Deletes all gists of the authenticated user
     * Use with caution. There is no rollback
     */
    @Step("Delete all Gists of the authenticated user")
    public void deleteAllGists() {
        // get all gist IDs
        List<String> gists =
                given()
                    .spec(this.getUnauthenticatedSpec())
//                    .filter(new AllureRestAssured())
                    .header("Authorization", "Bearer " + this.token)
                    .get("gists")
                    .then()
                    .extract().jsonPath().getList("id");
        // delete all gists
        for (String id: gists) {
            this.deleteGist(id);
        }
    }

    @Step("Get all Gists of the authenticated user")
    public Response getGistsForAuthenticateTestUser() {
        return given()
                .spec(this.getUnauthenticatedSpec())
//                    .filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + this.token)
                .get("gists");
    }

    @Step("Get Commits for the gist with id {id}")
    public Response getGistCommits(String id) {
        return given()
                .spec(this.getAuthenticatedSpec())
//                .filter(new AllureRestAssured())
                .when()
                .get("/gists/"+id+"/commits");
    }

    @Step("Get public gists of the user {user} as an unauthenticated user")
    public Response getPublicGistsOfUserAsUnauthenticatedUser(String user) {
        return given()
                .spec(this.getUnauthenticatedSpec())
//                .filter(new AllureRestAssured())
                .when()
                .get("/users/" + user + "/gists");
    }
}
