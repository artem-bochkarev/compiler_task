import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * @author Artem Bochkarev
 *
 */
public class Tester implements Runnable {

	public void solve() throws IOException {
		BufferedReader inputFile = new BufferedReader(new FileReader("simple.c"));
		String input = "";
		while (inputFile.ready()) {
			input += inputFile.readLine() + '\n';
		}
		//Creator.apply("pascal.g");
		Creator.apply("c.g");
		
		ParserHandMade parser = new ParserHandMade();
		Result parseResult = null;
		try {
			parseResult = parser.parse(input);
			//parseResult = parser.parse("var\n a,b:integer; c:real; a:integer;");
		}catch(ParseException pExc) {
			System.err.println(pExc.getMessage() + " " +  pExc.getErrorOffset());
			System.exit(1);
		}
		if (parseResult != null) {
			out.println(parseResult.errorMsg);
			out.println(parseResult.tree.toString());
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
