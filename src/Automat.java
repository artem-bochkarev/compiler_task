import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Automat {

	private enum Operation {
		OpenScope, CloseScope, IF, PLUS, MUL, none
	}
	
	private class Record {
		public Record(Operation operation, ArrayList<AutomatState> inputStates) {
			this.operation = operation;
			this.inputStates = inputStates;
		}
		public Operation operation;
		public ArrayList<AutomatState> inputStates;
		public ArrayList<AutomatState> beginStates;
	}

	public AutomatState start;
	public ArrayList<AutomatState> end = new ArrayList<AutomatState>(4);
	AutomatState curState, prevState;

	private boolean isSpecial(char c) {
		if ((c == '|')||(c == '+')||(c =='*')||(c == '\\')||(c == '['))
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public Automat(String s) throws ParseException {
		start = new AutomatState();
		curState = start;
		prevState = null;
		int i = 0;
		Operation operation = Operation.none;
		ArrayList<AutomatState> stateList = new ArrayList<AutomatState>();
		ArrayList<AutomatState> prevStates = new ArrayList<AutomatState>();
		List<Record> stek = new LinkedList<Record>();
		boolean wasSpec = false;
		stateList.add(start);
		while (i < s.length()) {

			Rule currentRule = null;
			if ((s.charAt(i)=='[')&&(wasSpec == false)) {
				//[a..z] construction
				if (s.charAt(i+2) == '.' && s.charAt(i+3) == '.') {
					char c1 = s.charAt(i+1);
					char c2 = s.charAt(i+4);
					currentRule = new Rule(c1, c2);
					i += 6;
				}else {
					int k = s.indexOf(']', i);
					currentRule = new Rule(s.substring(i+1, k));
					i = k+1;
				}
			}else if ( (!isSpecial(s.charAt(i))) || (wasSpec == true) ) {
				//simple character
				currentRule = new Rule(s.charAt(i));
				++i;
				wasSpec = false;
			}
			
			if ( currentRule != null ) {
				AutomatState temp = new AutomatState();
				if (operation != Operation.IF) {
					
					if ( stek.size() > 0 ) {
						if (stek.get(stek.size()-1).operation == Operation.none) {
							//closure();
							//nextForAllList(main, states)
						}else if (stek.get(stek.size()-1).operation == Operation.IF) {
							if ( stek.size() > 1 ) {
								//closure
							}else {
								//closure with start
							}
						}
					}
					
					prevStates = (ArrayList<AutomatState>) stateList.clone();
					stateList.clear();
				}
				nextForAll(prevStates, currentRule, temp);
				stateList.add(temp);
				
				curState = temp;
				operation = Operation.none;
				continue;
			}
			
			switch (s.charAt(i)) {
			case '|': {
				operation = Operation.IF;
			}break;
			case '*': {
				if ( operation == Operation.none ) {
					curState.addNext(currentRule, curState);
					//ToDo null times
				}else if ( operation == Operation.CloseScope ) {
					//closure
				}
				operation = Operation.MUL;
			}break;
			case '+': {
				if ( operation == Operation.none ) {
					curState.addNext(currentRule, curState);
					curState.minCounter = 1;
				}else if ( operation == Operation.CloseScope ) {
					//closure
				}
				operation = Operation.PLUS;
			}break;
			case '\\': {
				wasSpec = true;
			}break;
			case '(': {
				Record rec = new Record(operation, stateList);
				stek.add(rec);
				operation = Operation.OpenScope;
			}break;
			case ')': {
				operation = Operation.CloseScope;
				stek.remove(stek.size()-1);
			}break;
			}
			;
			++i;
			continue;
		}
		for (AutomatState as : stateList) {
			as.end = true;
			end.add(as);
		}
		curState.end = true;
		end.add(curState);
		
		curState = start;
	}
	
	public void restart() {
		curState = start;
	}
	
	public void nextForAll( List<AutomatState> list, Rule rule, AutomatState state) {
		for (AutomatState st : list) {
			st.addNext(rule, state);
		}
	}
	
	public void nextForAllList( List<AutomatState> main, List<AutomatState> states ) {
		for (AutomatState m : main) {
			for ( AutomatState s : states ) {
				m.next.addAll(s.next);
				m.rules.addAll(s.rules);
			}
		}
	}
	
	public void closure( List<AutomatState> list) {
		for (AutomatState st : list) {
			for ( AutomatState s : list ) {
				//st.
			}
		}
	}
	
	public int next(char c) {
		if (curState == null) {
			return 1;
		}
		curState = curState.getNext(c);
		if (curState == null) {
			return -1;
		}else if ((curState.isEnd() == true)) {//&&(curState.counter >= curState.minCounter)) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public boolean match(String s) {
		for (AutomatState as : end) {
			as.counter = 0;
		}
		AutomatState current = start;
		current.counter++;
		for (int i=0; i<s.length(); ++i) {
			AutomatState tmp = current.getNext(s.charAt(i));
			if (tmp == null) {
				return false;
			}else {
				current = tmp;
				current.counter++;
			}
		}
		if ((current.isEnd())&&(current.counter >= current.minCounter)) {
			return true;
		}else {
			return false;
		}
	}
	
}
