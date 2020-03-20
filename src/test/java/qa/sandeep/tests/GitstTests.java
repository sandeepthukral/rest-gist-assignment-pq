package qa.sandeep.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
//import io.qameta.allure.restassured.AllureRestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
//            .filter(new AllureRestAssured())
            .when()
            .get("/gists")
            .then()
            .statusCode(200)
            .body("", Matchers.hasSize(0));
    }
}
