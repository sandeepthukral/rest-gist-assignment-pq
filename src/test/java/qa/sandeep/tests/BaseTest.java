package qa.sandeep.tests;

import qa.sandeep.gistClient.GistApiClient;
import qa.sandeep.utils.RandomUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    GistApiClient gistApiClient = new GistApiClient();
    RandomUtils utils = new RandomUtils();
    Properties testData = new Properties();

    public BaseTest() {
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/test/resources/testData.properties");
            testData.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
