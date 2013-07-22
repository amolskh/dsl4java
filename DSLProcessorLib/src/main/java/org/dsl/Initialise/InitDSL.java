package org.dsl.Initialise;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import org.dsl.annotation.DSL;
import org.dsl.bean.DSLObject;
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
	static List dslCommands = new ArrayList();
	public static Map runTimeVars = new HashMap();
	public static Map methodCommandMapping = new HashMap();
	public static Properties prop;

	public static void initialise()
	{
		try
		{
			getConfig();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		String packageList = prop.getProperty("className");
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
			readFile();		

			for (int j=0; j < dslCommands.size(); j++) {
				Iterator it = methodCommandMapping.entrySet().iterator();
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

	private static void getConfig() throws IOException {
		prop = new Properties();
		InputStream in = InitDSL.class.getClassLoader().getResourceAsStream("dsl.properties");
		prop.load(in);
	}

	public static void readFile() {
		try {
			InputStream finStream = InitDSL.class.getClassLoader().getResourceAsStream("demo.dsl"); 
			DataInputStream dinStream = new DataInputStream(finStream);
			BufferedReader buReader = new BufferedReader(new InputStreamReader(
					dinStream));
			String strLine;
			while ((strLine = buReader.readLine()) != null)
			{
				//String strLine;dslClass
				if (strLine.contains("Assign")) {
					String[] cmd = strLine.split(" Assign ");
					dslCommands.add(cmd[0]);
					dslCommands.add("Assign " + cmd[1]);

				} else {
					dslCommands.add(strLine);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}    
	}

	public static Object invokeMethod(DSLObject dslObj, String command) {
		Method m = dslObj.getM();
		String oCommand = command;
		String commandName = dslObj.getCommandName();
		String commandSyntax = dslObj.getCommandSyntax();
		String[] commandRegex = dslObj.getCommandRegex();
		commandSyntax = MessageFormat.format(commandSyntax, commandRegex);
		Object[] input = new Object[commandRegex.length];
		Object result = null;
		int cnt = 0;
		if (command.matches(commandSyntax)) {
			Object[] orignalCommand = commandSyntax.split(" ");
			Object[] userCommand = command.split(" ");
			for (int i = 0; i < orignalCommand.length; i++) {
				if (!orignalCommand[i].equals(userCommand[i])) {
					input[cnt] = userCommand[i];
					cnt++;
				}
			}

			Class[] paramClass = m.getParameterTypes();
			for (int i = 0; i < paramClass.length; i++) {
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
			}
		}
		return result;
	}
}
