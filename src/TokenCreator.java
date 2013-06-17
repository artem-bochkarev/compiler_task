import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class TokenCreator {
	
	public static void apply(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		PrintWriter token = new PrintWriter(new FileWriter("src\\Token.java"));
		token.print("public class Token { \n" +
				"\tpublic TokenName name;\n" +
				"\tpublic String str;\n" +
				"\tpublic Token(TokenName tName, String str) {\n" +
				"\t\tthis.str = str;\n" +
				"\t\tname = tName;\n" +
				"\t}\n"+
				"\tpublic enum TokenName {\n" +
				"\t\tEND");
		String s = br.readLine();
		while ((s.charAt(0) != '@')||(s.charAt(1) != 'L')) {
			s = br.readLine();
		}
		s = br.readLine();
		while (s.charAt(0) != '@') {
			s = removeWS(s);
			if ( s.indexOf(':') < 0) {
				s = br.readLine();
				continue;
			}
			String name = s.substring(0, s.indexOf(':'));
			token.print(", " + name);
			s = br.readLine();
		}
		token.print("\n\t}\n}");
		token.close();
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
