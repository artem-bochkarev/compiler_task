import java.text.ParseException;
import java.util.*;

import javax.management.RuntimeErrorException;
public class ParserHandMade {
	Lexer lex;
	ParseException globalException;
	
	public enum Terminal {
		program, global_list, global_statement, var_def, var_init_list, var_init, func_def, arg_decl_list, arg_decl,
		statement_or_block, statements_block, statements_list, statement, while_stmt, if_stmt, return_stmt,	expression,
		expr, operator, var_access, number, function_call, arg_list, type, S
	}
	
	public enum Types {
		INT, FLOAT, CHAR
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
			resSet.add   (Token.TokenName.T_INT);
			resSet.add   (Token.TokenName.T_FLOAT);
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
		while ( (lex.curToken != Token.TokenName.END) && (lex.curToken().name != Token.TokenName.T_DC) ) {
			lex.nextToken();
		}
	}
	
	void skipWhile(Set<Token.TokenName> set) throws ParseException {
		while ( (lex.curToken != Token.TokenName.END) && (set.contains( lex.curToken() ) == false) ) {
			lex.nextToken();
		}
	}
	
	
	Tree parse(String str) throws ParseException {
		lex = new Lexer(str);
		lex.nextToken();
		//auto-generated
		Result result = S();
		if (lex.curToken().name != Token.TokenName.END) {
			throw globalException;
		}
		return result.tree;
	}
		HashSet<String> variables = new HashSet<String>();

	Result type() throws ParseException {
		Result result = new Result();
		Lexer copy = lex.copy();
		
		String currentType = lex.curStr;
		if ( typesMap.containsKey(currentType) == false ) {
			//error unknown type
			result.setError("Unknown type: " + currentType + '\n', lex.curPos, lex.curStringNumber);
			lex = copy;
			return result;
		}
		result.result = Result.Res.SUCCESS;
		result.tree = new Tree("type", new Tree(currentType));
		return result;
	}
	Result S() throws ParseException {
		return program();
	}
	Result program() throws ParseException {
		return global_list();
		
	}
	Result global_list() throws ParseException {
		Result result = new Result();
		result.result = Result.Res.SUCCESS;
		
		Set<Token.TokenName> tokensSet = getNextTokenVariants(Terminal.global_list);
		if ( tokensSet.contains(lex.curToken()) == false ) {
			//error unknown symbol...PANIC!!!
			result.setError( "This token not expected here: " + lex.curStr + '\n', lex.curPos, lex.curStringNumber );
			//change to figure scope
			skipWhile(tokensSet);
		}else {
			//normal
			result = global_statement();
		}
		if ( lex.curToken != Token.TokenName.END ) {
			Result tmp = global_list();
			result.appendResult(tmp);
		}
		return result;
	}
	Result global_statement() throws ParseException {
		Result result = new Result();
		result.result = Result.Res.SUCCESS;
		Set<Token.TokenName> tokensSet = getNextTokenVariants(Terminal.global_statement);
		if (tokensSet.contains(lex.curToken()) == false) {
			result.setError("This token not expected here: " + lex.curStr + '\n', lex.curPos, lex.curStringNumber);
			return result;
		}else {
			Lexer copy = lex.copy();
			Result resultVAR_DEF = var_def();
			if ( resultVAR_DEF.result == Result.Res.SUCCESS ) {
				return resultVAR_DEF;
			}
			lex = copy;
			copy = lex.copy();
			Result resultFUNC_DEF = func_def();
			if ( resultFUNC_DEF.result == Result.Res.SUCCESS ) {
				return resultFUNC_DEF;
			}
			lex = copy;
			result = Result.chooseResult(resultVAR_DEF, resultFUNC_DEF);
		}
		return result;
	}
	Result var_def() throws ParseException {
		Result result = new Result();
		result.result = Result.Res.SUCCESS;
		Set<Token.TokenName> tokensSet = getNextTokenVariants(Terminal.global_statement);
		if (tokensSet.contains(lex.curToken()) == false) {
			//Panic...;
			result.setError("This token not expected here: " + lex.curStr + '\n', lex.curPos, lex.curStringNumber);
			skipWhile(Token.TokenName.T_DC);
			lex.nextToken();
			return result;
		}else {
			result = var_init_list();
			if ( result.result == Result.Res.FAIL ) {
				skipWhile(Token.TokenName.T_DC);
				lex.nextToken();
				return result;
			}
			if ( lex.curToken().name != Token.TokenName.T_DC ) {
				result.setError("This token (" + lex.curStr + ") not expected here, ; should be here\n", lex.curPos, lex.curStringNumber);
				skipWhile(Token.TokenName.T_DC);
				lex.nextToken();
			}
		}
		
		return result;
	}
	Result var_init_list() throws ParseException {
		Result result = new Result();
		result = var_init();
		while ( result.result == Result.Res.SUCCESS && lex.curToken().name == Token.TokenName.T_COMMON ) {
			lex.nextToken();
			result.appendResult(var_init());
		}
		return result;
	}
	Result var_init() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result func_def() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result arg_decl_list() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result arg_decl() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result statement_or_block() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result statements_block() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result statements_list() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result statement() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result while_stmt() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result if_stmt() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result return_stmt() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result expression() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result expr() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result operator() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result var_access() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result number() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result function_call() throws ParseException {
		Result result = new Result();
		return result;
	}
	Result arg_list() throws ParseException {
		Result result = new Result();
		return result;
	}
}
