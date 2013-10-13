package org.dsl.ControlLoops;

import java.util.List;

import org.dsl.ControlLoops.ControlLoopType.LoopType;
import org.dsl.exception.DSLExecFailException;

public class ControlLoopsFactory {

	public static int processControlLoop(LoopType loopType,List<String> dslCommands,int j) throws DSLExecFailException {
		ControlLoops controlLoop = null;
		int k=0;
		switch (loopType) {
		case IF: {			
			controlLoop = new IfElseControl();
			k=controlLoop.perform(dslCommands, j);
			break;
		}
		default: {
			System.out.println("Invalid control loop type");
			break;
		}
		}
		return k;
	}
}


