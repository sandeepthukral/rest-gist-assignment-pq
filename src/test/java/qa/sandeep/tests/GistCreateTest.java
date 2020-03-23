package qa.sandeep.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import io.qameta.allure.restassured.AllureRestAssured;

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
        String gist = this.utils.getRandomGist(true).toString();
        String id = this.gistClient.createGist(gist);
        Response response = this.gistClient.getGistsForAuthenticateTestUser();
        response
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(1))
                .body("id", Matchers.contains(id));
    }

    @Test(description = "Create a random public Gist with multiple files")
    @Feature("Create a Gist")
    @Description("Create a random public Gist with multiple files")
    public void testCreateRandomPublicGistWithMultipleFiles() {
        String gist = this.utils.getRandomGistWithMultipleFiles(true).toString();
        String id = this.gistClient.createGist(gist);
        Response response = this.gistClient.getGistsForAuthenticateTestUser();
        response
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(1))
                .body("id", Matchers.contains(id));
    }
}
