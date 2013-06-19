@Lexer
	T_DC:;
	T_INTVALUE:\d+
	T_ASSIGN:=
	
	T_OS:\(
	T_CS:\)
	T_FOS:\{
	T_FCS:\}
	T_SOS:\[
	T_SCS:\]
	
	T_IF:if
	T_ELSE:else
	T_WHILE:while
	T_RETURN:return
	
	T_PLUS:\+
	T_MINUS:\-
	T_MUL:\*
	T_DIV:\\
	
	T_COMMON:\s*,\s*
	T_ID:[a-zA-Z]\w*
	T_STRING:("")|(".*?[^\\]")
@Global
	HashSet<String> variables = new HashSet<String>();
@Parser
	S();
	type    -> T_ID
	S       -> program
	
	program           -> global_list
	global_list       -> global_statement global_list | global_statement
	global_statement  -> var_def | func_def
	var_def           -> type var_init_list T_DC
	var_init_list     -> var_init T_COMMON var_init_list | var_init
	var_init          -> T_ID T_ASSIGN expression | T_ID T_SOS expression T_SCS T_ASSIGN expression | T_ID T_SOS expression T_SCS | T_ID T_SOS T_SCS | T_ID T_OS expression T_CS | T_ID
	func_def          -> type T_ID T_OS arg_decl_list  T_CS statements_block
	arg_decl_list     -> arg_decl T_COMMON arg_decl_list | arg_decl | eps
	arg_decl          -> type T_ID | type | eps
	
	statement_or_block-> statements_block | statement
	statements_block  -> T_FOS statements_list T_FCS
	statements_list   -> statement statements_list | statement
	statement         -> var_access T_ASSIGN expression T_DC | if_stmt | while_stmt | return_stmt | var_def T_DC
#	statement         -> var_init
	
	while_stmt        -> T_WHILE T_OS expression T_CS statement_or_block
	if_stmt           -> T_IF T_OS expression T_CS statement_or_block T_ELSE statement_or_block | T_IF T_OS expression T_CS statement_or_block
	return_stmt       -> T_RETURN expression T_DC
	expression        -> T_OS expression T_CS | expr operator expression | expr
	expr              -> function_call | number | var_access
	operator          -> T_PLUS | T_MINUS | T_MUL | T_DIV
	var_access        -> T_ID
	number            -> T_INTVALUE | T_STRING
	function_call     -> T_ID T_OS arg_list T_CS
	arg_list          -> expression T_COMMON expression | expression
	