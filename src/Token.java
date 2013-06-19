import java.util.Map;
import java.util.HashMap;

public class Token { 
	public TokenName name;
	public String str;
	public Map<TokenName, String> names;
	public Token(TokenName tName, String str) {
		this.str = str;
		names = new HashMap<TokenName, String>();
		name = tName;
		init();
	}
	public enum TokenName {
		END, T_DC, T_INTVALUE, T_ASSIGN, T_OS, T_CS, T_FOS, T_FCS, T_SOS, T_SCS, T_IF, T_ELSE, T_WHILE, T_RETURN, T_PLUS, T_MINUS, T_MUL, T_DIV, T_COMMON, T_ID, T_STRING
	}
	private void init() {
		names.put( TokenName.END, "END" );
		names.put( TokenName.T_DC, "T_DC" );
		names.put( TokenName.T_INTVALUE, "T_INTVALUE" );
		names.put( TokenName.T_ASSIGN, "T_ASSIGN" );
		names.put( TokenName.T_OS, "T_OS" );
		names.put( TokenName.T_CS, "T_CS" );
		names.put( TokenName.T_FOS, "T_FOS" );
		names.put( TokenName.T_FCS, "T_FCS" );
		names.put( TokenName.T_SOS, "T_SOS" );
		names.put( TokenName.T_SCS, "T_SCS" );
		names.put( TokenName.T_IF, "T_IF" );
		names.put( TokenName.T_ELSE, "T_ELSE" );
		names.put( TokenName.T_WHILE, "T_WHILE" );
		names.put( TokenName.T_RETURN, "T_RETURN" );
		names.put( TokenName.T_PLUS, "T_PLUS" );
		names.put( TokenName.T_MINUS, "T_MINUS" );
		names.put( TokenName.T_MUL, "T_MUL" );
		names.put( TokenName.T_DIV, "T_DIV" );
		names.put( TokenName.T_COMMON, "T_COMMON" );
		names.put( TokenName.T_ID, "T_ID" );
		names.put( TokenName.T_STRING, "T_STRING" );
	}

	public String getName(TokenName name) {
		return names.get(name);
	}
}
