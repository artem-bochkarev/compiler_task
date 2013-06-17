import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//import Lexer.Regexp;


public class LexerCreator {
	
	public static void apply(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		BufferedReader lexerExample = new BufferedReader(new FileReader("LexerExample.java"));
		PrintWriter lexerOut = new PrintWriter(new FileWriter("src\\Lexer.java"));
		String s = lexerExample.readLine();
		while (s.indexOf("//auto-generated") < 0) {
			lexerOut.println(s);
			s = lexerExample.readLine();
		}
		lexerOut.println(s);
		
		s = br.readLine();
		while ((s.charAt(0) != '@')||(s.charAt(1) != 'L')) {
			s = br.readLine();
		}
		s = br.readLine();
		lexerOut.println("\t\tskipWS = true;");
		while (s.charAt(0) != '@') {
			s = removeWS(s);
			if ( s.indexOf(':') < 0) {
				s = br.readLine();
				continue;
			}
			String name = s.substring(0, s.indexOf(':'));
			String regexp = s.substring(s.indexOf(':')+1);
			if (name.length() > 0 && regexp.length() > 0)
				lexerOut.println("\t\tregexps.add(new Regexp(\"" + regexp + "\", Token.TokenName." + name + ", \"" + name + "\" ));");
			//lexerOut.println("\t\tautomats.add(new Automat(\"" + regexp + "\"));");
			//lexerOut.println("\t\ttokens.add(Token.TokenName." + name + ");");
			s = br.readLine();
		}
		lexerOut.print("\t}\n}\n");
		lexerOut.close();
		lexerExample.close();
		br.close();
	}
	
	private static String removeWS(String s) {
		String tmp = "";
		for (int i=0; i<s.length(); ++i) {
			char c = s.charAt(i);
			if ((c == ' ') || (c == '\t')) {
				if ((i > 0)&&(s.charAt(i-1) == '\\')) {
					tmp += c;
				}
			}else {
				if ( c == '"' ) {
					tmp += '\\';
				}
				if (c == '\\') {
					tmp += c;
				}
				tmp += c;
			}
		}
		return tmp;
	}

}
