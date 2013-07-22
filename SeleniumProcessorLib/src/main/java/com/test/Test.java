package com.test;

import org.dsl.annotation.DSL;




public class Test {

	@DSL(commName = "Concat", commRegex = {"[a-zA-z0-9{}%]{1,}","[a-zA-z0-9{}%]{1,}"}, commSyntax = "Concat {0} and {1}")
	public String concatString(String a, String b) {
		return a.concat(b);
	}

	/*@DSL(commName = "Sub", commRegex = { "(\\d){1,10}", "(\\d){1,10}" }, commSyntax = "Sub {0} from {1}")
	public int sub(int a, int b) {
		return b - a;
	}

	@DSL(commName = "Sub", commRegex = { "(\\d){1,10}", "(\\d){1,10}" }, commSyntax = "Mul {0} and {1}")
	public int multiply(int a, int b) {
		return a * b;
	}

	@DSL(commName = "Print", commRegex = { "[a-zA-z0-9{}%]{1,}" }, commSyntax = "Print {0}")
	public void print(String obj) {
		System.out.println(InitDSL.runTimeVars.get(obj));
	}*/

}