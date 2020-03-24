package qa.sandeep.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
/* import io.qameta.allure.restassured.AllureRestAssured; */
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;

public class GistEditTest extends BaseTest {

    @BeforeMethod(description = "Delete all gists for the test user before test")
    @Description("Delete all gists for the test user before test")
    public void beforeTest() {
        this.gistApiClient.deleteAllGists();
    }

    @Test
    @Feature("Edit a Gist")
    @Description("Edit a Gist adding a new file")
    public void editGist(){
        String gist = this.utils.getRandomGistWithMultipleFiles(true).toString();
        String id = this.gistApiClient.createGist(gist);
        String updatedGist = this.utils.getRandomGist(true).toString();
        this.gistApiClient.updateGist(id, updatedGist);
        Response response = this.gistApiClient.getGistsForAuthenticateTestUser();
        response
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(1))
                // verify that there are three files now
                .body("files[0].keySet()size()", is(3));
    }
}
