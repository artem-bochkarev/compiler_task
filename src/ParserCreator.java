import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;


public class ParserCreator {
	
	static HashSet<String> parserNames = new HashSet<String>();
	static HashMap<String, String> arguments= new HashMap<String, String>();
	static ArrayList<String> varsToSave = new ArrayList<String>();
	static ArrayList<String> typesToSave = new ArrayList<String>();
	
	static int cnt = 0;
	
	public static void apply(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		BufferedReader parserExample = new BufferedReader(new FileReader("ParserExample.java"));
		PrintWriter parserOut = new PrintWriter(new FileWriter("src\\Parser.java"));
		String s = parserExample.readLine();
		while (s.indexOf("//auto-generated") < 0) {
			parserOut.println(s);
			s = parserExample.readLine();
		}
		parserOut.println(s);
		
		s = br.readLine();
		String variables = "";
		while ((s.charAt(0) != '@')||(s.charAt(1) != 'G')) {
			s = br.readLine();
		}
		s = br.readLine();
		while ((s.charAt(0) != '@')||(s.charAt(1) != 'P')) {
			variables += "\t"+s+"\n";
			s = br.readLine();
		}
		s = br.readLine();
		String start = s;
		s = br.readLine();
		ArrayList<String> rules = new ArrayList<String>();
		while (s.charAt(0) != '@') {
			//s = removeWS(s);
			String tmp = s;
			s = "";
			while (s.indexOf("->") < 0) {
				if (br.ready()) {
					s = br.readLine();
				}else break;
			}
			if ( tmp.length() > 0 && tmp.charAt(0) != '#' )
				rules.add(tmp);
			if (!br.ready()) {
				break;
			}
		}
		
		if (s.indexOf("->") > 0 && s.charAt(0) != '#') {
			rules.add(s);
		}
		
		for (String str : rules) {
			addParseName(str);
			arguments.put(cutName(str), cutArgs(str));
		}
		
		parserOut.println("\t\tTree result = "+start);
		parserOut.println("\t\tif (lex.curToken().name != Token.TokenName.END) {\n" +
			//"\t\t\tthrow new ParseException(\" $  expected + " +
			//		"at position\", lex.curPos());\n" +
			"\t\t\tthrow globalException;\n" +
			"\t\t}\n" +
			"\t\treturn result;\n"+
			"\t}");
		
		toSave(variables);
		parserOut.println(variables);
		
		for (String str : rules) {
			parserOut.print(printRule(str));
		}
		parserOut.print("}\n");
		parserOut.close();
		br.close();
		parserExample.close();
	}
	
	private static void toSave(String s) {
		StringTokenizer st = new StringTokenizer(s, "\n;");
		while (st.hasMoreTokens()) {
			String t = st.nextToken();
			String type = "";
			String name = "";
			if (t.indexOf('=') > 0) {
				t = t.substring(0, t.indexOf('='));
			}
			StringTokenizer q = new StringTokenizer(t);
			while (q.hasMoreTokens()) {
				String temp = q.nextToken();
				if (q.hasMoreTokens()) {
					type += temp;
				}else {
					name = temp;
				}
			}
			varsToSave.add(name);
			typesToSave.add(type);
		}
	}
	
	private static void addParseName(String s) {
		parserNames.add(cutName(s));
	}
	
	private static String cutName(String s) {
		int sc = s.indexOf(']');
		int rl = s.indexOf("->");
		int scp = s.indexOf('(');
		String name;
		if ((sc > 0)&&(rl > sc)) {
			name = removeWS(s.substring(sc+1, rl));
		}else {
			name = removeWS(s.substring(0, rl));
		}
		if ((scp>0)&&(scp < rl)) {
			name = name.substring(0, name.indexOf('('));
		}
		return name;
	}
	
	private static String removeWS(String s) {
		String tmp = "";
		for (int i=0; i<s.length(); ++i) {
			char c = s.charAt(i);
			if ((c == ' ') || (c == '\t')||(c == '\\')) {
				if ((i > 0)&&(s.charAt(i-1) == '\\')) {
					tmp += c;
				}
			}else {
				tmp += c;
			}
		}
		return tmp;
	}
	
	private static String cutArgs(String s) {
		String tmp = "";
		int rl = s.indexOf("->");
		int scp = s.indexOf('(');
		if ((scp > 0)&&(scp < rl)) {
			tmp = s.substring(scp+1, s.indexOf(')'));
		}
		return tmp;
	}
	
	private static String printRule(String pattern) {
		String tmp = "";
		String name = cutName(pattern);
		String args = cutArgs(pattern);
		
		String rules = pattern.substring(pattern.indexOf("->")+2);
		tmp += "\tTree " + name + "("+args+") throws ParseException {\n";
		tmp += "\t\tboolean isGood = true;\n";
		tmp += "\t\tLexer copy = null;\n";
		tmp += "\t\tTree result = null;\n";
		tmp += "\t\tString value = null;\n";
		tmp += "\t\tParseException p = new ParseException(\"\", 0);\n";
		for (int i=0; i<varsToSave.size(); ++i) {
			tmp += "\t\t"+typesToSave.get(i)+" TEMP_"+varsToSave.get(i)+" = null;\n";
		}
		ArrayList<String> pts1 = new ArrayList<String>();
		ArrayList<String> pts2 = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(rules, "|");
		while (st.hasMoreTokens()) {
			String str = st.nextToken();
			StringTokenizer temp = new StringTokenizer(str);
			String t = temp.nextToken();
			if (parserNames.contains(t)) {
				pts2.add(str);
			}else {
				pts1.add(str);
			}
		}
		tmp += tryRulesTerminal(pts1, name, args);
		
		for (String str : pts2) {
			String res = tryRuleNonTerminal(str, name, cnt++, args);
			tmp += res;
		}
		tmp += "\t\tglobalException = p;\n";
		tmp += "\t\tthrow p;\n";
		tmp += "\t}\n";
		return tmp;
	}
	
	private static String tryRulesTerminal(ArrayList<String> rules, String name, String args) {
		String tmp = "";
		for (String s : rules) {
			tmp += tryRuleNonTerminal(s, name, cnt++, args);
		}
		return tmp;
	}
	
	private static String tryRuleNonTerminal(String rule, String name, int number, String args) {
		String tmp = "";
		
		int k = rule.indexOf('{');
		String expr = "";
		if (k > 0) {
			int z = rule.lastIndexOf('}');
			expr = rule.substring(k+1, z);
			rule = rule.substring(0, k);
		}
		StringTokenizer st = new StringTokenizer(rule);
		ArrayList<String> tokens = new ArrayList<String>(st.countTokens());
		while (st.hasMoreTokens()) {
			tokens.add(st.nextToken());
		}
		
		int counter = 0;
		String variables = "";
		ArrayList<String> resultNames = new ArrayList<String>();
		tmp += "\t\tcopy = lex.copy();\n";
		for (int i=0; i<varsToSave.size(); ++i) {
			tmp += "\t\tTEMP_"+varsToSave.get(i)+
				" = ("+typesToSave.get(i)+") " + varsToSave.get(i)+".clone();\n";
		}
		tmp += "\t\tisGood = true;\n";
		tmp += "\t\tvalue = \""+name+"\";\n";
		tmp += "\t\ttry {\n";
		String result = "new Tree(\""+name+"\"";
		for (String str : tokens) {
			if (parserNames.contains(str)) {
				
				String argument = generateArguments(parseRuleForArguments(expr, counter), arguments.get(name));
				tmp += "\t\t\tsubTree_"+number+"_"+counter+" = "+str+"("+argument+");\n";
				
				
				variables += "\t\tTree subTree_"+number+"_"+counter+" = null;\n";
				resultNames.add(new String("subTree_"+number+"_"+counter));
				result += ", subTree_"+number+"_"+counter;
				++counter;
			}else {
				variables += "\t\tToken token_"+number+"_"+counter+" = null;\n";
				resultNames.add(new String("token_"+number+"_"+counter));
				tmp += "\t\t\tif (lex.curToken().name == Token.TokenName."+str+") {\n" +
						"\t\t\t\ttoken_"+number+"_"+counter+" = lex.curToken();\n" +
						"\t\t\t\tlex.nextToken();\n" +
						"\t\t\t}else {\n" +
						"\t\t\t\tthrow new ParseException(\"This token(\" + lex.curToken().str + \") not expected here\", lex.curPos );\n" +
						"\t\t\t}\n";
				result += ", new Tree(token_"+number+"_"+counter+".str)";
				++counter;
			}
		}
		
		result += ");\n";
		if (k > 0) {
			result += parseExpr(resultNames, expr);
		}
		result += "\t\t\treturn result;\n";
		tmp += "\t\t}catch(ParseException a) {\n" +
				"\t\t\tisGood = false;\n" +
				"\t\t\tlex = copy;\n" +
				"\t\t\tif ( a.getErrorOffset() > p.getErrorOffset() )\n" +
				"\t\t\t\tp = a;\n";
		for (int i=0; i<varsToSave.size(); ++i) {
			tmp += "\t\t\t"+varsToSave.get(i)+" = TEMP_"+varsToSave.get(i)+";\n";
		}
		tmp +=	"\t\t}\n";
		tmp += "\t\tif (isGood == true) {\n" +
				"\t\t\tresult = "+result +
				"\t\t}\n";
		tmp += "\n";
		return variables+tmp;
	}
	
	private static HashMap<String, String> parseRuleForArguments(String rule, int number) {
		String search = "&"+(number+1);
		String tmp = rule;
		int k = rule.indexOf(search);
		HashMap<String, String> result = new HashMap<String, String>();
		while (k >= 0) {
			tmp = tmp.substring(k+search.length()+1);
			StringTokenizer st = new StringTokenizer(tmp, " =;");
			String name = st.nextToken();
			String arg = st.nextToken();
			result.put(name, arg);
			k = tmp.indexOf(search);
		}
		return result;
	}
	
	private static String generateArguments(HashMap<String, String> hash, String args) {
		String result = "";
		StringTokenizer st = new StringTokenizer(args, " ,\t\n");
		while (st.hasMoreTokens()) {
			st.nextToken();
			String name = st.nextToken();
			if (st.hasMoreTokens()) {
				result += ", "+hash.get(name);
			}else {
				if (hash.containsKey(name)) {
					result += " "+hash.get(name);
				}
			}
		}
		return result;
	}
	
	private static String parseExpr(ArrayList<String> names, String expr) {
		String tmp = "";
		StringTokenizer st = new StringTokenizer(expr, " \n\t");
		tmp += "\t\t\t";
		while (st.hasMoreTokens()) {
			String s = st.nextToken(" \n\t");
			if (s.charAt(0)=='&') {
				st.nextToken(";");
				s = st.nextToken(" \n\t");
			}else if (s.charAt(0)=='$') {
				int k = s.indexOf('.');
				if (k < 0) {
					tmp += "result.value ";
				}else {
					String temp = s.substring(1, k);
					int i = Integer.parseInt(temp) - 1;
					String name = names.get(i);
					tmp += name+"."+s.substring(k+1, s.length())+" ";
				}
			}else {
				tmp += " "+s+" ";
			}
		}
		return tmp+'\n';
	}

}
