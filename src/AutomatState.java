import java.util.ArrayList;
import java.util.Iterator;

public class AutomatState {
	public AutomatState() {
		end = false;
		counter = 0;
		minCounter = 0;
	}
	public void addNext(Rule rule, AutomatState state) {
		rules.add(rule);
		next.add(state);
	}
	public AutomatState getNext(char c) {
		Iterator<AutomatState> st = next.iterator();
		Iterator<Rule> rl = rules.iterator();
		while (rl.hasNext()) {
			if (rl.next().apply(c)) {
				return st.next();
			}else {
				st.next();
			}
		}
		return null;
	}
	public boolean isEnd() {
		return end;
	}
	public ArrayList<AutomatState> next = new ArrayList<AutomatState>(4);
	public ArrayList<Rule> rules = new ArrayList<Rule>(4);
	public int counter;
	public int minCounter;
	public boolean end;
}

