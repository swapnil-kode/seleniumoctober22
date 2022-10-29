package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataUtil 
{
public Map<String,String> loadClassMethods() throws FileNotFoundException, IOException, ParseException
{
	String path = System.getProperty("user.dir")+"//src//test//resources//jsons//classmethods.json";
	//System.out.println(path);
	Map<String,String>methodMap = new HashMap<String,String>();
	JSONParser parser = new JSONParser();
	JSONObject json = (JSONObject)parser.parse(new FileReader(new File(path)));
	JSONArray classDetails = (JSONArray)json.get("classdetails");
	for(int cD=0;cD<classDetails.size();cD++)
	{
		JSONObject classDetail = (JSONObject) classDetails.get(cD);
		String className = (String)classDetail.get("class");
		JSONArray methodsName = (JSONArray)classDetail.get("methods");
		for(int mD=0;mD<methodsName.size();mD++)
		{
			String method = (String)methodsName.get(mD);
			methodMap.put(method, className);
		}
	}
	System.out.println(methodMap);
	return methodMap;
}
public int getTestDataIterations(String path, String dataFlag) throws FileNotFoundException, IOException, ParseException
{
JSONParser parser = new JSONParser();
JSONObject dataobject = (JSONObject)parser.parse(new FileReader(new File(path)));
JSONArray testData = (JSONArray)dataobject.get("testdata");
for(int i=0;i<testData.size();i++)
{
JSONObject objectdata = (JSONObject)testData.get(i);
String flag = (String)objectdata.get("flag");
if(dataFlag.equals(flag))
{
JSONArray dataArray = (JSONArray)objectdata.get("data");
return dataArray.size();
}
}
return -1;
}
}
