package api.user.login;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.lang.String;
import java.util.HashMap;

public class GetUserLoginApi {
  String baseURI;

  public String basePath = "/user/login";

  int expectedStatusCode;

  RequestSpecBuilder requestSpecBuilder;

  RequestSpecification requestSpecification;

  ResponseSpecBuilder responseSpecBuilder;

  ResponseSpecification responseSpecification;

  Response apiResponse;

  HashMap<String, String> queryParameters = new HashMap<String, String>();

  public GetUserLoginApi(String baseURI) {
    this.baseURI=baseURI;
    requestSpecBuilder = new RequestSpecBuilder();
    responseSpecBuilder = new ResponseSpecBuilder();
  }

  public void setQueryParameter(String key, String value) {
    queryParameters.put(key, value);
  }

  public void setExpectedStatusCode(int expectedStatusCode) {
    this.expectedStatusCode = expectedStatusCode;
  }

  private Response getApiResponse() {
    return apiResponse;
  }

  public String getApiResponseAsString() {
    return apiResponse.asString();
  }

  private void createRequest() {
    requestSpecBuilder.setBaseUri(baseURI);
    requestSpecBuilder.setBasePath(basePath);
    requestSpecBuilder.addQueryParams(queryParameters);
    requestSpecification = requestSpecBuilder.build();
  }

  private void executeRequest() {
    apiResponse = RestAssured.given().spec(requestSpecification).get();
  }

  private void validateResponse() {
    responseSpecBuilder.expectStatusCode(expectedStatusCode);
    responseSpecification = responseSpecBuilder.build();
    getApiResponse().then().spec(responseSpecification);
  }

  public void perform() {
    createRequest();
    executeRequest();
    validateResponse();
  }
}
