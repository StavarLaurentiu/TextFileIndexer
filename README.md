# TextFileIndexer #

## Project Overview ##
*TextFileIndexer* is a Java-based application designed to index text files in a given directory. The program tokenizes the contents of each file, supports multiple tokenization strategies, and stores the results for quick querying. Users can search for specific words across indexed files, change tokenization strategies, and manage their index easily through a console-based interface. This project uses a *Makefile* to simplify compilation, running, and testing.

## Features ##
- File Indexing: Indexes text files in a specified directory.
- Multiple Tokenization Strategies:
  - SimpleTokenizer: Splits text based on whitespace and punctuation.
  - AdvancedTokenizer: Distinguishes between words, numbers, and punctuation.
- Dynamic Strategy Switching: Change the tokenization strategy at runtime and re-index files with the new strategy.
- Console Interface: Offers an interactive console for indexing, querying, and strategy management.
- Testing Suite: Includes a series of tests for core functionality and edge cases.
  
## Project Structure ##
The project follows a standard Java structure with main code under src/main/java and tests in src/test/java.

. <br>
├── src <br>
│   ├── main <br>
│   │   └── java  <br>
│   │       └── FileIndexer <br>
│   │           ├── AdvancedTokenizer.java <br>
│   │           ├── Index.java <br>
│   │           ├── SimpleTokenizer.java <br>
│   │           ├── TextFileIndexer.java <br>
│   │           ├── Token.java <br>
│   │           ├── TokenizerContext.java <br>
│   │           └── TokenizerStrategy.java <br>
│   └── test <br>
│       └── java <br>
│           └── test <br>
│               └── TextFileIndexerTest.java <br>
├── lib <br>
│   └── (JUnit and other libraries if required) <br>
└── Makefile <br>

## Requirements ##
- Java: Ensure Java (JDK 8 or higher) is installed and configured on your system.
- Make: Required to use the provided Makefile for building, running, and testing.

## Usage Instructions ##
1. *Clone the Repository*
2. 
    ```bash
   git clone <repository-url> <br>
   cd TextFileIndexer <br> <br>
    ```

2. *Build the Project* <br> <br>
   The Makefile includes targets for building and cleaning the project. Use the following command to compile the project:
    
   ```bash
    make
    ```
    This command compiles all classes in src/main/java and src/test/java and places the compiled .class files in the bin directory.
    <br> <br>

3. *Run the Application* <br> <br>
   To start the console-based TextFileIndexer application, run:

    ```bash
    make run
    ```
    The application will display available commands:
    
    - index <path>: Index the specified file or directory.
    - erase <path>: Remove the specified file or directory from the index.
    - query <word>: Search for files containing the specified word.
    - strategy <type>: Switch between simple and advanced tokenization strategies.
    - exit: Exit the application.
    <br> <br>

4. *Run the Tests* <br> <br>
   To compile and run the test suite, use:

    ```bash
    make test
    ```
    This command runs all test cases in TextFileIndexerTest to verify the functionality of core components and edge cases.
    <br> <br>

5. *Clean the Project*
   To remove all compiled files from the bin directory, run:
    
    ```bash
    make clean
    ```

    This command deletes all .class files in the bin directory.
        <br> <br>

## Code Overview ##

### Key Classes ###

- **TextFileIndexer**: Main application class providing a console-based interface.
- **Index**: Manages the indexed data, allowing files to be indexed, erased, and queried.
- **TokenizerContext**: A strategy context that allows for dynamic switching between tokenizers.
- **TokenizerStrategy**: An interface for tokenization strategies.
- **SimpleTokenizer**: Basic tokenizer that separates words by non-word characters.
- **AdvancedTokenizer**: Tokenizer that differentiates between words, numbers, and punctuation.
- **Token**: Represents a token with type and value.

### Testing ###
The TextFileIndexerTest class includes various tests to verify:

- Basic indexing functionality.
- Querying with words, numbers, and special characters.
- Edge cases such as empty files, non-existent words, and special characters in file paths.

### Example Commands ###
Here are some example commands you can use in the application console:

```plaintext
> index example_dir
> query hello
> strategy advanced
> query 123
> erase example_dir/file1.txt
> exit
```

## Known Limitations ##
- The application requires files to be in plain text format.
- Non-recursive directory indexing is currently unsupported.
- The application does not support concurrent indexing or querying.
- Querying special characters may not work as expected with the simple tokenizer.
