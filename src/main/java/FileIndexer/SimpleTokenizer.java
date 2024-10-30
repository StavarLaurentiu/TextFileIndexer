package main.java.FileIndexer;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete strategy that splits text based on non-word characters and assigns a simple "WORD" token type.
 */
public class SimpleTokenizer implements TokenizerStrategy {
    @Override
    public List<Token> tokenize(String text) {
        List<Token> tokens = new ArrayList<>();
        String[] words = text.split("\\W+");

        for (String word : words) {
            if (!word.isEmpty()) {
                tokens.add(new Token("WORD", word));
            }
        }
        return tokens;
    }
}
