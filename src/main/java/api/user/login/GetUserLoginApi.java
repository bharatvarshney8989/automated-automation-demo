package api.user.login;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * Created by kohlih on 25-10-2017.
 */
public class GetUserLoginApi {

    String baseURI;
    String basePath="/user/login";
    int expectedStatusCode;

    RequestSpecBuilder requestSpecBuilder;
    RequestSpecification requestSpecification;
    ResponseSpecBuilder responseSpecBuilder;
    ResponseSpecification responseSpecification;
    Response apiResponse;

    HashMap<String, String> queryParameters = new HashMap<String, String>();

    public GetUserLoginApi(String baseURI) {
        this.baseURI=baseURI;
        requestSpecBuilder=new RequestSpecBuilder();
        responseSpecBuilder=new ResponseSpecBuilder();
    }

    public void setQueryParameter(String key, String value){
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

    private void createRequest(){
        requestSpecBuilder.setBaseUri(baseURI);
        requestSpecBuilder.setBasePath(basePath);
        requestSpecBuilder.addQueryParams(queryParameters);
        requestSpecification = requestSpecBuilder.build();
    }

    private void executeRequest(){
        apiResponse = given().spec(requestSpecification).get();
    }

    private void validateResponse(){
        responseSpecBuilder.expectStatusCode(expectedStatusCode);
        responseSpecification = responseSpecBuilder.build();
        getApiResponse().then().spec(responseSpecification);
    }

    public void perform(){
        createRequest();
        executeRequest();
        validateResponse();
    }
}