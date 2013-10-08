package org.dsl.ControlLoops;

import java.util.List;
import org.dsl.ControlLoops.ControlLoopType.LoopType;
import org.dsl.exception.DSLExecFailException;

public abstract class ControlLoops {
	private LoopType loopType = null;
	
	public LoopType getLoopType() {
		return loopType;
	}

	public void setLoopType(LoopType loopType) {
		this.loopType = loopType;
	}

	public ControlLoops(LoopType type) {
	   this.loopType = type;	
	}
	
	protected abstract int perform(List<String> dslCommands,int j) throws DSLExecFailException;

}
