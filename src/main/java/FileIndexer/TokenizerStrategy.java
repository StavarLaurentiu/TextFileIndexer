package main.java.FileIndexer;

import java.util.List;

/**
 * Strategy interface for tokenizing text into a list of tokens with types.
 */
public interface TokenizerStrategy {
    /**
     * Tokenizes the given text into a list of tokens with types.
     *
     * @param text is the text to tokenize
     * @return a list of Token objects with type and value
     */
    List<Token> tokenize(String text);
}
