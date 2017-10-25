import api.user.login.GetUserLoginApi;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

/**
 * Created by kohlih on 25-10-2017.
 */
public class TestPetStore {

    @Test
    public void GetLoginTestApproach1(){
        given().
            param("username","test").
            param("password","test").
        when().
            get("http://petstore.swagger.io/v2/user/login").
        then().
            assertThat().
            statusCode(200);
    }

    @Test
    public void GetLoginTestApproach2(){
        GetUserLoginApi getUserLoginApi = new GetUserLoginApi("http://petstore.swagger.io/v2");
        getUserLoginApi.setQueryParameter("username","test");
        getUserLoginApi.setQueryParameter("password","test");
        getUserLoginApi.setExpectedStatusCode(200);
        getUserLoginApi.perform();

        Assert.assertEquals(getUserLoginApi.getApiResponseAsString().contains("logged in user session"),true,"User should be logged in");
    }
}