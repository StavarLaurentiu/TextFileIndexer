package main.java.FileIndexer;

import java.io.File;
import java.io.IOException;
import java.util.*;

    /**
     * Main application class providing a console interface.
     */
    public class TextFileIndexer {
        static List<String> indexedFiles = new ArrayList<String>();

        public static void main(String[] args) {
            Scanner scanner = null;

            try {
                scanner = new Scanner(System.in);

                TokenizerContext tokenizerContext = new TokenizerContext();
                tokenizerContext.setStrategy(new SimpleTokenizer());

                Index index = new Index(tokenizerContext);

                System.out.println("Welcome to the text file indexing service.");
                System.out.println("Commands:");
                System.out.println("  index <path>          - Index the specified file or directory");
                System.out.println("  erase <path>          - Erase the specified file or directory from the index");
                System.out.println("  query <word>          - Query files containing the given word");
                System.out.println("  strategy <type>       - Change tokenizer strategy (simple/advanced)");
                System.out.println("  exit                  - Exit the application");

                while (true) {
                    System.out.print("> ");
                    String line;

                    try {
                        line = scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error reading input. Please try again.");
                        scanner.next();  // Clear the invalid input
                        continue;
                    }

                    // Exit on EOF
                    if (line == null) {
                        break;
                    }

                    // Skip empty lines
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    // Split the line into command and arguments
                    String[] parts = line.trim().split("\\s+", 2);
                    String command = parts[0];

                    // Process the command
                    if (command.equalsIgnoreCase("exit")) {
                        break;
                    } else if (command.equalsIgnoreCase("index")) {
                        // Check if the path argument is provided
                        if (parts.length < 2) {
                            System.out.println("Usage: index <path>");
                            continue;
                        }

                        // Check if the path exists
                        String pathStr = parts[1];
                        File path = new File(pathStr);
                        if (!path.exists()) {
                            System.out.println("Path does not exist: " + pathStr);
                            continue;
                        }

                        // Check if the path is already indexed
                        if (indexedFiles.contains(pathStr)) {
                            System.out.println("Path already indexed: " + pathStr);
                            continue;
                        }

                        // Index the path
                        try {
                            index.indexPath(path);

                            // Add the paths recursively to the indexed files list
                            indexPath(path);

                            System.out.println("Indexed: " + pathStr);
                        } catch (IOException e) {
                            System.out.println("Error indexing path: " + e.getMessage());
                        }
                    } else if (command.equalsIgnoreCase("erase")) {
                        // Check if the path argument is provided
                        if (parts.length < 2) {
                            System.out.println("Usage: erase <path>");
                            continue;
                        }

                        // Check if the path exists
                        String pathStr = parts[1];
                        File path = new File(pathStr);
                        if (!path.exists()) {
                            System.out.println("Path does not exist: " + pathStr);
                            continue;
                        }

                        // Check if no indexed path starts with the given path
                        boolean found = false;
                        for (String indexedPath : indexedFiles) {
                            if (indexedPath.startsWith(pathStr)) {
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            System.out.println("Path not indexed: " + pathStr);
                            continue;
                        }

                        // Erase the path
                        index.erasePath(path);

                        // Remove all the files that start with the path
                        indexedFiles.removeIf(indexedPath -> indexedPath.startsWith(pathStr));

                        System.out.println("Erased: " + pathStr + " and all its sub-paths.");
                    } else if (command.equalsIgnoreCase("query")) {
                        // Check if the word argument is provided
                        if (parts.length < 2) {
                            System.out.println("Usage: query <word>");
                            continue;
                        }

                        // Query the index
                        String word = parts[1];
                        Set<String> results;
                        try {
                            results = index.query(word);
                            if (results.isEmpty()) {
                                System.out.println("No files contain the word: " + word);
                            } else {
                                System.out.println("Files containing the word '" + word + "':");
                                for (String file : results) {
                                    System.out.println("  " + file);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error querying index: " + e.getMessage());
                        }
                    } else if (command.equalsIgnoreCase("strategy")) {
                        // Check if the type argument is provided
                        if (parts.length < 2) {
                            System.out.println("Usage: strategy <type>");
                            continue;
                        }

                        // Get the strategy type
                        String type = parts[1];

                        // Set the tokenizer strategy
                        try {
                            if (type.equalsIgnoreCase("simple")) {
                                tokenizerContext.setStrategy(new SimpleTokenizer());
                                System.out.println("Tokenizer strategy set to SimpleTokenizer.");
                            } else if (type.equalsIgnoreCase("advanced")) {
                                tokenizerContext.setStrategy(new AdvancedTokenizer());
                                System.out.println("Tokenizer strategy set to AdvancedTokenizer.");
                            } else {
                                System.out.println("Unknown strategy type: " + type);
                            }
                        } catch (Exception e) {
                            System.out.println("Error setting tokenizer strategy: " + e.getMessage());
                        }

                        // Clear the index
                        index.clear();

                        // Re-index the files with the new strategy
                        for (String pathStr : indexedFiles) {
                            File path = new File(pathStr);
                            try {
                                index.indexPath(path);
                                System.out.println("Re-indexed: " + pathStr + " with new strategy.");
                            } catch (IOException e) {
                                System.out.println("Error re-indexing path: " + e.getMessage() + " with new strategy.");
                            }
                        }
                    } else {
                        System.out.println("Unknown command: " + command);
                    }
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            } finally {
                if (scanner != null) {
                    scanner.close();
                }
                System.out.println("Goodbye.");
            }
        }

        private static void indexPath(File path) {
            if (path.isDirectory()) {
                for (File file : Objects.requireNonNull(path.listFiles())) {
                    indexPath(file);
                }
            } else {
                indexedFiles.add(path.getPath());
            }
        }
    }

