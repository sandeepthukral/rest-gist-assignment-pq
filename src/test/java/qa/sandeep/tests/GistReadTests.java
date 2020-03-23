package qa.sandeep.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/* import io.qameta.allure.restassured.AllureRestAssured; */

public class GistReadTests extends BaseTest{

    @BeforeMethod()
    @Description("Delete all gists for the test user before test")
    public void beforeTest() {
        this.gistClient.deleteAllGists();
    }


    @Test(description = "Get an empty list of Gists")
    @Feature("Read Gists")
    @Description("Get an empty list of Gists")
    public void testGetEmptyListOfGists() {
        Response response = this.gistClient.getGistsForAuthenticateTestUser();
        response
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(0));
    }
}
