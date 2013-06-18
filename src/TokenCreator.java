import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TokenCreator {
	
	public static void apply(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		PrintWriter tokenWriter = new PrintWriter(new FileWriter("src\\Token.java"));
		List<String> names = new ArrayList<String>();
		tokenWriter.print("import java.util.Map;\n" +
				"import java.util.HashMap;\n\n" +
				"public class Token { \n" +
				"\tpublic TokenName name;\n" +
				"\tpublic String str;\n" +
				"\tpublic Map<TokenName, String> names;\n" +
				"\tpublic Token(TokenName tName, String str) {\n" +
				"\t\tthis.str = str;\n" +
				"\t\tnames = new HashMap<TokenName, String>();\n" +
				"\t\tname = tName;\n" +
				"\t\tinit();\n" +
				"\t}\n"+
				"\tpublic enum TokenName {\n" +
				"\t\tEND");
		String s = br.readLine();
		while ((s.charAt(0) != '@')||(s.charAt(1) != 'L')) {
			s = br.readLine();
		}
		
		names.add("END");
		s = br.readLine();
		while (s.charAt(0) != '@') {
			s = removeWS(s);
			if ( s.indexOf(':') < 0) {
				s = br.readLine();
				continue;
			}
			String name = s.substring(0, s.indexOf(':'));
			names.add(name);
			tokenWriter.print(", " + name);
			s = br.readLine();
		}
		tokenWriter.println("\n\t}");
		
		tokenWriter.print("\tprivate void init() {\n");
		for (String name : names) {
			tokenWriter.println("\t\tnames.put( TokenName." + name + ", \"" + name + "\" );");
		}
		
		tokenWriter.println("\t}\n");
		
		tokenWriter.print("\tpublic String getName(TokenName name) {\n" +
						  "\t\treturn names.get(name);\n" +
						  "\t}\n");
		
		tokenWriter.println("}");
		tokenWriter.close();
		br.close();
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

}
