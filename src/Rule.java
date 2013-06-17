import java.util.HashSet;
import java.util.List;

public class Rule {
	private char c1, c2;
	private HashSet<Character> hash;
	private enum Type {
		INTERVAL, LIST
	}
	private Type type;
	public Rule(char c1, char c2) {
		type = Type.INTERVAL;
		this.c1 = c1;
		this.c2 = c2;
	}
	
	public Rule(char c) {
		type = Type.INTERVAL;
		this.c1 = c;
		this.c2 = c;
	}
	
	public Rule( List<Character> chars ) {
		type = Type.LIST;
		hash = new HashSet<Character>();
		hash.addAll(chars);
	}
	
	public Rule( String str ) {
		type = Type.LIST;
		hash = new HashSet<Character>();
		for ( int i=0; i<str.length(); ++i ) {
			hash.add(str.charAt(i));
		}
	}

	public boolean apply(char c) {
		if ( type == Type.INTERVAL ) {
			if ((c >= c1)&&(c <= c2))
				return true;
			else
				return false;
		}else {
			return hash.contains(c);
		}
	}
}
