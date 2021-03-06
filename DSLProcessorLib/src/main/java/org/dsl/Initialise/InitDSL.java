package org.dsl.Initialise;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.dsl.ControlLoops.ControlLoopsFactory;
import org.dsl.ControlLoops.ControlLoopType.LoopType;
import org.dsl.annotation.DSL;
import org.dsl.bean.DSLObject;
import org.dsl.exception.DSLExecFailException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class InitDSL
{
	static Class<?> dslClass = null;
	static List<String> dslCommands = new ArrayList<String>();
	public static Map runTimeVars = new HashMap();
	public static Map globalRunTimeVars = new HashMap();
	public static Map methodCommandMapping = new HashMap();
	public static Properties propConfig;
	public static Properties propGlobalConfig;

	public static void initialise(String testCase) throws DSLExecFailException
	{
		try
		{
			propConfig=getConfig("dsl.properties");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		String packageList = propConfig.getProperty("packageName");
		String[] pack= packageList.split("\\|");
		for(int i=0;i<pack.length;i++){
			Reflections reflections = new Reflections(new ConfigurationBuilder().filterInputsBy(new FilterBuilder().includePackage(pack[i]))
					.setUrls(ClasspathHelper.forPackage(pack[i]))
					.setScanners(new SubTypesScanner(),new MethodAnnotationsScanner(),new TypeElementsScanner(),new TypeAnnotationsScanner()));
			Set<Method> annotedMethods = reflections.getMethodsAnnotatedWith(DSL.class);
			Iterator<Method> itM = annotedMethods.iterator();
			while(itM.hasNext())
			{
				Method method = itM.next();
				Annotation dslAnnotation = method.getAnnotation(DSL.class);
				DSL dslCastAnnotn = (DSL)dslAnnotation;
				DSLObject dslObj = new DSLObject();
				dslObj.setCommandName(dslCastAnnotn.commName());
				dslObj.setCommandSyntax(dslCastAnnotn.commSyntax());
				dslObj.setCommandRegex(dslCastAnnotn.commRegex());
				dslObj.setM(method);
				String commandName = dslCastAnnotn.commName();
				String commandSyntax = dslCastAnnotn.commSyntax();
				String[] commandRegex = dslCastAnnotn.commRegex();
				commandSyntax = MessageFormat.format(commandSyntax, commandRegex);
				methodCommandMapping.put(commandSyntax, dslObj);
			}
		}
		readFile(testCase);	
		runTimeVars.clear();
		for (int j=0; j < dslCommands.size(); j++) {			
			Iterator it = methodCommandMapping.entrySet().iterator();

			if (((String)dslCommands.get(j)).matches("If (.*)")){
				j=ControlLoopsFactory.processControlLoop(LoopType.IF,dslCommands,j);				
			}
			
			if (((String)dslCommands.get(j)).matches("While (.*)")){
				j=ControlLoopsFactory.processControlLoop(LoopType.WHILE,dslCommands,j);				
			}

			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();

				if (((String)dslCommands.get(j)).matches((String)pairs.getKey())) {
					DSLObject obj = (DSLObject)pairs.getValue();
					Object result = invokeMethod(obj,(String)dslCommands.get(j));
					if ((result == null) || (j >= dslCommands.size() - 1))
						break;
					if (!((String)dslCommands.get(j + 1)).startsWith("Assign"))
						break;
					String varName = ((String)dslCommands.get(j + 1)).replaceAll("Assign ", "");
					runTimeVars.put(varName, result);
					j++;
					break;
				}
			}
		}		
	}

	private static Properties getConfig(String propFileName) throws IOException {
		Properties prop = new Properties();
		InputStream in = InitDSL.class.getClassLoader().getResourceAsStream(propFileName);
		prop.load(in);
		return prop;
	}

	public static void readFile(String testCase) {	
		dslCommands.clear();
		String testSteps[] = testCase.split("\n");
		String strLine;

		for(int i=0;i<testSteps.length;i++)
		{	
			strLine=testSteps[i].trim();

			if(strLine.startsWith("#")){
				continue;
			}
			if(strLine.equals("Exit")){
				break;
			}
			if (strLine.contains("Assign")) {
				String[] cmd = strLine.split(" Assign ");
				dslCommands.add(cmd[0]);
				dslCommands.add("Assign " + cmd[1]);

			} else {
				dslCommands.add(strLine);
			}
		}
	}

	public static Object invokeMethod(DSLObject dslObj, String command) throws DSLExecFailException {
		Method m = dslObj.getM();
		String oCommand = command;
		String commandName = dslObj.getCommandName();
		String commandSyntax = dslObj.getCommandSyntax();
		String[] commandRegex = dslObj.getCommandRegex();
		commandSyntax = MessageFormat.format(commandSyntax, commandRegex);
		Object[] input = new Object[commandRegex.length];
		Object result = null;
		int cnt = 0;
		int i = 0;
		int j=0;

		String[] orignalCommand = commandSyntax.split(" ");
		String[] userCommand = command.split(" ");		

		while (i < orignalCommand.length) {
			if (orignalCommand[i].equals(userCommand[cnt])) {
				//System.out.println(orignalCommand[i] + " is neglected");
				i++;
				cnt++;
			}  
			else if (orignalCommand[i].matches(".*")) {
				//System.out.println("Regex");
				String inpVal = "";
				if ((i + 1) < orignalCommand.length) {
					i = i + 1;
					String hold = orignalCommand[i];
					while (i < orignalCommand.length && !hold.equals(userCommand[cnt])) {
						inpVal = inpVal + userCommand[cnt]+" ";
						cnt++;						
					}
				} else {
					while (cnt < userCommand.length) {
						inpVal = inpVal + userCommand[cnt]+" ";
						cnt++;
					}
					i++;
				}
				if(j< input.length){
					input[j]=inpVal.trim();
					j++;
				}
			} else {
				System.out.println("No match");
				i++;
				cnt++;
			}
		}

		Class[] paramClass = m.getParameterTypes();
		for (i = 0; i < paramClass.length; i++) {
			String classCastType = null;
			if ("int".equals(paramClass[i].getName())) {
				classCastType = "java.lang.Integer";
				input[i] = Integer.valueOf(Integer.parseInt((String)input[i]));
			} else {
				classCastType = paramClass[i].getName();
				try
				{
					Class classCast = Class.forName(classCastType);
					input[i] = classCast.cast(input[i]);						
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
		try {
			m.setAccessible(true);
			Object calc = m.getDeclaringClass().newInstance();
			result = m.invoke(calc, input);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}		
		catch (InvocationTargetException e) {
			e.printStackTrace();

			List<String> exceptionList = new ArrayList<String>();

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw, true);
			e.printStackTrace(pw);
			String stackTrace = sw.getBuffer().toString();

			String exceptionArray[]= stackTrace.split("\n");
			for (int k=0;k<exceptionArray.length;k++){
				if (exceptionArray[k].contains("Caused by")){
					while(k<exceptionArray.length){
						exceptionList.add(exceptionArray[k]);
						k++;
					}
					break;
				}

			}

			String errorString = "";

			for (String s : exceptionList)
			{
				errorString += s + "\n";
			}			

			if (stackTrace.contains("Caused by")) {
				throw new DSLExecFailException(errorString); 				  			   
			}
		}			
		return result;
	}

	public static void createGlobalHash(){

		try
		{
			propGlobalConfig=getConfig("GlobalConfig.properties");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		globalRunTimeVars.put("{browser}", propGlobalConfig.getProperty("browser"));
		globalRunTimeVars.put("{testUrl}", propGlobalConfig.getProperty("testUrl"));	
	}

	public static Object getVariableValue(String varName){
		Object obj=InitDSL.runTimeVars.get(varName);
		if(obj==null){
			obj=InitDSL.globalRunTimeVars.get(varName);
		}
		return obj;
	}

}
