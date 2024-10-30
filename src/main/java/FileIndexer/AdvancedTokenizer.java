package main.java.FileIndexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Advanced tokenizer that distinguishes between different token types, such as WORD and NUMBER.
 */
public class AdvancedTokenizer implements TokenizerStrategy {
    private static final Pattern TOKEN_PATTERN = Pattern.compile("\\d+|\\w+|\\p{Punct}");

    @Override
    public List<Token> tokenize(String text) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(text);

        while (matcher.find()) {
            String tokenValue = matcher.group();
            String tokenType;

            if (tokenValue.matches("\\d+")) {
                tokenType = "NUMBER";
            } else if (tokenValue.matches("\\w+")) {
                tokenType = "WORD";
            } else {
                tokenType = "PUNCTUATION";
            }

            tokens.add(new Token(tokenType, tokenValue));
        }
        return tokens;
    }
}
