package javapoetCode;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GenerateCode {

	public static void main(String[] args) throws IOException {

		Builder classBuilder = TypeSpec.classBuilder("GetUserLoginApi").addModifiers(Modifier.PUBLIC)
				.addField(String.class, "baseURI")
				.addField(FieldSpec.builder(String.class, "basePath").addModifiers(Modifier.PUBLIC)
						.initializer("$S", "/user/login").build())
				.addField(int.class, "expectedStatusCode")
				.addField(FieldSpec.builder(ParameterizedTypeName.get(RequestSpecBuilder.class), "requestSpecBuilder")
						.build())
				.addField(FieldSpec
						.builder(ParameterizedTypeName.get(RequestSpecification.class), "requestSpecification").build())
				.addField(FieldSpec.builder(ParameterizedTypeName.get(ResponseSpecBuilder.class), "responseSpecBuilder")
						.build())
				.addField(FieldSpec
						.builder(ParameterizedTypeName.get(ResponseSpecification.class), "responseSpecification")
						.build())
				.addField(FieldSpec.builder(ParameterizedTypeName.get(Response.class), "apiResponse").build())
				.addField(FieldSpec
						.builder(ParameterizedTypeName.get(HashMap.class, String.class, String.class),
								"queryParameters")
						.initializer("new $T()", ParameterizedTypeName.get(HashMap.class, String.class, String.class))
						.build())
				.addMethod(createConsturctor()).addMethod(generateSetQueryParameter())
				.addMethod(generateSetExpectedStatusCode()).addMethod(generateGetApiResponse())
				.addMethod(generateGetApiResponseAsString()).addMethod(generateCreateRequest())
				.addMethod(generateExecuteRequest()).addMethod(generateValidateResponse()).addMethod(generatePerform());
		TypeSpec build = classBuilder.build();
		JavaFile javaFile = JavaFile.builder("api.user.login", build).build();
		javaFile.writeTo(new File("./output"));
		JavaFile file = JavaFile.builder("api.user.login", build).build();
		file.writeTo(System.out);

	}

	private static MethodSpec createConsturctor() {
		MethodSpec methodSpec = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
				.addParameter(String.class, "baseURI").addStatement("this.baseURI=baseURI")
				.addStatement("requestSpecBuilder = new $T()", ParameterizedTypeName.get(RequestSpecBuilder.class))
				.addStatement("responseSpecBuilder = new $T()", ParameterizedTypeName.get(ResponseSpecBuilder.class))
				.build();
		return methodSpec;
	}

	private static MethodSpec generateSetQueryParameter() {
		return MethodSpec.methodBuilder("setQueryParameter").addModifiers(Modifier.PUBLIC)
				.addParameter(String.class, "key").addParameter(String.class, "value").returns(void.class)
				.addStatement("queryParameters.put(key, value)").build();
	}

	private static MethodSpec generateSetExpectedStatusCode() {
		return MethodSpec.methodBuilder("setExpectedStatusCode").returns(void.class).addModifiers(Modifier.PUBLIC)
				.addParameter(int.class, "expectedStatusCode")
				.addStatement("this.expectedStatusCode = expectedStatusCode").build();
	}

	private static MethodSpec generateGetApiResponse() {
		return MethodSpec.methodBuilder("getApiResponse").addModifiers(Modifier.PRIVATE)
				.returns(ParameterizedTypeName.get(Response.class)).addStatement("return apiResponse").build();
	}

	private static MethodSpec generateGetApiResponseAsString() {
		return MethodSpec.methodBuilder("getApiResponseAsString").addModifiers(Modifier.PUBLIC).returns(String.class)
				.addStatement("return apiResponse.asString()").build();
	}

	private static MethodSpec generateCreateRequest() {
		return MethodSpec.methodBuilder("createRequest").addModifiers(Modifier.PRIVATE).returns(void.class)
				.addStatement("requestSpecBuilder.setBaseUri(baseURI)")
				.addStatement("requestSpecBuilder.setBasePath(basePath)")
				.addStatement("requestSpecBuilder.addQueryParams(queryParameters)")
				.addStatement("requestSpecification = requestSpecBuilder.build()").build();
	}

	private static MethodSpec generateExecuteRequest() {
		return MethodSpec.methodBuilder("executeRequest").addModifiers(Modifier.PRIVATE).returns(void.class)
				.addStatement("apiResponse = $T().spec(requestSpecification).get()",
						ClassName.get("io.restassured.RestAssured", "given"))
				.build();
	}

	private static MethodSpec generateValidateResponse() {
		return MethodSpec.methodBuilder("validateResponse").addModifiers(Modifier.PRIVATE).returns(void.class)
				.addStatement("responseSpecBuilder.expectStatusCode(expectedStatusCode)")
				.addStatement("responseSpecification = responseSpecBuilder.build()")
				.addStatement("getApiResponse().then().spec(responseSpecification)").build();
	}

	private static MethodSpec generatePerform() {
		return MethodSpec.methodBuilder("perform").addModifiers(Modifier.PUBLIC).returns(void.class)
				.addStatement("createRequest()").addStatement("executeRequest()").addStatement("validateResponse()")
				.build();
	}

}
