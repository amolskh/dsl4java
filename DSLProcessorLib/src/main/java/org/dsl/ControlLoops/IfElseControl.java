package org.dsl.ControlLoops;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dsl.ControlLoops.ControlLoopType.LoopType;
import org.dsl.Initialise.InitDSL;
import org.dsl.bean.DSLObject;
import org.dsl.exception.DSLExecFailException;

public class IfElseControl extends ControlLoops{

	IfElseControl() throws DSLExecFailException {
		super(LoopType.IF);
	}

	@Override
	public int perform(List<String> dslCommands,int j) throws DSLExecFailException{
		int i=0;

		String var=dslCommands.get(j);
		String[] arr = var.split("If");
		String expression = arr[1].trim();		

		Pattern p = Pattern.compile("\\{(.*?)\\}");
		Matcher m = p.matcher(expression);

		while(m.find()) {
			String temp=m.group(1);
			String val =InitDSL.getVariableValue("{"+temp+"}").toString();
			expression=expression.replace("{"+temp+"}", val);		
		}	

		BooleanOperations booleanOps = new BooleanOperations();

		if(booleanOps.getValue(expression)){
			for (i=j+1; i < dslCommands.size(); i++) {			
				Iterator it = InitDSL.methodCommandMapping.entrySet().iterator();

				if (((String)dslCommands.get(i)).equals("End If")){
					if (((String)dslCommands.get(i+1)).equals("Else")){
						while (!((String)dslCommands.get(i)).equals("End Else")){
							i++;
							continue;	
						}						
					}
					i=i+1;
					break;			
				}

				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();

					if (((String)dslCommands.get(i)).matches((String)pairs.getKey())) {
						DSLObject obj = (DSLObject)pairs.getValue();
						Object result = InitDSL.invokeMethod(obj,(String)dslCommands.get(i));
						if ((result == null) || (i >= dslCommands.size() - 1))
							break;
						if (!((String)dslCommands.get(i + 1)).startsWith("Assign"))
							break;
						String varName = ((String)dslCommands.get(i + 1)).replaceAll("Assign ","");
						InitDSL.runTimeVars.put(varName,result);
						i++;
						break;
					}
				}
			}
		}else{
			while (!((String)dslCommands.get(j)).equals("End If")){
				j++;
				continue;	
			}

			if(((String)dslCommands.get(j+1)).equals("Else")){				

				for (i=j+1; i < dslCommands.size(); i++) {			
					Iterator it = InitDSL.methodCommandMapping.entrySet().iterator();

					if (((String)dslCommands.get(i)).equals("End Else")){
						i=i+1;
						break;			
					}

					while (it.hasNext()) {
						Map.Entry pairs = (Map.Entry)it.next();

						if (((String)dslCommands.get(i)).matches((String)pairs.getKey())) {
							DSLObject obj = (DSLObject)pairs.getValue();
							Object result = InitDSL.invokeMethod(obj,(String)dslCommands.get(i));
							if ((result == null) || (i >= dslCommands.size() - 1))
								break;
							if (!((String)dslCommands.get(i + 1)).startsWith("Assign"))
								break;
							String varName = ((String)dslCommands.get(i + 1)).replaceAll("Assign ", "");
							InitDSL.runTimeVars.put(varName,result);
							i++;
							break;
						}
					}
				}
			}else{
				i=j+1;
			}
		}		
		return i;
	}
}
