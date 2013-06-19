import java.text.ParseException;
import java.util.*;


public class ParserHandMade {
	Lexer lex;
	ParseException globalException;
	
	public enum Terminal {
		program, global_list, global_statement, var_def, var_init_list, var_init, func_def, arg_decl_list, arg_decl,
		statement_or_block, statements_block, statements_list, statement, while_stmt, if_stmt, return_stmt,	expression,
		expr, operator, var_access, number, function_call, arg_list, type, S
	}
	
	private List<HashMap<String, Types>> identifiers;
	Types currentType;
	
	public enum Types {
		INT, FLOAT, CHAR
	}
	
	public ParserHandMade() {
		typesMap = new HashMap<String, ParserHandMade.Types>();
		typesMap.put("int", Types.INT);
		typesMap.put("float", Types.FLOAT);
		typesMap.put("char", Types.CHAR);
		identifiers = new ArrayList<HashMap<String, Types>>();
		identifiers.add(new HashMap<String, ParserHandMade.Types>());
	}
	
	Lexer choosePanicLexer(Lexer l1, Lexer l2) {
		if ( l1.curStringNumber == l2.curStringNumber ) {
			if ( l1.curPos > l2.curPos ) {
				return l1;
			}
			return l2;
		}
		if ( l1.curStringNumber > l2.curStringNumber ) {
			return l1;
		}else {
			return l2;
		}
	}
	
	Map<String, Types> typesMap; 
	
	Set<Token.TokenName> getNextTokenVariants( Terminal term ) {
		HashSet<Token.TokenName> resSet = new HashSet<Token.TokenName>();
		
		switch (term) {
		case program: {
			resSet.addAll(getNextTokenVariants(Terminal.global_list));
		}break;
		case global_list: {
			//resSet.addAll(getNextTokenVariants(Terminal.global_statement));
			resSet.addAll(getNextTokenVariants(Terminal.type));
		}break;
		case global_statement: {
			//resSet.addAll(getNextTokenVariants(Terminal.var_def));
			//resSet.addAll(getNextTokenVariants(Terminal.func_def));
			resSet.addAll(getNextTokenVariants(Terminal.type));
		}break;
		case var_def: {
			resSet.addAll(getNextTokenVariants(Terminal.type));
		}break;
		case var_init_list: {
			//resSet.addAll(getNextTokenVariants(Terminal.var_init));
			resSet.add   (Token.TokenName.T_ID);
		}break;
		case var_init: {
			resSet.add   (Token.TokenName.T_ID);
		}break;
		case func_def: {
			resSet.addAll(getNextTokenVariants(Terminal.type));
		}break;
		case arg_decl_list: {
			//resSet.addAll(getNextTokenVariants(Terminal.arg_decl));
			resSet.addAll(getNextTokenVariants(Terminal.type));
		}break;
		case arg_decl: {
			resSet.addAll(getNextTokenVariants(Terminal.type));
		}break;
		case statement_or_block: {
			//resSet.addAll(getNextTokenVariants(Terminal.statements_block));
			resSet.add   (Token.TokenName.T_FOS);
			resSet.addAll(getNextTokenVariants(Terminal.statement));
		}break;
		case statements_block: {
			resSet.add   (Token.TokenName.T_FOS);
		}break;
		case statements_list: {
			resSet.addAll(getNextTokenVariants(Terminal.statement));
		}break;
		case statement: {
			resSet.addAll(getNextTokenVariants(Terminal.expression));
			resSet.addAll(getNextTokenVariants(Terminal.if_stmt));
			resSet.addAll(getNextTokenVariants(Terminal.while_stmt));
			resSet.addAll(getNextTokenVariants(Terminal.return_stmt));
			resSet.addAll(getNextTokenVariants(Terminal.var_def));
		}break;
		case while_stmt: {
			resSet.add   (Token.TokenName.T_WHILE);
		}break;
		case if_stmt: {
			resSet.add   (Token.TokenName.T_IF);
		}break;
		case return_stmt: {
			resSet.add   (Token.TokenName.T_RETURN);
		}break;
		case expression: {
			resSet.add   (Token.TokenName.T_OS);
			//resSet.addAll(getNextTokenVariants(Terminal.expr));
			resSet.add   (Token.TokenName.T_INTVALUE);
			resSet.add   (Token.TokenName.T_ID);
		}break;
		case expr: {
			//resSet.addAll(getNextTokenVariants(Terminal.number));
			resSet.add   (Token.TokenName.T_INTVALUE);
			//resSet.addAll(getNextTokenVariants(Terminal.var_access));
			//resSet.addAll(getNextTokenVariants(Terminal.function_call));
			resSet.add   (Token.TokenName.T_ID);
		}break;
		case operator: {
			resSet.add   (Token.TokenName.T_PLUS);
			resSet.add   (Token.TokenName.T_MINUS);
			resSet.add   (Token.TokenName.T_MUL);
			resSet.add   (Token.TokenName.T_DIV);
		}break;
		case var_access: {
			resSet.add   (Token.TokenName.T_ID);
		}break;
		case number: {
			resSet.add   (Token.TokenName.T_INTVALUE);
		}break;
		case function_call: {
			resSet.add   (Token.TokenName.T_ID);
		}break;
		case arg_list: {
			//resSet.addAll(getNextTokenVariants(Terminal.expression));
			resSet.add   (Token.TokenName.T_OS);
			resSet.add   (Token.TokenName.T_INTVALUE);
			resSet.add   (Token.TokenName.T_ID);
		}break;
		case type: {
			//resSet.add   (Token.TokenName.T_INT);
			//resSet.add   (Token.TokenName.T_FLOAT);
			resSet.add(Token.TokenName.T_ID);
		}break;
		case S: {
			resSet.addAll(getNextTokenVariants(Terminal.program));
		}break;
		default:
			throw new RuntimeException("Terminal not described in list");
		}
		
		return resSet;
	}
	
	/*Map<Token.TokenName, List<Terminal>> getNextTokenMap( Terminal term ) {
		HashSet<Token.TokenName> resSet = new HashSet<Token.TokenName>();
		
		
		
		return resSet;
	}*/
	
	void skipWhile(Token.TokenName name) throws ParseException {
		while ( (lex.curToken != Token.TokenName.END) && (lex.curToken != name) ) {
			lex.nextToken();
		}
	}
	
	void skipWhileCountOne(Token.TokenName inc, Token.TokenName dec) throws ParseException {
		int counter = 1;
		while ( lex.curToken != Token.TokenName.END && counter > 0 ) {
			lex.nextToken();
			if (lex.curToken == inc)
				++counter;
			if (lex.curToken == dec)
				--counter;
		}
	}
	
	void skipWhileCount(Token.TokenName inc, Token.TokenName dec) throws ParseException {
		while ( (lex.curToken != Token.TokenName.END) && (lex.curToken != inc) ) {
			lex.nextToken();
		}
		skipWhileCountOne(inc, dec);
	}
	
	void skipWhile(Set<Token.TokenName> set) throws ParseException {
		while ( (lex.curToken != Token.TokenName.END) && (set.contains( lex.curToken ) == false) ) {
			lex.nextToken();
		}
	}
	
	
	Result parse(String str) throws ParseException {
		lex = new Lexer(str);
		lex.nextToken();
		//auto-generated
		Result result = S();
		/*if (lex.curToken().name != Token.TokenName.END) {
			throw globalException;
		}*/
		return result;
	}
		HashSet<String> variables = new HashSet<String>();
	
		private void addToResultAndNext(Result result, Lexer lex) throws ParseException {
			result.addChildren(lex.curStr);
			lex.nextToken();
		}

	Result type() throws ParseException {
		Result result = new Result("type");
		
		String typeName = lex.curStr;
		if ( typesMap.containsKey(typeName) == false ) {
			//error unknown type
			result.setError("Unknown type: " + typeName + '\n', lex.getPosInString(), lex.curStringNumber);
			return result;
		}
		currentType = typesMap.get(typeName);
		addToResultAndNext(result, lex);
		return result;
	}
	Result S() throws ParseException {
		Result result = new Result("S");
		result.appendResult(program());
		return result;
	}
	Result program() throws ParseException {
		Result result = new Result("program");
		result.appendResult(global_list());
		if ( lex.curToken != Token.TokenName.END ) {
			result.setError("This token" + lex.curStr + "not expected here, END shoud be here\n", lex.getPosInString(), lex.curStringNumber);
		}
		addToResultAndNext(result, lex);
		return result;
	}
	
	Result global_list() throws ParseException {
		Result result = new Result("global_list");
		
		Set<Token.TokenName> tokensSet = getNextTokenVariants(Terminal.global_list);
		if ( tokensSet.contains(lex.curToken().name) == false ) {
			//error unknown symbol...PANIC!!!
			result.setError( "This token not expected here: " + lex.curStr + '\n', lex.getPosInString(), lex.curStringNumber );
			//change to figure scope
			skipWhile(tokensSet);
		}else {
			//normal
			result.appendResult(global_statement());
		}
		if ( lex.curToken != Token.TokenName.END ) {
			result.appendResult(global_list());
		}
		return result;
	}
	
	Result global_statement() throws ParseException {
		Result result = new Result("global_statement");
		Set<Token.TokenName> tokensSet = getNextTokenVariants(Terminal.global_statement);
		if (tokensSet.contains(lex.curToken().name) == false) {
			result.setError("This token not expected here: " + lex.curStr + '\n', lex.getPosInString(), lex.curStringNumber);
			return result;
		}
		Lexer copy = lex.copy();
		Token.TokenName shouldBeType = lex.curToken;
		lex.nextToken();
		
		Token.TokenName shouldBeID = lex.curToken;
		lex.nextToken();
		
		Token.TokenName varies = lex.curToken;
		lex = copy;
		if ( varies == Token.TokenName.T_ASSIGN || varies == Token.TokenName.T_DC || varies == Token.TokenName.T_SOS) {
			//auto skipped, don't restore lexer
			result.appendResult(var_def());
			if ( result.result != Result.Res.SUCCESS ) {
				result.addChildren("FAILED");
				skipWhile(Token.TokenName.T_DC);
				addToResultAndNext(result, lex);
				result.result = Result.Res.SUCCESS;
			}
			return result;
		}
		
		if ( varies == Token.TokenName.T_OS ) {
			//auto skipped, don't restore lexer
			result.appendResult(func_def());
			if ( result.result != Result.Res.SUCCESS ) {
				result.addChildren("FAILED");
				skipWhileCount(Token.TokenName.T_FOS, Token.TokenName.T_FCS);
				addToResultAndNext(result, lex);
				result.result = Result.Res.SUCCESS;
			}
			return result;
		}
		result.setError("This token (" + lex.curStr + ") not expected here, ( or = should be here\n", lex.getPosInString(), lex.curStringNumber);
		return result;
	}
	Result var_def() throws ParseException {
		Result result = new Result("var_def");
		Lexer copy = lex.copy();
		
		result.appendResult(type());
		if (result.result != Result.Res.SUCCESS) {
			result.setError("This token not expected here: " + lex.curStr + '\n', lex.getPosInString(), lex.curStringNumber, true);
			lex = copy;
			return result;
		}
		
		result.appendResult(var_init_list());
		if ( result.result == Result.Res.FAIL ) {
			lex = copy;
			return result;
		}
		if ( lex.curToken().name != Token.TokenName.T_DC ) {
			lex = copy;
			result.setError("This token (" + lex.curStr + ") not expected here, ; should be here\n", lex.getPosInString(), lex.curStringNumber, true);
			return result;
		}
		addToResultAndNext(result, lex);
		return result;
	}
	Result var_init_list() throws ParseException {
		Result result = new Result("var_init_list");
		result.appendResult(var_init());
		while ( result.result == Result.Res.SUCCESS && lex.curToken().name == Token.TokenName.T_COMMON ) {
			addToResultAndNext(result, lex);
			result.appendResult(var_init_list());
		}
		return result;
	}
	Result var_init() throws ParseException {
		Result result = new Result("var_init");
		Lexer copy = lex.copy();
		
		if ( lex.curToken().name != Token.TokenName.T_ID ) {
			result.setError("This token (" + lex.curStr + ") not expected here, T_ID should be here\n", lex.getPosInString(), lex.curStringNumber);
		}else {
			String id = lex.curStr;
			addToResultAndNext(result, lex);
			switch (lex.curToken().name) {
				case T_SOS: {
					//array init
					addToResultAndNext(result, lex);
					if ( lex.curToken != Token.TokenName.T_SCS ) {
						result.appendResult(expression());
						if ( result.result != Result.Res.SUCCESS ) {
							lex = copy;
							return result;
						}
					}
					
					if ( lex.curToken().name != Token.TokenName.T_SCS ) {
						lex = copy;
						result.setError("This token (" + lex.curStr + ") not expected here, T_SCS should be here\n", lex.getPosInString(), lex.curStringNumber);
						return result;
					}
					addToResultAndNext(result, lex);
					
					if ( lex.curToken().name == Token.TokenName.T_ASSIGN ) {
						addToResultAndNext(result, lex);
						result.appendResult(expression());
					}
				}break;
				case T_ASSIGN: {
					addToResultAndNext(result, lex);
					result.appendResult(expression());
				}break;
				case T_OS: {
					//disabled
					lex = copy;
					result.setError("This token (" + lex.curStr + ") not expected here, T_ASSIGN should be here\n", lex.getPosInString(), lex.curStringNumber);
				}break;
				default: {
					//result.tree = new Tree("type", new Tree(lex.curStr));
					//ok
				}
			}
			if ( result.result == Result.Res.SUCCESS ) {
				if ( identifiers.get(identifiers.size()-1).containsKey(id) )
					result.setError("Duplicate identifier: " + id + "\n", 0, lex.curStringNumber);
				else
					identifiers.get(identifiers.size()-1).put(id, currentType);
			}
		}
		
		return result;
	}
	Result func_def() throws ParseException {
		Result result = new Result("func_def");
		Lexer copy = lex.copy();
		result.appendResult(type());
		if ( result.result != Result.Res.SUCCESS ) {
			lex = copy;
			return result;
		}
		if ( lex.curToken != Token.TokenName.T_ID ) {
			result.setError("This token (" + lex.curStr + ") not expected here, T_ID should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		
		if ( lex.curToken != Token.TokenName.T_OS ) {
			result.setError("This token (" + lex.curStr + ") not expected here, T_OS should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		
		result.appendResult(arg_decl_list());
		if ( result.result != Result.Res.SUCCESS ) {
			lex = copy;
			return result;
		}
		
		if ( lex.curToken != Token.TokenName.T_CS ) {
			//Panic
			result.setError("This token (" + lex.curStr + ") not expected here, T_CS should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		result.appendResult(statements_block());
		return result;
	}
	
	Result arg_decl_list() throws ParseException {
		Result result = new Result("arg_decl_list");
		if ( lex.curToken == Token.TokenName.T_CS ) {
			return result;
		}
		result.appendResult(arg_decl());
		if ( result.result == Result.Res.FAIL ) {
			return result;
		}
		
		if ( lex.curToken == Token.TokenName.T_COMMON ) {
			addToResultAndNext(result, lex);
			return arg_decl_list();
		}
		return result;
	}
	
	Result arg_decl() throws ParseException {
		Result result = new Result("arg_decl");
		result.appendResult(type());
		if ( result.result == Result.Res.FAIL ) {
			return result;
		}
		if ( lex.curToken == Token.TokenName.T_ID ) {
			addToResultAndNext(result, lex);
		}
		return result;
	}
	
	Result statement_or_block() throws ParseException {
		Result result = new Result("statement_or_block");
		if ( lex.curToken == Token.TokenName.T_FOS ) {
			result.appendResult(statements_block());
		}else {
			result.appendResult(statement());
		}
		return result;
	}
	Result statements_block() throws ParseException {
		Result result = new Result("statements_block");
		Lexer copy = lex.copy();
		if ( lex.curToken != Token.TokenName.T_FOS ) {
			result.setError("This token (" + lex.curStr + ") not expected here, { should be here\n", lex.getPosInString(), lex.curStringNumber);
			return result;
		}
		
		identifiers.add(new HashMap<String, Types>());
		
		addToResultAndNext(result, lex);
		result.appendResult(statements_list());
		if (result.result != Result.Res.SUCCESS) {
			lex = copy;
			skipWhileCountOne(Token.TokenName.T_FOS, Token.TokenName.T_FCS);
			//return result;
		}
		
		identifiers.remove(identifiers.size()-1);
		if ( lex.curToken != Token.TokenName.T_FCS ) {
			result.setError("This token (" + lex.curStr + ") not expected here, } should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		
		addToResultAndNext(result, lex);
		
		return result;
	}
	Result statements_list() throws ParseException {
		Result result = new Result("statements_list");
		result.appendResult(statement());
		if ( result.result != Result.Res.SUCCESS ) {
			Set<Token.TokenName> set = new HashSet<Token.TokenName>();
			set.add(Token.TokenName.T_DC);
			set.add(Token.TokenName.T_FCS);
			skipWhile(set);
			result.addChildren("FAILED");
			
			if ( lex.curToken == Token.TokenName.T_FCS || lex.curToken == Token.TokenName.END) {
				return result;
			}
			result.result = Result.Res.SUCCESS;
			addToResultAndNext(result, lex);
		}
		Result list_result = statements_list();
		if ( list_result.result == Result.Res.SUCCESS ) {
			result.appendResult(list_result);
		}
		return result;
	}
	Result statement() throws ParseException {
		Result result = new Result("statement");
		switch (lex.curToken) {
		case T_IF: {
			result.appendResult(if_stmt());
		}break;
		case T_WHILE: {
			result.appendResult(while_stmt());
		}break;
		case T_RETURN: {
			result.appendResult(return_stmt());
		}break;
		case T_ID: {
			Lexer copy = lex.copy();
			addToResultAndNext(result, lex);
			if ( lex.curToken == Token.TokenName.T_ASSIGN ) {
				addToResultAndNext(result, lex);
				result.appendResult(expression());
				if ( result.result == Result.Res.FAIL ) {
					lex = copy;
					return result;
				}
				if ( lex.curToken != Token.TokenName.T_DC ) {
					result.setError("This token (" + lex.curStr + ") not expected here, ; should be here\n", lex.getPosInString(), lex.curStringNumber);
					lex = copy;
					return result;
				}
				addToResultAndNext(result, lex);
				return result;
			}else {
				lex = copy;
			}
			result.appendResult(var_def());
		}break;
		default: {
			result.setError("This token (" + lex.curStr + ") not expected here\n", lex.getPosInString(), lex.curStringNumber);
		}
		}
		return result;
	}
	Result while_stmt() throws ParseException {
		Result result = new Result("while_stmt");
		Lexer copy = lex.copy();
		if ( lex.curToken != Token.TokenName.T_WHILE ) {
			result.setError("This token (" + lex.curStr + ") not expected here, while should be here\n", lex.getPosInString(), lex.curStringNumber);
			return result;
		}
		addToResultAndNext(result, lex);
		if ( lex.curToken != Token.TokenName.T_OS ) {
			result.setError("This token (" + lex.curStr + ") not expected here, ( should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		result.appendResult(expression());
		if ( result.result == Result.Res.FAIL ) {
			lex = copy;
			return result;
		}
		if ( lex.curToken != Token.TokenName.T_CS ) {
			result.setError("This token (" + lex.curStr + ") not expected here, ) should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		result.appendResult(statement_or_block());
		if ( result.result == Result.Res.FAIL ) {
			lex = copy;
			return result;
		}
		return result;
	}
	Result if_stmt() throws ParseException {
		Result result = new Result("if_stmt");
		Lexer copy = lex.copy();
		if ( lex.curToken != Token.TokenName.T_IF ) {
			result.setError("This token (" + lex.curStr + ") not expected here, if should be here\n", lex.getPosInString(), lex.curStringNumber);
			return result;
		}
		addToResultAndNext(result, lex);
		if ( lex.curToken != Token.TokenName.T_OS ) {
			result.setError("This token (" + lex.curStr + ") not expected here, ( should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		result.appendResult(expression());
		if ( result.result == Result.Res.FAIL ) {
			lex = copy;
			return result;
		}
		if ( lex.curToken != Token.TokenName.T_CS ) {
			result.setError("This token (" + lex.curStr + ") not expected here, ) should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		result.appendResult(statement_or_block());
		if ( result.result == Result.Res.FAIL ) {
			lex = copy;
			return result;
		}
		if ( lex.curToken != Token.TokenName.T_ELSE )
			return result;
		addToResultAndNext(result, lex);
		result.appendResult(statement_or_block());
		if ( result.result == Result.Res.FAIL ) {
			lex = copy;
			return result;
		}
		return result;
	}
	Result return_stmt() throws ParseException {
		Result result = new Result("return_stmt");
		Lexer copy = lex.copy();
		if ( lex.curToken != Token.TokenName.T_RETURN ) {
			result.setError("This token (" + lex.curStr + ") not expected here, return should be here\n", lex.getPosInString(), lex.curStringNumber);
			return result;
		}
		addToResultAndNext(result, lex);
		result.appendResult(expression());
		if ( result.result == Result.Res.FAIL ) {
			lex = copy;
			return result;
		}
		if ( lex.curToken != Token.TokenName.T_DC ) {
			result.setError("This token (" + lex.curStr + ") not expected here, ; should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		return result;
	}
	Result expression() throws ParseException {
		Result result = new Result("expression");
		Lexer copy = lex.copy();
		if ( lex.curToken == Token.TokenName.T_OS ) {
			addToResultAndNext(result, lex);
			result.appendResult(expression());
			if ( result.result == Result.Res.FAIL ) {
				lex = copy;
				return result;
			}
			if ( lex.curToken != Token.TokenName.T_CS ) {
				result.setError("This token (" + lex.curStr + ") not expected here, ) should be here\n", lex.getPosInString(), lex.curStringNumber);
				lex = copy;
				return result;
			}
			addToResultAndNext(result, copy);
			return result;
		}
		
		result.appendResult(expr());
		if ( result.result == Result.Res.FAIL ) {
			lex = copy;
			return result;
		}
		
		Result oper_result = operator();
		if ( oper_result.result == Result.Res.SUCCESS ) {
			result.appendResult(oper_result);
			Result expressionResult = expression();
			if ( expressionResult.result != Result.Res.SUCCESS ) {
				lex = copy;
			}
			result.appendResult(expressionResult);
		}
		return result;
	}
	Result expr() throws ParseException {
		Result result = new Result("expr");
		
		Result funcResult = function_call();
		if ( funcResult.result == Result.Res.SUCCESS ) {
			result.appendResult(funcResult);
			return result;
		}
		
		Result varResult = var_access();
		if ( varResult.result == Result.Res.SUCCESS ) {
			result.appendResult(varResult);
			return result;
		}
		
		Result numResult = number();
		if ( numResult.result == Result.Res.SUCCESS ) {
			result.appendResult(numResult);
			return result;
		}
		result.setError("This token (" + lex.curStr + ") not expected here, T_ID or VALUE should be here\n", lex.getPosInString(), lex.curStringNumber);
		return result;
	}
	Result operator() throws ParseException {
		Result result = new Result("operator");
		
		switch (lex.curToken) {
		case T_PLUS:
		case T_MINUS:
		case T_MUL:
		case T_DIV:
		{
			addToResultAndNext(result, lex);
		}break;
		default:
			result.setError("Unknown operator (" + lex.curStr + ")\n", lex.getPosInString(), lex.curStringNumber);
		}
		
		return result;
	}
	Result var_access() throws ParseException {
		Result result = new Result("var_access");
		if ( lex.curToken != Token.TokenName.T_ID ) {
			result.setError("This token (" + lex.curStr + ") not expected here, T_ID should be here\n", lex.getPosInString(), lex.curStringNumber);
			return result;
		}
		addToResultAndNext(result, lex);
		return result;
	}
	Result number() throws ParseException {
		Result result = new Result("number");
		switch (lex.curToken) {
		case T_INTVALUE: {
			addToResultAndNext(result, lex);
			//result.tree.children.add(new Tree(lex.curStr));
		}break;
		case T_STRING: {
			addToResultAndNext(result, lex);
			//result.tree.children.add(new Tree(lex.curStr));
		}break;
		default:
			result.setError("This token (" + lex.curStr + ") not expected here, T_INTVALUE or T_STRING should be here\n", lex.getPosInString(), lex.curStringNumber);
		}
		return result;
	}
	Result function_call() throws ParseException {
		Result result = new Result("function_call");
		Lexer copy = lex.copy();
		if ( lex.curToken != Token.TokenName.T_ID ) {
			result.setError("This token (" + lex.curStr + ") not expected here, T_ID should be here\n", lex.getPosInString(), lex.curStringNumber);
			return result;
		}
		addToResultAndNext(result, lex);
		if ( lex.curToken != Token.TokenName.T_OS ) {
			result.setError("This token (" + lex.curStr + ") not expected here, ( should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		
		result.appendResult(arg_list());
		if ( result.result == Result.Res.FAIL ) {
			lex = copy;
			return result;
		}
		
		if ( lex.curToken != Token.TokenName.T_CS ) {
			result.setError("This token (" + lex.curStr + ") not expected here, ( should be here\n", lex.getPosInString(), lex.curStringNumber);
			lex = copy;
			return result;
		}
		addToResultAndNext(result, lex);
		return result;
	}
	Result arg_list() throws ParseException {
		Result result = new Result("arg_list");
		result.appendResult(expression());
		if ( result.result == Result.Res.FAIL ) {
			return result;
		}
		if ( lex.curToken == Token.TokenName.T_COMMON ) {
			addToResultAndNext(result, lex);
			result.appendResult(arg_list());
		}
		return result;
	}
}
