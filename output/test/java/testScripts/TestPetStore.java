package testScripts;

import api.user.login.GetUserLoginApi;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPetStore {
  @Test
  public void getLoginTestApproach2() {
    GetUserLoginApi getUserLoginApi = new GetUserLoginApi("http://petstore.swagger.io/v2");
    getUserLoginApi.setQueryParameter("username","test");
    getUserLoginApi.setQueryParameter("password","test");
    getUserLoginApi.setExpectedStatusCode(200);
    getUserLoginApi.perform();
    Assert.assertEquals(getUserLoginApi.getApiResponseAsString().contains("logged in user session"),true,"User should be logged in");
  }
}
