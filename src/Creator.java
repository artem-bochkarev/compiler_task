import java.io.IOException;


public class Creator {
	public static void apply(String name) throws IOException {
		LexerCreator.apply(name);
		TokenCreator.apply(name);
		TreeCreator.apply(name);
		ParserCreator.apply(name);
	}

}
