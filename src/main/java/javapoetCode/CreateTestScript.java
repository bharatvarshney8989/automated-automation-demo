package javapoetCode;

import java.io.File;
import java.io.IOException;
import javax.lang.model.element.Modifier;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

public class CreateTestScript {

	public static void generateTestCase(String outputDirectoryPath) throws IOException {
		Builder classBuilder = TypeSpec.classBuilder("TestPetStore").addModifiers(Modifier.PUBLIC)
				.addMethod(generateGetLoginTestApproach2());
		TypeSpec build = classBuilder.build();
		JavaFile file = JavaFile.builder("testScripts", build).build();
		file.writeTo(new File(outputDirectoryPath + "/test/java"));
	}

	private static MethodSpec generateGetLoginTestApproach2() {
		return MethodSpec.methodBuilder("getLoginTestApproach2").addModifiers(Modifier.PUBLIC).returns(void.class)
				.addAnnotation(AnnotationSpec.builder(ClassName.get("org.testng.annotations", "Test")).build())
				.addStatement("$T getUserLoginApi = new $T(\"http://petstore.swagger.io/v2\")",
						ClassName.get("api.user.login", "GetUserLoginApi"),
						ClassName.get("api.user.login", "GetUserLoginApi"))
				.addStatement("getUserLoginApi.setQueryParameter(\"username\",\"test\")")
				.addStatement("getUserLoginApi.setQueryParameter(\"password\",\"test\")")
				.addStatement("getUserLoginApi.setExpectedStatusCode(200)").addStatement("getUserLoginApi.perform()")
				.addStatement(
						"$T.assertEquals(getUserLoginApi.getApiResponseAsString().contains(\"logged in user session\"),true,\"User should be logged in\")",
						ClassName.get("org.testng", "Assert"))
				.build();
	}

}
