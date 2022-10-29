package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONRunner {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException 
	{
	Map<String, String> classMeth = new DataUtil().loadClassMethods();
	String configpath = System.getProperty("user.dir")+"//src//test//resources//jsons//testconfig.json";
	JSONParser parser = new JSONParser();
	JSONObject json = (JSONObject) parser.parse(new FileReader(new File(configpath)));
	String parallelsuites = (String)json.get("parallelsuites");
	TestNGRunner testng = new TestNGRunner(Integer.parseInt(parallelsuites));
	JSONArray testsuites = (JSONArray)json.get("testsuites");
	for(int i=0;i<testsuites.size();i++)
	{
		JSONObject testsuite = (JSONObject)testsuites.get(i);
		String runmode = (String)testsuite.get("runmode");
		if(runmode.equals("Y"))
		{
			String name = (String)testsuite.get("name");
			String testdatajsonfile = (String)testsuite.get("testdatajsonfile");
			String suitesfilename = (String)testsuite.get("suitesfilename");
			String paralleltests = (String)testsuite.get("paralleltests");
			boolean pTest=false;
			if(paralleltests.equals("Y"))
				pTest = true;
			testng.createSuite(name, pTest);
			
			String suitePath = System.getProperty("user.dir")+"//src//test//resources//jsons//"+suitesfilename;
			String dataPath = System.getProperty("user.dir")+"//src//test//resources//jsons//"+testdatajsonfile;
			JSONParser suiteparser = new JSONParser();
			JSONObject suiteObject = (JSONObject)suiteparser.parse(new FileReader(new File(suitePath)));
			JSONArray suiteArray = (JSONArray)suiteObject.get("testcases");
			for(int sid=0;sid<suiteArray.size();sid++)
			{
				JSONObject testcases = (JSONObject)suiteArray.get(sid);
				String tname = (String)testcases.get("name");
				JSONArray parameters = (JSONArray)testcases.get("parameters");
				JSONArray executions = (JSONArray)testcases.get("executions");
				for(int eid=0;eid<executions.size();eid++)
				{
					JSONObject execution = (JSONObject)executions.get(eid);
					String executionname = (String)execution.get("executionname");
					String dataflag = (String)execution.get("dataflag");
					int iteration = new DataUtil().getTestDataIterations(dataPath, dataflag);
					for(int j=0;j<iteration;j++)
					{
					JSONArray parametervalues = (JSONArray)execution.get("parametervalues");
					JSONArray methods = (JSONArray)execution.get("methods");
					testng.addTest(tname+"-"+executionname);
					for(int pid=0;pid<parameters.size();pid++)
					{
						testng.addTestParameter((String)parameters.get(pid), (String)parametervalues.get(pid));
					}
					testng.addTestParameter("datafilepath", testdatajsonfile);
					testng.addTestParameter("dataflag", dataflag);
					testng.addTestParameter("iteration", String.valueOf(j));
					
					List<String>includedMethods = new ArrayList<String>();
					for(int mID=0;mID<methods.size();mID++)
					{
						String method = (String)methods.get(mID);
						String methodClass = classMeth.get(method);
						if(mID==methods.size()-1||!((String)classMeth.get((String)methods.get(mID+1))).equals(methodClass))
							{
							includedMethods.add(method);
							testng.addTestClass(methodClass, includedMethods);
							includedMethods=new ArrayList<String>();
							}
						else
						{
							includedMethods.add(method);
						}
					}
				}
				testng.run();
			}
			
		}
	}
	}
	}
}
