import java.text.ParseException;
import java.util.*;
public class Parser {
	Lexer lex;
	ParseException globalException;
	
	Tree parse(String str) throws ParseException {
		lex = new Lexer(str);
		lex.nextToken();
		//auto-generated
		Tree result = 	S();
		if (lex.curToken().name != Token.TokenName.END) {
			throw globalException;
		}
		return result;
	}
		HashSet<String> variables = new HashSet<String>();

	Tree type() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_0_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "type";
		try {
			if (lex.curToken().name == Token.TokenName.T_INT) {
				token_0_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("type", new Tree(token_0_0.str));
			return result;
		}

		Token token_1_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "type";
		try {
			if (lex.curToken().name == Token.TokenName.T_FLOAT) {
				token_1_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("type", new Tree(token_1_0.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree S() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_2_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "S";
		try {
			subTree_2_0 = program();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("S", subTree_2_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree program() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_3_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "program";
		try {
			subTree_3_0 = global_list();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("program", subTree_3_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree global_list() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_4_0 = null;
		Tree subTree_4_1 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "global_list";
		try {
			subTree_4_0 = global_statement();
			subTree_4_1 = global_list();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("global_list", subTree_4_0, subTree_4_1);
			return result;
		}

		Tree subTree_5_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "global_list";
		try {
			subTree_5_0 = global_statement();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("global_list", subTree_5_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree global_statement() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_6_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "global_statement";
		try {
			subTree_6_0 = var_def();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("global_statement", subTree_6_0);
			return result;
		}

		Tree subTree_7_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "global_statement";
		try {
			subTree_7_0 = func_def();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("global_statement", subTree_7_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree var_def() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_8_0 = null;
		Tree subTree_8_1 = null;
		Token token_8_2 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "var_def";
		try {
			subTree_8_0 = type();
			subTree_8_1 = var_init_list();
			if (lex.curToken().name == Token.TokenName.T_DC) {
				token_8_2 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("var_def", subTree_8_0, subTree_8_1, new Tree(token_8_2.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree var_init_list() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_9_0 = null;
		Token token_9_1 = null;
		Tree subTree_9_2 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "var_init_list";
		try {
			subTree_9_0 = var_init();
			if (lex.curToken().name == Token.TokenName.T_COMMON) {
				token_9_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_9_2 = var_init_list();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("var_init_list", subTree_9_0, new Tree(token_9_1.str), subTree_9_2);
			return result;
		}

		Tree subTree_10_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "var_init_list";
		try {
			subTree_10_0 = var_init();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("var_init_list", subTree_10_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree var_init() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_11_0 = null;
		Token token_11_1 = null;
		Tree subTree_11_2 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "var_init";
		try {
			if (lex.curToken().name == Token.TokenName.T_ID) {
				token_11_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_ASSIGN) {
				token_11_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_11_2 = expression();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("var_init", new Tree(token_11_0.str), new Tree(token_11_1.str), subTree_11_2);
			return result;
		}

		Token token_12_0 = null;
		Token token_12_1 = null;
		Tree subTree_12_2 = null;
		Token token_12_3 = null;
		Token token_12_4 = null;
		Tree subTree_12_5 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "var_init";
		try {
			if (lex.curToken().name == Token.TokenName.T_ID) {
				token_12_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_SOS) {
				token_12_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_12_2 = expression();
			if (lex.curToken().name == Token.TokenName.T_SCS) {
				token_12_3 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_ASSIGN) {
				token_12_4 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_12_5 = expression();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("var_init", new Tree(token_12_0.str), new Tree(token_12_1.str), subTree_12_2, new Tree(token_12_3.str), new Tree(token_12_4.str), subTree_12_5);
			return result;
		}

		Token token_13_0 = null;
		Token token_13_1 = null;
		Tree subTree_13_2 = null;
		Token token_13_3 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "var_init";
		try {
			if (lex.curToken().name == Token.TokenName.T_ID) {
				token_13_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_SOS) {
				token_13_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_13_2 = expression();
			if (lex.curToken().name == Token.TokenName.T_SCS) {
				token_13_3 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("var_init", new Tree(token_13_0.str), new Tree(token_13_1.str), subTree_13_2, new Tree(token_13_3.str));
			return result;
		}

		Token token_14_0 = null;
		Token token_14_1 = null;
		Tree subTree_14_2 = null;
		Token token_14_3 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "var_init";
		try {
			if (lex.curToken().name == Token.TokenName.T_ID) {
				token_14_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_OS) {
				token_14_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_14_2 = expression();
			if (lex.curToken().name == Token.TokenName.T_CS) {
				token_14_3 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("var_init", new Tree(token_14_0.str), new Tree(token_14_1.str), subTree_14_2, new Tree(token_14_3.str));
			return result;
		}

		Token token_15_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "var_init";
		try {
			if (lex.curToken().name == Token.TokenName.T_ID) {
				token_15_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("var_init", new Tree(token_15_0.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree func_def() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_16_0 = null;
		Token token_16_1 = null;
		Token token_16_2 = null;
		Tree subTree_16_3 = null;
		Token token_16_4 = null;
		Tree subTree_16_5 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "func_def";
		try {
			subTree_16_0 = type();
			if (lex.curToken().name == Token.TokenName.T_ID) {
				token_16_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_OS) {
				token_16_2 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_16_3 = arg_decl_list();
			if (lex.curToken().name == Token.TokenName.T_CS) {
				token_16_4 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_16_5 = statements_block();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("func_def", subTree_16_0, new Tree(token_16_1.str), new Tree(token_16_2.str), subTree_16_3, new Tree(token_16_4.str), subTree_16_5);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree arg_decl_list() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_17_0 = null;
		Token token_17_1 = null;
		Tree subTree_17_2 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "arg_decl_list";
		try {
			subTree_17_0 = arg_decl();
			if (lex.curToken().name == Token.TokenName.T_COMMON) {
				token_17_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_17_2 = arg_decl_list();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("arg_decl_list", subTree_17_0, new Tree(token_17_1.str), subTree_17_2);
			return result;
		}

		Tree subTree_18_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "arg_decl_list";
		try {
			subTree_18_0 = arg_decl();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("arg_decl_list", subTree_18_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree arg_decl() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_19_0 = null;
		Token token_19_1 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "arg_decl";
		try {
			subTree_19_0 = type();
			if (lex.curToken().name == Token.TokenName.T_ID) {
				token_19_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("arg_decl", subTree_19_0, new Tree(token_19_1.str));
			return result;
		}

		Tree subTree_20_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "arg_decl";
		try {
			subTree_20_0 = type();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("arg_decl", subTree_20_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree statement_or_block() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_21_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statement_or_block";
		try {
			subTree_21_0 = statements_block();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statement_or_block", subTree_21_0);
			return result;
		}

		Tree subTree_22_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statement_or_block";
		try {
			subTree_22_0 = statement();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statement_or_block", subTree_22_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree statements_block() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_23_0 = null;
		Tree subTree_23_1 = null;
		Token token_23_2 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statements_block";
		try {
			if (lex.curToken().name == Token.TokenName.T_FOS) {
				token_23_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_23_1 = statements_list();
			if (lex.curToken().name == Token.TokenName.T_FCS) {
				token_23_2 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statements_block", new Tree(token_23_0.str), subTree_23_1, new Tree(token_23_2.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree statements_list() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_24_0 = null;
		Tree subTree_24_1 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statements_list";
		try {
			subTree_24_0 = statement();
			subTree_24_1 = statements_list();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statements_list", subTree_24_0, subTree_24_1);
			return result;
		}

		Tree subTree_25_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statements_list";
		try {
			subTree_25_0 = statement();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statements_list", subTree_25_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree statement() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_26_0 = null;
		Token token_26_1 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statement";
		try {
			subTree_26_0 = expression();
			if (lex.curToken().name == Token.TokenName.T_DC) {
				token_26_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statement", subTree_26_0, new Tree(token_26_1.str));
			return result;
		}

		Tree subTree_27_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statement";
		try {
			subTree_27_0 = if_stmt();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statement", subTree_27_0);
			return result;
		}

		Tree subTree_28_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statement";
		try {
			subTree_28_0 = while_stmt();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statement", subTree_28_0);
			return result;
		}

		Tree subTree_29_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statement";
		try {
			subTree_29_0 = return_stmt();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statement", subTree_29_0);
			return result;
		}

		Tree subTree_30_0 = null;
		Token token_30_1 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "statement";
		try {
			subTree_30_0 = var_def();
			if (lex.curToken().name == Token.TokenName.T_DC) {
				token_30_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("statement", subTree_30_0, new Tree(token_30_1.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree while_stmt() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_31_0 = null;
		Token token_31_1 = null;
		Tree subTree_31_2 = null;
		Token token_31_3 = null;
		Tree subTree_31_4 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "while_stmt";
		try {
			if (lex.curToken().name == Token.TokenName.T_WHILE) {
				token_31_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_OS) {
				token_31_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_31_2 = expression();
			if (lex.curToken().name == Token.TokenName.T_CS) {
				token_31_3 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_31_4 = statement_or_block();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("while_stmt", new Tree(token_31_0.str), new Tree(token_31_1.str), subTree_31_2, new Tree(token_31_3.str), subTree_31_4);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree if_stmt() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_32_0 = null;
		Token token_32_1 = null;
		Tree subTree_32_2 = null;
		Token token_32_3 = null;
		Tree subTree_32_4 = null;
		Token token_32_5 = null;
		Tree subTree_32_6 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "if_stmt";
		try {
			if (lex.curToken().name == Token.TokenName.T_IF) {
				token_32_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_OS) {
				token_32_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_32_2 = expression();
			if (lex.curToken().name == Token.TokenName.T_CS) {
				token_32_3 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_32_4 = statement_or_block();
			if (lex.curToken().name == Token.TokenName.T_ELSE) {
				token_32_5 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_32_6 = statement_or_block();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("if_stmt", new Tree(token_32_0.str), new Tree(token_32_1.str), subTree_32_2, new Tree(token_32_3.str), subTree_32_4, new Tree(token_32_5.str), subTree_32_6);
			return result;
		}

		Token token_33_0 = null;
		Token token_33_1 = null;
		Tree subTree_33_2 = null;
		Token token_33_3 = null;
		Tree subTree_33_4 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "if_stmt";
		try {
			if (lex.curToken().name == Token.TokenName.T_IF) {
				token_33_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_OS) {
				token_33_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_33_2 = expression();
			if (lex.curToken().name == Token.TokenName.T_CS) {
				token_33_3 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_33_4 = statement_or_block();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("if_stmt", new Tree(token_33_0.str), new Tree(token_33_1.str), subTree_33_2, new Tree(token_33_3.str), subTree_33_4);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree return_stmt() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_34_0 = null;
		Tree subTree_34_1 = null;
		Token token_34_2 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "return_stmt";
		try {
			if (lex.curToken().name == Token.TokenName.T_RETURN) {
				token_34_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_34_1 = expression();
			if (lex.curToken().name == Token.TokenName.T_DC) {
				token_34_2 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("return_stmt", new Tree(token_34_0.str), subTree_34_1, new Tree(token_34_2.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree expression() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_35_0 = null;
		Tree subTree_35_1 = null;
		Token token_35_2 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "expression";
		try {
			if (lex.curToken().name == Token.TokenName.T_OS) {
				token_35_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_35_1 = expression();
			if (lex.curToken().name == Token.TokenName.T_CS) {
				token_35_2 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("expression", new Tree(token_35_0.str), subTree_35_1, new Tree(token_35_2.str));
			return result;
		}

		Tree subTree_36_0 = null;
		Tree subTree_36_1 = null;
		Tree subTree_36_2 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "expression";
		try {
			subTree_36_0 = expr();
			subTree_36_1 = operator();
			subTree_36_2 = expression();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("expression", subTree_36_0, subTree_36_1, subTree_36_2);
			return result;
		}

		Tree subTree_37_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "expression";
		try {
			subTree_37_0 = expr();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("expression", subTree_37_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree expr() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_38_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "expr";
		try {
			subTree_38_0 = function_call();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("expr", subTree_38_0);
			return result;
		}

		Tree subTree_39_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "expr";
		try {
			subTree_39_0 = number();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("expr", subTree_39_0);
			return result;
		}

		Tree subTree_40_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "expr";
		try {
			subTree_40_0 = var_access();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("expr", subTree_40_0);
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree operator() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_41_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "operator";
		try {
			if (lex.curToken().name == Token.TokenName.T_PLUS) {
				token_41_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("operator", new Tree(token_41_0.str));
			return result;
		}

		Token token_42_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "operator";
		try {
			if (lex.curToken().name == Token.TokenName.T_MINUS) {
				token_42_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("operator", new Tree(token_42_0.str));
			return result;
		}

		Token token_43_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "operator";
		try {
			if (lex.curToken().name == Token.TokenName.T_MUL) {
				token_43_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("operator", new Tree(token_43_0.str));
			return result;
		}

		Token token_44_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "operator";
		try {
			if (lex.curToken().name == Token.TokenName.T_DIV) {
				token_44_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("operator", new Tree(token_44_0.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree var_access() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_45_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "var_access";
		try {
			if (lex.curToken().name == Token.TokenName.T_ID) {
				token_45_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("var_access", new Tree(token_45_0.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree number() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_46_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "number";
		try {
			if (lex.curToken().name == Token.TokenName.T_INTVALUE) {
				token_46_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("number", new Tree(token_46_0.str));
			return result;
		}

		Token token_47_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "number";
		try {
			if (lex.curToken().name == Token.TokenName.T_STRING) {
				token_47_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("number", new Tree(token_47_0.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree function_call() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Token token_48_0 = null;
		Token token_48_1 = null;
		Tree subTree_48_2 = null;
		Token token_48_3 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "function_call";
		try {
			if (lex.curToken().name == Token.TokenName.T_ID) {
				token_48_0 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			if (lex.curToken().name == Token.TokenName.T_OS) {
				token_48_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_48_2 = arg_list();
			if (lex.curToken().name == Token.TokenName.T_CS) {
				token_48_3 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("function_call", new Tree(token_48_0.str), new Tree(token_48_1.str), subTree_48_2, new Tree(token_48_3.str));
			return result;
		}

		globalException = p;
		throw p;
	}
	Tree arg_list() throws ParseException {
		boolean isGood = true;
		Lexer copy = null;
		Tree result = null;
		String value = null;
		ParseException p = new ParseException("", 0);
		HashSet<String> TEMP_variables = null;
		Tree subTree_49_0 = null;
		Token token_49_1 = null;
		Tree subTree_49_2 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "arg_list";
		try {
			subTree_49_0 = expression();
			if (lex.curToken().name == Token.TokenName.T_COMMON) {
				token_49_1 = lex.curToken();
				lex.nextToken();
			}else {
				throw new ParseException("This token(" + lex.curToken().str + ") not expected here", lex.curPos );
			}
			subTree_49_2 = expression();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("arg_list", subTree_49_0, new Tree(token_49_1.str), subTree_49_2);
			return result;
		}

		Tree subTree_50_0 = null;
		copy = lex.copy();
		TEMP_variables = (HashSet<String>) variables.clone();
		isGood = true;
		value = "arg_list";
		try {
			subTree_50_0 = expression();
		}catch(ParseException a) {
			isGood = false;
			lex = copy;
			if ( a.getErrorOffset() > p.getErrorOffset() )
				p = a;
			variables = TEMP_variables;
		}
		if (isGood == true) {
			result = new Tree("arg_list", subTree_50_0);
			return result;
		}

		globalException = p;
		throw p;
	}
}
