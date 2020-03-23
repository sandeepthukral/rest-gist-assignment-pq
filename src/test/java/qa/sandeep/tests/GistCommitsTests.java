package qa.sandeep.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GistCommitsTests extends BaseTest {

    @BeforeMethod(description = "Delete all gists for the test user before test")
    @Description("Delete all gists for the test user before test")
    public void beforeTest() {
        this.gistClient.deleteAllGists();
    }


    @Test
    @Feature("Commits in Gists")
    @Description("A new gist should show one commit")
    public void testNewGistShouldShowOneCommit(){
        String gist = this.utils.getRandomGistWithMultipleFiles(true).toString();
        String id = this.gistClient.createGist(gist);
        Response response = this.gistClient.getGistCommits(id);
        response
                .then()
                .statusCode(200)
                 // verify that there is one commit
                .body("", Matchers.hasSize(1));
    }


    @Test
    @Feature("Commits in Gists")
    @Description("Edited Gist should show two commits")
    public void testEditedGistShouldShowTwoCommits(){
        String gist = this.utils.getRandomGistWithMultipleFiles(true).toString();
        String id = this.gistClient.createGist(gist);
        String updatedGist = this.utils.getRandomGist(true).toString();
        this.gistClient.updateGist(id, updatedGist);
        Response response = this.gistClient.getGistCommits(id);
        response
                .then()
                .statusCode(200)
                // verify that there are two commits
                .body("", Matchers.hasSize(2));
    }
}
