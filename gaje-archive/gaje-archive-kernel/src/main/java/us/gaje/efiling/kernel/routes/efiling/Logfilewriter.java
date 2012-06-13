package us.gaje.efiling.kernel.routes.efiling;

import java.io.IOException;

import org.apache.camel.Header;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;

public class Logfilewriter
{
	public String write(@Header("file") String file,@Header("action") String action) throws IOException
	{
		Logger log = Logger.getLogger(file);
		//log.removeAllAppenders();
		PatternLayout layout = new PatternLayout("%d %5p - [%m]%n");
		log.addAppender(new FileAppender(layout,"/tmp/output/" + file));
		log.setLevel(Level.INFO);
		log.info(action);
		return "";
	}
	
}