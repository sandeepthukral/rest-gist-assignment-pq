package qa.sandeep.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GistPrivateTest extends BaseTest {

    @BeforeMethod(description = "Delete all gists for the test user before test")
    @Description("Delete all gists for the test user before test")
    public void beforeTest() {
        this.gistClient.deleteAllGists();
    }

    @Test
    @Feature("Private gists")
    @Description("A private Gist should not show in list of gists of the user")
    public void testPrivateGistNotVisibleToOthers(){
        String gist = this.utils.getRandomGist(false).toString();
        this.gistClient.createGist(gist);

        // check for the list of all gists of this user as an unauthenticated user
        Response response = this.gistClient.getPublicGistsOfUserAsUnauthenticatedUser(
                this.testData.getProperty("username"));
        response
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(0));
    }

    @Test
    @Feature("Private gists")
    @Description("A private Gist should be visible to the owner of the gist")
    public void testPrivateGistVisibleToOwner(){
        String gist = this.utils.getRandomGist(false).toString();
        String id = this.gistClient.createGist(gist);

        // check for the list of all gists of this user authenticated as the user itself
        Response response = this.gistClient.getGistsForAuthenticateTestUser();
        response
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(1))
                .body("id", Matchers.contains(id));
    }
}
