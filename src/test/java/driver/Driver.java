package driver;

import com.github.evanquan.parsely.parser.Parser;
import com.github.evanquan.parsely.parser.ParserFactory;

public class Driver {
    public static void main(String[] args) {
        Parser parser =
                ParserFactory.getParser(ParserFactory.ParserType.VERB_AGNOSTIC);
    }
}
