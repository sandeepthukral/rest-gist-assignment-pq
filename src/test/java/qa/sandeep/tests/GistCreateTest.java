package qa.sandeep.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
//import io.qameta.allure.restassured.AllureRestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GistCreateTest extends BaseTest {

    @BeforeMethod(description = "Delete all gists for the test user before test")
    @Description("Delete all gists for the test user before test")
    public void beforeTest() {
        this.gistClient.deleteAllGists();
    }

    @Test(description = "Create a random public Gist")
    @Feature("Create a Gist")
    @Description("Create a random public Gist")
    public void testCreateRandomPublicGist() {
        String gist = this.utils.getRandomGist(true);
        String id = this.gistClient.createGist(gist);
        given()
                .spec(this.gistClient.getAuthenticatedSpec())
//                .filter(new AllureRestAssured())
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
        String gist = this.utils.getRandomGistWithMultipleFiles(true);
        String id = this.gistClient.createGist(gist);
        given()
                .spec(this.gistClient.getAuthenticatedSpec())
//                .filter(new AllureRestAssured())
                .when()
                .get("/gists")
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(1))
                .body("owner.login", Matchers.contains("errorlogsblog"));
    }
}
