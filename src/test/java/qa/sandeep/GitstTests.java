package qa.sandeep;

import io.restassured.specification.RequestSpecification;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import qa.sandeep.GistClient;

import static io.restassured.RestAssured.given;

public class GitstTests extends BaseTest{


    @BeforeTest
    public void beforeTest() {
        this.gistClient.deleteAllGists();
    }

    @Test
    public void testGetEmptyListOfGists(){
        RequestSpecification spec = this.gistClient.getAuthenticatedSpec();
        given()
            .spec(spec)
            .when()
            .get("/gists")
            .then()
            .statusCode(200)
            .body("", Matchers.hasSize(0));
    }
}
