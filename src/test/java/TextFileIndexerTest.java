package test;

import main.java.FileIndexer.*;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class TextFileIndexerTest {

    private TokenizerContext tokenizerContext;
    private Index index;

    public TextFileIndexerTest() {
        tokenizerContext = new TokenizerContext();
        tokenizerContext.setStrategy(new SimpleTokenizer());
        index = new Index(tokenizerContext);
    }

    public void reset() {
        tokenizerContext.setStrategy(new SimpleTokenizer());
        index = new Index(tokenizerContext);
    }

    public void testIndexingFiles() throws IOException {
        System.out.println("Running testIndexingFiles...");
        File path = new File("example_dir");
        index.indexPath(path);
        boolean passed = !index.getIndex().isEmpty();
        printTestResult("testIndexingFiles", passed);
    }

    public void testQuerySimpleWord() throws IOException {
        System.out.println("Running testQuerySimpleWord...");
        File file = new File("example_dir/file1.txt");
        index.indexPath(file);
        Set<String> result = index.query("Hello");
        boolean passed = result.contains(file.getAbsolutePath());
        printTestResult("testQuerySimpleWord", passed);
    }

    public void testEmptyFile() throws IOException {
        System.out.println("Running testEmptyFile...");
        File emptyFile = new File("example_dir/empty_file.txt");
        index.indexPath(emptyFile);
        boolean passed = index.getIndex().isEmpty();
        printTestResult("testEmptyFile", passed);
    }

    public void testCaseSensitivity() throws IOException {
        System.out.println("Running testCaseSensitivity...");
        File file = new File("example_dir/file1.txt");
        index.indexPath(file);
        Set<String> result = index.query("hello"); // lowercase query
        boolean passed = result.contains(file.getAbsolutePath());
        printTestResult("testCaseSensitivity", passed);
    }

    public void testQueryNonExistentWord() throws IOException {
        System.out.println("Running testQueryNonExistentWord...");
        File file = new File("example_dir/file1.txt");
        index.indexPath(file);
        Set<String> result = index.query("NonExistentWord");
        boolean passed = result.isEmpty();
        printTestResult("testQueryNonExistentWord", passed);
    }

    public void testQueryWithNumber() throws IOException {
        System.out.println("Running testQueryWithNumber...");
        File file = new File("example_dir/file1.txt");
        index.indexPath(file);
        Set<String> result = index.query("123");
        boolean passed = result.contains(file.getAbsolutePath());
        printTestResult("testQueryWithNumber", passed);
    }

    public void testQuerySpecialCharacter() throws IOException {
        System.out.println("Running testQuerySpecialCharacter...");
        File file = new File("example_dir/file2.txt");
        index.indexPath(file);
        Set<String> result = index.query("&");
        boolean passed = result.contains(file.getAbsolutePath());
        printTestResult("testQuerySpecialCharacter", passed);
    }

    public void testChangeTokenizerStrategy() throws IOException {
        System.out.println("Running testChangeTokenizerStrategy...");
        File file = new File("example_dir/file2.txt");
        index.indexPath(file);
        tokenizerContext.setStrategy(new AdvancedTokenizer());
        index.indexPath(file);
        Set<String> result = index.query("hello");
        boolean passed = !result.isEmpty();
        printTestResult("testChangeTokenizerStrategy", passed);
    }

    public void testMultipleFilesIndexing() throws IOException {
        System.out.println("Running testMultipleFilesIndexing...");
        File file1 = new File("example_dir/file1.txt");
        File file2 = new File("example_dir/file2.txt");
        index.indexPath(file1);
        index.indexPath(file2);
        Set<String> result1 = index.query("Hello");
        Set<String> result2 = index.query("like");
        boolean passed = result1.contains(file1.getAbsolutePath()) && result2.contains(file2.getAbsolutePath());
        printTestResult("testMultipleFilesIndexing", passed);
    }

    public void testQueryAfterChangeStrategy() throws IOException {
        System.out.println("Running testQueryAfterChangeStrategy...");
        File file1 = new File("example_dir/file1.txt");
        File file2 = new File("example_dir/file2.txt");
        index.indexPath(file1);
        tokenizerContext.setStrategy(new AdvancedTokenizer());
        index.indexPath(file2);
        Set<String> result1 = index.query("Hello");
        Set<String> result2 = index.query("like");
        boolean passed = result1.contains(file1.getAbsolutePath()) && result2.contains(file2.getAbsolutePath());
        printTestResult("testQueryAfterChangeStrategy", passed);
    }

    public void testEraseFile() throws IOException {
        System.out.println("Running testEraseFile...");
        File file1 = new File("example_dir/file1.txt");
        File file2 = new File("example_dir/file2.txt");
        index.indexPath(file1);
        index.indexPath(file2);
        index.eraseFile(file1.getAbsolutePath());
        Set<String> result1 = index.query("Hello");
        Set<String> result2 = index.query("like");
        boolean passed = !result1.contains(file1.getAbsolutePath()) && result2.contains(file2.getAbsolutePath());
        printTestResult("testEraseFile", passed);
    }

    public void testQueryAfterErase() throws IOException {
        System.out.println("Running testQueryAfterErase...");
        File file1 = new File("example_dir/file1.txt");
        File file2 = new File("example_dir/file2.txt");
        index.indexPath(file1);
        index.indexPath(file2);
        index.eraseFile(file1.getAbsolutePath());
        Set<String> result1 = index.query("Hello");
        Set<String> result2 = index.query("like");
        boolean passed = !result1.contains(file1.getAbsolutePath()) && result2.contains(file2.getAbsolutePath());
        printTestResult("testQueryAfterErase", passed);
    }

    private void printTestResult(String testName, boolean passed) {
        if (passed) {
            System.out.println(testName + " passed.");
        } else {
            System.out.println(testName + " failed.");
        }
    }

    public static void main(String[] args) throws IOException {
        TextFileIndexerTest tester = new TextFileIndexerTest();
        tester.testIndexingFiles();
        tester.reset();
        tester.testQuerySimpleWord();
        tester.reset();
        tester.testEmptyFile();
        tester.reset();
        tester.testCaseSensitivity();
        tester.reset();
        tester.testQueryNonExistentWord();
        tester.reset();
        tester.testQueryWithNumber();
        tester.reset();
        tester.testQuerySpecialCharacter();
        tester.reset();
        tester.testChangeTokenizerStrategy();
        tester.reset();
        tester.testMultipleFilesIndexing();
        tester.reset();
        tester.testQueryAfterChangeStrategy();
        tester.reset();
        tester.testEraseFile();
        tester.reset();
        tester.testQueryAfterErase();
    }
}
