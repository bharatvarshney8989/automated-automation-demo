package javapoet;

import java.io.IOException;

public class CodeGenerator {

	public static void main(String[] args) throws IOException {
		CodeGenerator obj = new CodeGenerator();
		obj.generate();
	}

	public static void generate() throws IOException{
		System.out.println("Inside CodeGenerator - Starting code generation");
		String outputDirectory = System.getProperty("user.home").replace("\\", "/")+"/Desktop/src";
		CreateApiClass.generateApiClass(outputDirectory);
		CreateTestScript.generateTestCase(outputDirectory);
		System.out.println("Completed code generation.");
	}
}