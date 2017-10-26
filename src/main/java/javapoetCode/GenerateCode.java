package javapoetCode;

import java.io.IOException;

public class GenerateCode {
	public static void main(String[] args) throws IOException {
		GenerateCode obj = new GenerateCode();
		obj.generateCode();
	}
	public void generateCode() throws IOException{
		CreateApiClass.generateApiClass("./output");
		CreateTestScript.generateTestCase("./output");
	}

}
