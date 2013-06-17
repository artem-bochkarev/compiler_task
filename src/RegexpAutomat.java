import java.text.ParseException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RegexpAutomat {
	
	private enum LastOperation {
		OpenScope, CloseScope, IF, PLUS, MUL, none
	}

	public Automat start;
	public ArrayList<Automat> end = new ArrayList<Automat>(4);
	Automat curAutomat, prevAutomat;

	private boolean isSpecial(char c) {
		if ((c == '|')||(c == '+')||(c =='*')||(c == '(')||(c == ')'))
			return true;
		return false;
	}
	
	private int findMyCloseScope(String s, int k) {
		ArrayList<Integer> stack = new ArrayList<Integer>();
		for (int i=0; i<s.length(); ++i) {
			if (s.charAt(i) == '(') {
				stack.add(1);
			}
			if (s.charAt(i) == ')') {
				stack.remove(stack.size()-1);
				if (stack.size() == 0) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public RegexpAutomat(String s) throws ParseException {
		int k = s.indexOf('(');
		String s2 = s.substring(0, k);
		if (k < 0) {
			s2 = s;
		}
		start = new Automat(s2);
		ArrayList<RegexpAutomat> lst = new ArrayList<RegexpAutomat>();
		ArrayList<RegexpAutomat> prevLst = null;
		if (k > 0) {
			int z = findMyCloseScope(s, k);
			RegexpAutomat reg = new RegexpAutomat(s.substring(k, z));
			switch (s.charAt(z+1)) {
				case '+': {
					for (Automat a : reg.end) {
						for (AutomatState as : a.end) {
							as.next.addAll(a.start.next);
							as.rules.addAll(a.start.rules);
							as.minCounter = 1;
						}
					}
				}break;
				case '*': {
					for (Automat a : reg.end) {
						for (AutomatState as : a.end) {
							as.next.addAll(a.start.next);
							as.rules.addAll(a.start.rules);
							as.minCounter = 1;
						}
					}
					lst = prevLst;
				}break;
				case '|': {
					lst.add(reg);
				}break;
			};
		}
		
	}
	
	public boolean next(char c) {
		return true;
	}

}
