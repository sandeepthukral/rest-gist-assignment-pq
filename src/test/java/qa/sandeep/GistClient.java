package qa.sandeep;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://api.github.com")
                .addFilter(new ResponseLoggingFilter()) // for debugging
                .addFilter(new RequestLoggingFilter()) // for debugging
                .build();
    }

    public RequestSpecification getAuthenticatedSpec(){
        RequestSpecification spec = this.getUnauthenticatedSpec();
        return spec.header("Authorization", "Bearer " + this.token);
    }

    /**
     * Deletes a gist given its ID
     * Expects the authenticated user to be the owner of the gist
     * @param id Gist ID
     */
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
    public void deleteAllGists() {
        // get all gist IDs
        List<String> gists =
                given()
                    .spec(this.getUnauthenticatedSpec())
                    .header("Authorization", "Bearer " + this.token)
                    .get("gists")
                    .then()
                    .extract().jsonPath().getList("id");
        // delete all gists
        for (String id: gists) {
            this.deleteGist(id);
        }
    }
}
