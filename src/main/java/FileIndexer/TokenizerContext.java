package main.java.FileIndexer;

import java.util.List;

/**
 * Context class that uses a TokenizerStrategy.
 */
public class TokenizerContext {
    private TokenizerStrategy strategy;

    /**
     * Sets the tokenizer strategy to use.
     *
     * @param strategy is the tokenizer strategy
     */
    public void setStrategy(TokenizerStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Tokenizes the given text using the current strategy.
     *
     * @param text the text to tokenize
     * @return a list of Token objects with type and value
     */
    public List<Token> executeStrategy(String text) {
        return strategy.tokenize(text);
    }
}
