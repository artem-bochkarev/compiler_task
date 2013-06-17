import java.text.ParseException ;
import java.util.*;
public class Lexer implements Cloneable {
	String input;
	boolean skipWS;
	int curChar;
	int curPos;
	Token.TokenName curToken;
	String curStr;
	
	
	private class Regexp {
		public String expr;
		public String name;
		public Token.TokenName token;
		Regexp( String regexp, Token.TokenName name, String rule) {
			expr = regexp;
			token = name;
			this.name= rule; 
		}
	}
	
	ArrayList<Regexp> regexps;
	
	public Lexer copy() throws ParseException {
		Lexer c = new Lexer(input);
		c.skipWS = skipWS;
		c.curChar = curChar;
		c.curPos = curPos;
		c.curToken = curToken;
		c.curStr = curStr;
		c.regexps = regexps;
	    return c;
	}
	
	
	private boolean isBlank(int c) {
		return (c == ' ' || c == '\r' || c == '\n' || c == '\t');
	}
	
	private void nextChar() {
		curPos++;
		if (curPos >= input.length()) {
			curChar = -1;
		}else {
			curChar = input.charAt(curPos);
		}
	}
	
	private void skipSpaces() throws ParseException {
		if (skipWS == true) {
			if ( curPos >= input.length() )
				return;
			while (isBlank(input.charAt(curPos))) {
				curPos++;
				if ( curPos >= input.length() )
					return;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void nextToken() throws ParseException {
		ArrayList<Regexp> regs = (ArrayList<Regexp>)regexps.clone();

		skipSpaces();
		for ( int i=input.length(); i>curPos; --i ) {
			curStr = input.substring(curPos, i);
			for ( Regexp regexp : regs ) {
				if ( curStr.matches(regexp.expr) ) {
					curToken = regexp.token;
					curPos = i;
					return;
				}
			}
		}
		if ( curPos >= input.length() ) {
			curToken = Token.TokenName.END;
		}else {
			throw new ParseException("No possible matches for Lexer", curPos);
		}
	}
	public Token curToken() {
		return new Token(curToken, curStr);
	}
	public int curPos() {
		return curPos;
	}
	
	public Lexer(String str) throws ParseException {
		input = str;
		curPos = 0;
		regexps = new ArrayList<Regexp>();

		//auto-generated
		skipWS = true;
		regexps.add(new Regexp(";", Token.TokenName.T_DC, "T_DC" ));
		regexps.add(new Regexp("int", Token.TokenName.T_INT, "T_INT" ));
		regexps.add(new Regexp("float", Token.TokenName.T_FLOAT, "T_FLOAT" ));
		regexps.add(new Regexp("void", Token.TokenName.T_VOID, "T_VOID" ));
		regexps.add(new Regexp("\\d+", Token.TokenName.T_INTVALUE, "T_INTVALUE" ));
		regexps.add(new Regexp("=", Token.TokenName.T_ASSIGN, "T_ASSIGN" ));
		regexps.add(new Regexp("\\(", Token.TokenName.T_OS, "T_OS" ));
		regexps.add(new Regexp("\\)", Token.TokenName.T_CS, "T_CS" ));
		regexps.add(new Regexp("\\{", Token.TokenName.T_FOS, "T_FOS" ));
		regexps.add(new Regexp("\\}", Token.TokenName.T_FCS, "T_FCS" ));
		regexps.add(new Regexp("\\[", Token.TokenName.T_SOS, "T_SOS" ));
		regexps.add(new Regexp("\\]", Token.TokenName.T_SCS, "T_SCS" ));
		regexps.add(new Regexp("if", Token.TokenName.T_IF, "T_IF" ));
		regexps.add(new Regexp("else", Token.TokenName.T_ELSE, "T_ELSE" ));
		regexps.add(new Regexp("while", Token.TokenName.T_WHILE, "T_WHILE" ));
		regexps.add(new Regexp("return", Token.TokenName.T_RETURN, "T_RETURN" ));
		regexps.add(new Regexp("\\+", Token.TokenName.T_PLUS, "T_PLUS" ));
		regexps.add(new Regexp("\\-", Token.TokenName.T_MINUS, "T_MINUS" ));
		regexps.add(new Regexp("\\*", Token.TokenName.T_MUL, "T_MUL" ));
		regexps.add(new Regexp("\\\\", Token.TokenName.T_DIV, "T_DIV" ));
		regexps.add(new Regexp("\\s*,\\s*", Token.TokenName.T_COMMON, "T_COMMON" ));
		regexps.add(new Regexp("[a-zA-Z]\\w*", Token.TokenName.T_ID, "T_ID" ));
		regexps.add(new Regexp("(\"\")|(\".*?[^\\\\]\")", Token.TokenName.T_STRING, "T_STRING" ));
	}
}
