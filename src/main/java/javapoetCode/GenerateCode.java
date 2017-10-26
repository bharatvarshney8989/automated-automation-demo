package javapoetCode;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateCode {
	private final static Logger LOGGER = Logger.getLogger(GenerateCode.class.getName());
public static void main(String[] args) throws IOException {
	GenerateCode obj = new GenerateCode();
	obj.generateCode();
}
	public void generateCode() throws IOException{
		LOGGER.log(Level.INFO, "Inside GenerateCode - Starting code generation");
		String outputDirectory = System.getProperty("user.home").replace("\\", "/")+"/Desktop/src";
		CreateApiClass.generateApiClass(outputDirectory);
		CreateTestScript.generateTestCase(outputDirectory);
		LOGGER.log(Level.INFO, "Completed code generation.");
	}

}
