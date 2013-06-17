import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * @author Artem Bochkarev
 *
 */
public class Tester implements Runnable {

	public void solve() throws IOException {
		/*LexerCreator.apply("structure.g");
		TokenCreator.apply("structure.g");
		TreeCreator.apply("structure.g");
		ParserCreator.apply("structure.g");*/
		/*try {
			LexerExample lp = new LexerExample("12345 + abba -12+a");
			while (lp.curToken != Token.TokenName.END) {
				lp.nextToken();
				switch (lp.curToken().name) {
					case ID: {
						System.out.println("  ----  ID = " + lp.curToken().str);
					}break;
					case INT: {
						System.out.println("  ----  INT = "  + lp.curToken().str);
					}break;
					case PLUS: {
						System.out.println("  ----  PLUS = " + lp.curToken().str);
					}break;
					case MINUS: {
						System.out.println("  ----  MINUS = "  + lp.curToken().str);
					}break;
					default: {
						System.out.println("  ----  END");
					}
				};
				
			}
		/*	Automat auto = new Automat("'0'..'9'+");
			out.println(auto.next('1'));
			out.println(auto.next('2'));
			out.println(auto.next('3'));
			out.println(auto.next('\n'));
			*/
			
		/*}catch(ParseException e) {
			System.err.println(e.getMessage());
		}
		out.println();*/
		//Creator.apply("pascal.g");
		Creator.apply("c.g");
		
		Parser parser = new Parser();
		Tree parseResult = null;
		try {
			parseResult = parser.parse("int i(123); int a;");
			//parseResult = parser.parse("var\n a,b:integer; c:real; a:integer;");
		}catch(ParseException pExc) {
			System.err.println(pExc.getMessage() + " " +  pExc.getErrorOffset());
			System.exit(1);
		}
		if (parseResult != null) {
			out.println(parseResult.toString());
		}
		
	}

	BufferedReader br;
	StringTokenizer st;
	PrintWriter out;

	String nextToken() throws IOException {
		while (!st.hasMoreTokens()) {
			st = new StringTokenizer(br.readLine());
		}
		return st.nextToken();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	String nextStr() throws IOException {
		return nextToken();
	}

	char nextChar() throws IOException {
		return nextToken().charAt(0);
	}

	long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(new OutputStreamWriter(System.out));
			st = new StringTokenizer("");
			solve();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			out.close();
		}
	}

	public static void main(String[] args) {
		new Thread(new Tester()).start();
	}
}
