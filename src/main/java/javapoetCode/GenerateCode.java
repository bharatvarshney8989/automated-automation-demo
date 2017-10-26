package javapoetCode;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateCode {
	private final static Logger LOGGER = Logger.getLogger(GenerateCode.class.getName());

	public void generateCode() throws IOException{
		LOGGER.log(Level.INFO, "Inside GenerateCode - Starting code generation");
		String outputDirectory = System.getProperty("user.home").replace("\\", "/")+"/Desktop";
		CreateApiClass.generateApiClass(outputDirectory);
		CreateTestScript.generateTestCase(outputDirectory);
		LOGGER.log(Level.INFO, "Completed code generation.");
	}

}
