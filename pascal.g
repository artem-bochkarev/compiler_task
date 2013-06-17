@Lexer
	INTEGER:integer
	REAL:real
	STRING:string
	VAR:var
	DD::
	DC:;
	COMMON:\s*,\s*
	ID:[a-z]+
@Global
	HashSet<String> variables = new HashSet<String>();
@Parser
	S();
	NAMES-> ID COMMON NAMES {if (variables.contains( $1.str )) {
		throw new ParseException("Duplicate identifier \""+ $1.str+"\"", lex.curPos());
		}else {
			variables.add( $1.str );
		} 
	}
	| ID {if (variables.contains( $1.str )) {
		throw new ParseException("Duplicate identifier \""+ $1.str+"\"", lex.curPos());
		}else {
			variables.add( $1.str );
		} 
	}
	LINE-> NAMES DD TYPE DC
	TYPE-> INTEGER | REAL | STRING
	LINES-> LINE LINES | LINE
	S-> VAR LINES