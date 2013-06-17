import java.text.ParseException;
import java.util.*;
public class Parser {
	Lexer lex;
	ParseException globalException;
	
	Tree parse(String str) throws ParseException {
		lex = new Lexer(str);
		lex.nextToken();
		//auto-generated
		Tree result = S();
		if (lex.curToken().name != Token.TokenName.END) {
			throw new ParseException(" $  expected "+
					"at position", lex.curPos());
		}
		return result;
	}
	
	
	Tree S() throws ParseException {
		
		LexerExample copy = (LexerExample) lex.clone();
		
		switch (lex.curToken()) {
		case SUM: {
			// +
			
			lex.nextToken();
			Tree sub1 = S();
			Tree sub2 = S();
			return new Tree ("S", new Tree("+"), sub1, sub2);
		}
		case MUL: {
			// *
			lex.nextToken();
			Tree sub1 = S();
			Tree sub2 = S();
			return new Tree ("S", new Tree("*"), sub1, sub2);
		}
		case SUB: {
			// *
			lex.nextToken();
			Tree sub1 = S();
			Tree sub2 = S();
			return new Tree ("S", new Tree("-"), sub1, sub2);
		}
		case VALUE: {
			Tree sub = V();
			return new Tree ("S", sub);
		}
		case END:
			// eps
			return new Tree("S");
		default:
			throw new AssertionError();
		}
	}
	
	Tree V() throws ParseException {
		switch (lex.curToken()) {
		case VALUE: {
			// VALUE
			lex.nextToken();
			return new Tree ("V");
		}
		default:
			throw new AssertionError();
		}
	}
}
