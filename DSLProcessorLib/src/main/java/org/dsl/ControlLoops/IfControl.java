package org.dsl.ControlLoops;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dsl.ControlLoops.ControlLoopType.LoopType;
import org.dsl.Initialise.InitDSL;
import org.dsl.bean.DSLObject;
import org.dsl.exception.DSLExecFailException;

public class IfControl extends ControlLoops{

	IfControl() throws DSLExecFailException {
		super(LoopType.IF);
	}

	@Override
	public int perform(List<String> dslCommands,int j) throws DSLExecFailException{
		int i=0;

		String var=dslCommands.get(j);
		String arr[]= var.split(" ");
		var=arr[1].replaceAll("\\(", "");
		var=var.replaceAll("\\)", "");		

		if((Boolean)InitDSL.getVariableValue(var)){

			for (i=j+1; i < dslCommands.size(); i++) {			
				Iterator it = InitDSL.methodCommandMapping.entrySet().iterator();

				if (((String)dslCommands.get(i)).equals("End If")){
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
						InitDSL.runTimeVars.put(varName, result);
						i++;
						break;
					}
				}
			}
		}
		return i;
	}
}
