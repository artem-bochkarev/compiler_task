import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;


public class TreeCreator {
	
	static HashSet<String> parserNames = new HashSet<String>();
	static int cnt = 0;
	
	public static void apply(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		BufferedReader parserImpl = new BufferedReader(new FileReader("TreeExample.java"));
		PrintWriter tree = new PrintWriter(new FileWriter("src\\Tree.java"));
		String s = parserImpl.readLine();
		while (s.indexOf("//auto-generated") < 0) {
			tree.println(s);
			s = parserImpl.readLine();
		}
		tree.println(s);
		
		s = br.readLine();
		while ((s.charAt(0) != '@')||(s.charAt(1) != 'P')) {
			s = br.readLine();
		}
		s = br.readLine();
		ArrayList<String> rules = new ArrayList<String>();
		while (s.charAt(0) != '@') {
			//s = removeWS(s);
			String tmp = s;
			s = "";
			while (s.indexOf("->") < 0) {
				tmp += s;
				if (br.ready()) {
					s = br.readLine();
				}else break;
			}
			rules.add(tmp);
			if (!br.ready()) {
				break;
			}
		}
		
		if (s.indexOf("->") > 0) {
			rules.add(s);
		}
		
		for (String str : rules) {
			tree.print(addAtribute(str));
		}
		tree.print("}\n");
		tree.close();
	}
	
	private static String addAtribute(String s) {
		String result = "";
		int sc = s.indexOf('[');
		int rl = s.indexOf("->");
		if ((sc < rl)&&(sc > 0)) {
			rl = s.indexOf(']');
			String tmp = s.substring(sc+1, rl);
			StringTokenizer st = new StringTokenizer(tmp, ",");
			while (st.hasMoreTokens()) {
				String attrib = st.nextToken();
				StringTokenizer st2 = new StringTokenizer(attrib);
				String type = st2.nextToken();
				String name = st2.nextToken();
				result += "\tpublic "+type+" "+name+";\n";
			}
		}
		return result;
	}
}
