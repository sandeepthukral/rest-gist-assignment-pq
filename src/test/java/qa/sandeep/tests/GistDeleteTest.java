package qa.sandeep.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GistDeleteTest extends BaseTest{

    @BeforeMethod(description = "Delete all gists for the test user before test")
    @Description("Delete all gists for the test user before test")
    public void beforeTest() {
        this.gistClient.deleteAllGists();
    }

    @Test
    @Feature("Delete a Gist")
    @Description("Delete a Gist")
    public void testDeleteGist(){
        String gist = this.utils.getRandomGistWithMultipleFiles(true).toString();
        String id = this.gistClient.createGist(gist);
        // delete Gist
        this.gistClient.deleteGist(id);

        // check that there are no gists
        Response response = this.gistClient.getGistsForAuthenticateTestUser();
        response
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(0));
    }
}
