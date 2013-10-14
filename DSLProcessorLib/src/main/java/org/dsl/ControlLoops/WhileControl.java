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

public class WhileControl extends ControlLoops {

	WhileControl() throws DSLExecFailException {
		super(LoopType.WHILE);
	}

	@Override
	public int perform(List<String> dslCommands,int j)throws DSLExecFailException{
		int i=0;
		int k=j;

		BooleanOperations booleanOps = new BooleanOperations();

		while(booleanOps.getValue(getBooleanExpression(dslCommands,k))){
			for (i=k+1; i < dslCommands.size(); i++) {			
				Iterator it = InitDSL.methodCommandMapping.entrySet().iterator();

				if(((String)dslCommands.get(i)).equals("End While")){
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
		}
		while (!((String)dslCommands.get(k)).equals("End While")){
			k++;
			continue;	
		}		
    	return k+1;
	}

	public String getBooleanExpression(List<String> dslCommands,int k){
		String var=dslCommands.get(k);
		String[] arr = var.split("While");
		String expression = arr[1].trim();		

		Pattern p = Pattern.compile("\\{(.*?)\\}");
		Matcher m = p.matcher(expression);

		while(m.find()) {
			String temp=m.group(1);
			String val =InitDSL.getVariableValue("{"+temp+"}").toString();
			expression=expression.replace("{"+temp+"}", val);		
		}	
		return expression;	
	}

}
