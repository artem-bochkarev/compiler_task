public class Token { 
	public TokenName name;
	public String str;
	public Token(TokenName tName, String str) {
		this.str = str;
		name = tName;
	}
	public enum TokenName {
		END, T_DC, T_INT, T_FLOAT, T_VOID, T_INTVALUE, T_ASSIGN, T_OS, T_CS, T_FOS, T_FCS, T_SOS, T_SCS, T_IF, T_ELSE, T_WHILE, T_RETURN, T_PLUS, T_MINUS, T_MUL, T_DIV, T_COMMON, T_ID, T_STRING
	}
}