@Global
@Lexer
	INT:'0'..'9'+
	ID:'a'..'z'+
	PLUS:\+
	MINUS:-
@Parser
	S("a");
	[int value]S(String s)-> PLUS S S {System.out.println(s);
	 $value = $2.value + $3.value; &2.s = "2"; &3.s = "1";}
	| MINUS S S {$value = $2.value - $3.value; &2.s = "2"; &3.s = "1";}
	| VALUE {$value = $1.value;}
	VALUE-> INT {$value = Integer.parseInt( $1.str);}
	| ID