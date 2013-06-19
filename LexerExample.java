import java.text.ParseException ;
import java.util.*;
public class Lexer implements Cloneable {
	String input;
	boolean skipWS;
	int curChar;
	int curPos;
	int curStringNumber;
	int posDiff;
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
		c.curStringNumber = curStringNumber;
		c.posDiff = posDiff;
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
				if ( input.charAt(curPos) == '\n' ) {
					curStringNumber++;
					posDiff = curPos;
				}
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
	
	public int curStringNumber() {
		return curStringNumber;
	}
	
	public int getPosInString() {
		return curPos - posDiff;
	}
	
	public Lexer(String str) throws ParseException {
		input = str;
		curPos = 0;
		curStringNumber = 0;
		regexps = new ArrayList<Regexp>();

		//auto-generated
		skipWS = true;
		regexps.add(new Regexp("[a-z]+", Token.TokenName.ID));
	}
}
