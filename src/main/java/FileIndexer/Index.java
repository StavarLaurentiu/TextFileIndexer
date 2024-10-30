package main.java.FileIndexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Class responsible for indexing files and querying the index.
 */
public class Index {
    private final Map<String, Set<String>> index = new HashMap<>();
    private final TokenizerContext tokenizerContext;

    /**
     * Constructs an Index with the specified tokenizer context.
     *
     * @param tokenizerContext the tokenizer context to use
     */
    public Index(TokenizerContext tokenizerContext) {
        this.tokenizerContext = tokenizerContext;
    }

    /**
     * Indexes a single file by its token values.
     *
     * @param file the file to index
     * @throws IOException if an I/O error occurs reading from the file
     */
    public void indexFile(File file) throws IOException {
        if (!file.isFile()) {
            return;
        }

        String content = new String(Files.readAllBytes(file.toPath()));
        List<Token> tokens = tokenizerContext.executeStrategy(content);

        for (Token token : tokens) {
            String tokenValue = token.getValue().toLowerCase();
            if (!tokenValue.isEmpty()) {
                // Check if the index contains the tokenValue key
                if (!index.containsKey(tokenValue)) {
                    // If not, put a new empty HashSet for that key
                    index.put(tokenValue, new HashSet<>());
                }

                // Add the file path to the set associated with the tokenValue key
                index.get(tokenValue).add(file.getAbsolutePath());
            }
        }
    }

    /**
     * Indexes a file or directory recursively.
     *
     * @param path the file or directory to index
     * @throws IOException if an I/O error occurs
     */
    public void indexPath(File path) throws IOException {
        if (path.isDirectory()) {
            // Get the list of files in the directory
            File[] files = path.listFiles();

            // Check if the list is null (which could happen if path is not a directory or an I/O error occurs)
            if (files == null) {
                throw new NullPointerException("The directory path is either invalid or inaccessible.");
            }

            // Process each file in the directory
            for (File file : files) {
                // Recursively index each file or directory
                indexPath(file);
            }
        } else {
            indexFile(path);
        }
    }

    /**
     * Erases a file from the index.
     *
     * @param file the file path to erase
     */
    public void eraseFile (String file) {
        // Iterate over the index entries
        for (Map.Entry<String, Set<String>> entry : index.entrySet()) {
            // Remove the file path from the set associated with the entry key
            entry.getValue().remove(file);
        }
    }

    /**
     * Erases a file or directory from the index.
     *
     * @param path the file or directory path to erase
     */
    public void erasePath(File path) {
        if (path.isDirectory()) {
            // Get the list of files in the directory
            File[] files = path.listFiles();

            // Check if the list is null (which could happen if path is not a directory or an I/O error occurs)
            if (files == null) {
                throw new NullPointerException("The directory path is either invalid or inaccessible.");
            }

            // Process each file in the directory
            for (File file : files) {
                // Recursively erase each file or directory
                erasePath(file);
            }
        } else {
            eraseFile(path.getAbsolutePath());
        }
    }

    /**
     * Queries the index for files containing the given word.
     *
     * @param word the word to search for
     * @return a set of file paths containing the word
     */
    public Set<String> query(String word) {
        word = word.toLowerCase();

        // Check if the index contains the word key
        // If the word is found, return the set of file paths
        // If the word is not found, return an empty set
        return index.getOrDefault(word, Collections.emptySet());
    }

    /**
     * Clears the index.
     */
    public void clear() {
        index.clear();
    }

    public Map<String, Set<String>> getIndex() {
        return index;
    }
}
