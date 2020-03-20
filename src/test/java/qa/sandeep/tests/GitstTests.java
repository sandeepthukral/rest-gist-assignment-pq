package qa.sandeep.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.sandeep.utils.RandomUtils;

import static io.restassured.RestAssured.given;

public class GitstTests extends BaseTest{

    @BeforeMethod(description = "Delete all gists for the test user before test")
    @Description("Delete all gists for the test user before test")
    public void beforeTest() {
        this.gistClient.deleteAllGists();
    }

    @Test(description = "Get an empty list of Gists")
    @Feature("Fetch Gists")
    @Description("Get an empty list of Gists")
    public void testGetEmptyListOfGists() {
        given()
            .spec(this.gistClient.getAuthenticatedSpec())
            .filter(new AllureRestAssured())
            .when()
            .get("/gists")
            .then()
            .statusCode(200)
            .body("", Matchers.hasSize(0));
    }

    @Test(description = "Create a random public Gist")
    @Feature("Create a Gist")
    @Description("Create a random public Gist")
    public void testCreateRandomPublicGist() {
        String gist = RandomUtils.getRandomGist(true);
        this.gistClient.createGist(gist);
        given()
                .spec(this.gistClient.getAuthenticatedSpec())
                .filter(new AllureRestAssured())
                .when()
                .get("/gists")
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(1))
                .body("owner.login", Matchers.contains("errorlogsblog"));
    }

    @Test(description = "Create a random public Gist with multiple files")
    @Feature("Create a Gist")
    @Description("Create a random public Gist with multiple files")
    public void testCreateRandomPublicGistWithMultipleFiles() {
        String gist = RandomUtils.getRandomGistWithMultipleFiles(true);
        this.gistClient.createGist(gist);
        given()
                .spec(this.gistClient.getAuthenticatedSpec())
                .filter(new AllureRestAssured())
                .when()
                .get("/gists")
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(1))
                .body("owner.login", Matchers.contains("errorlogsblog"));
    }
}
