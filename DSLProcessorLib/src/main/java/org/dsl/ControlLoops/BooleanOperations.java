package org.dsl.ControlLoops;

import java.util.Stack;
import java.util.StringTokenizer;

public class BooleanOperations {

	int lhs;
	int rhs;

	private Stack<Integer> opStack;       
	private Stack<String>  postfixStack;  
	private StringTokenizer str;
	private static final int EOL     = 0;
	private static final int SPACE     = 1;
	private static final int VALUE   = 2;
	private static final int OPAREN  = 3;
	private static final int CPAREN  = 4;
	//private static final int BITWISE_NOT  = 5;
	private static final int BOOLEAN_NOT  = 5;
	private static final int ADDITION = 6;
	private static final int RSHIFT     = 7;
	private static final int LSHIFT    = 8;
	private static final int AND     = 9;
	private static final int EXOR    = 10;
	private static final int OR   = 11;


	private static class Precedence
	{
		public String precedence;

		public Precedence(String precedence )
		{
			this.precedence  = precedence;
		}
	}

	private static Precedence [ ] precTable = new Precedence[ ]
			{ 
		new Precedence("-2"),  // EOL
		new Precedence("-1"),  // SPACE
		new Precedence("0"),  // VALUE
		new Precedence("0"),  // OPAREN
		new Precedence("99"),  // CPAREN
		new Precedence("7"), // Bitwise Not
		new Precedence("6"),  //  +
		new Precedence("5"),  //  >>
		new Precedence("4"),  //  <<
		new Precedence("3"),  //  &
		new Precedence("2"),  //  ^
		new Precedence("1")   //  |     
			};

	private static class Token
	{
		private int type = SPACE;
		private String value;


		public Token( )
		{
			this( SPACE );
		}

		public Token( int t )
		{
			this( t, null );
		}

		public Token( int t, String v )
		{
			type = t;
			if("true".equals(v)){
				value = "1";
			}else if("false".equals(v)){
				value = "0";
			}else{
				value=v;
			}
		}

		public int getType( )
		{
			return type;
		}

		public String getValue( )
		{
			return value;
		}

	}    

	private static class EvalTokenizer
	{
		private StringTokenizer str;

		public EvalTokenizer( StringTokenizer is )
		{
			str = is;
		}
		public Token getToken( )
		{           

			if( !str.hasMoreTokens( ) )
				return new Token( );

			String s = str.nextToken( );
			if( s.equals( " " ) ) return getToken( );
			if( s.equals( "+" ) ) return new Token( ADDITION );
			if( s.equals( "^" ) ) return new Token( EXOR );
			if( s.equals( ">" ) ) return new Token( RSHIFT );
			if( s.equals( "<" ) ) return new Token( LSHIFT );
			if( s.equals( "(" ) ) return new Token( OPAREN );
			if( s.equals( ")" ) ) return new Token( CPAREN );
			if( s.equals( "&" ) ) return new Token( AND );
			if( s.equals( "|" ) ) return new Token( OR );
			//if( s.equals( "~" ) ) return new Token( BITWISE_NOT );
			if( s.equals( "!" ) ) return new Token( BOOLEAN_NOT );

			// if its operand
			return new Token( VALUE, s );
		}
	}

	public boolean getValue(String expression )
	{
		expression = expression.replaceAll("and", "&");
		expression = expression.replaceAll("or", "|");
		expression = expression.replaceAll("not", "!");
		//System.out.println("expression"+expression);
		opStack = new Stack<Integer>( );
		postfixStack = new Stack<String>( );

		str = new StringTokenizer(expression,"!~+*-/^()&|>< ",true);

		opStack.push( EOL );

		EvalTokenizer tok = new EvalTokenizer( str );
		Token lastToken;

		do
		{
			lastToken = tok.getToken( );
			processToken( lastToken );
			
		} while( lastToken.getType( ) != SPACE );

		if( postfixStack.isEmpty( ) )
		{
			System.err.println( "=== ... Missing operand!" );
			return false;
		}
		
		String theResult = postfixStack.pop( );	
		if("1".equals(theResult)){
			theResult="true";
		}else if("0".equals(theResult)){
			theResult="false";
		}else{
			//value=v;
		}

		if( !postfixStack.isEmpty( ) )
			System.err.println( "===: missing operators!" );

		//System.out.println("Boolean Operation Result ="+(theResult));
		boolean result = Boolean.parseBoolean(theResult);
		return result;
	}

	private void processToken( Token lastToken )
	{
		int topOp;
		int lastType = lastToken.getType( );


		switch( lastType )
		{
		case VALUE:
			//System.out.println("case value pushing into postfix stack "+ lastToken.getValue( ));
			postfixStack.push( lastToken.getValue( ) );
			return;

		case CPAREN:
			while( ( topOp = opStack.peek( ) ) != OPAREN && topOp != EOL )
				binaryOp( topOp );
			if( topOp == OPAREN )
				opStack.pop( );  
			else
				System.err.println( " ...Missing open parenthesis" );
			break;

		case OPAREN:
			opStack.push( lastType );
			break;

		default:    
			while( Integer.parseInt(precTable[ lastType ].precedence) <= Integer.parseInt(precTable[ topOp = opStack.peek( ) ].precedence )) {
				/*System.out.println("process token precTable[ lastType ].inputSymbol =" +precTable[ lastType ].precedence +
						"topof stack"+precTable[ lastType ].precedence);
				System.out.println("process token precTable[ topOp = opStack.peek( ) ].topOfStack ="+
						Integer.parseInt(precTable[ topOp = opStack.peek( ) ].precedence ));*/

				binaryOp( topOp );
			}
			/*System.out.println("out of loop process token precTable[ lastType ].inputSymbol =" +precTable[ lastType ].precedence
					+"topof stack"+precTable[ lastType ].precedence);
			System.out.println("out of loop process token precTable[ topOp = opStack.peek( ) ].topOfStack ="+
					Integer.parseInt(precTable[ topOp = opStack.peek( ) ].precedence ));*/
			if( lastType != SPACE )
				opStack.push( lastType );
			break;
		}
	}

	private long postfixPop( )
	{
		Long top = -1l;
		if ( postfixStack.isEmpty( ) )
		{
			System.err.println( "=== ...Missing operand" );
			return 0;
		}
		String value = postfixStack.pop( );		
	
		if("true".equals(value)){
			value = "1";
		}else if("false".equals(value)){
			value = "0";
		}else{

		}				
		top = Long.parseLong(value,16);
		return top;
	}

	private void binaryOp( int topOp )
	{
		if( topOp == OPAREN )
		{
			System.err.println( "==== unbalanced parentheses" );
			opStack.pop( );
			return;
		}

		long rhs, lhs;

		if( topOp == EXOR ){
			rhs = postfixPop( );
		    lhs = postfixPop( );
			postfixStack.push( Long.toHexString(( lhs ^ rhs ) & 0xFFFFFFF) );
		}
		
		else if( topOp == AND ) {
			rhs = postfixPop( );
		    lhs = postfixPop( );
			//System.out.println("And operation");
			postfixStack.push( Long.toHexString((lhs & rhs) & 0xFFFFFFF) );
		    //postfixStack.push(lhs & rhs);
			//System.out.println("and result"+  Long.toHexString((lhs & rhs) & 0xFFFFFFF));
		}
		else if( topOp == OR ) {
			rhs = postfixPop( );
		    lhs = postfixPop( );
			//System.out.println("OR operation");
			postfixStack.push( Long.toHexString((lhs | rhs ) & 0xFFFFFFF) );
		}
		else if( topOp == LSHIFT ) {
			rhs = postfixPop( );
		    lhs = postfixPop( );
			System.out.println("left shift operation lhs="+lhs+" rhs= "+rhs+ " " +(( lhs << rhs ) ));
			long c = (long) ((lhs << rhs) & 0xFFFFFFF);
			System.out.println("--checker"+Long.toHexString(c));
			postfixStack.push( Long.toHexString(( lhs << rhs ) ) );
			System.out.println("left shift result ...."+  Long.toHexString((lhs << rhs) & 0xFFFFFFF));
			System.out.println("peek"+postfixStack.peek());
		}
		else if( topOp == RSHIFT ) {
			rhs = postfixPop( );
		    lhs = postfixPop( );
			System.out.println("right shift operation lhs="+lhs+" rhs= "+rhs+ " " +(( lhs >> rhs ) & 0xFFFFFFF ));
			postfixStack.push( Long.toHexString(( lhs >> rhs ) & 0xFFFFFFF) );
		}
		else if( topOp == ADDITION ) {
			rhs = postfixPop( );
		    lhs = postfixPop( );
			System.out.println("addition lhs="+lhs+" rhs="+rhs+" result="+ (( lhs + rhs ) & 0xFFFFFFF));
			postfixStack.push( Long.toHexString(( lhs + rhs ) & 0xFFFFFFF) );
		} 
		else if( topOp == BOOLEAN_NOT ) {
			lhs=rhs = postfixPop( );
		  	boolean vOut = rhs!=0;			
			//System.out.println("BOOLEAN_NOT="+lhs+" rhs="+rhs+" result="+ (!vOut));
			postfixStack.push(String.valueOf(!vOut));
		}		
		/*else if( topOp == BITWISE_NOT ) {
			rhs = postfixPop( );
		    lhs = postfixPop( );
			System.out.println("BITWISE_NOT="+lhs+" rhs="+rhs+" result="+ ((~rhs ) & 0xFFFFFFF));
			postfixStack.push( Long.toHexString(( ~rhs & 0xFFFFFFF)) );
		}*/
		opStack.pop( );
	}

}
