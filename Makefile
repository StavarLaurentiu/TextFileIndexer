# Directories
SRC_DIR := src/main/java
BIN_DIR := bin
TEST_DIR := src/test/java

# Find all Java source files
SRC_FILES := $(shell find $(SRC_DIR)/FileIndexer -name "*.java")
TEST_FILES := $(shell find $(TEST_DIR) -name "*.java")

# Default target
all: compile

# Compile main classes into bin
compile:
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $(SRC_FILES)

# Compile test classes with main classes in classpath
compile-tests: compile
	javac -d $(BIN_DIR) -cp $(BIN_DIR) $(TEST_FILES)

# Run the main application
run: compile
	java -cp $(BIN_DIR) src/main/java/FileIndexer/TextFileIndexer.java

# Run tests
test: compile-tests
	java -cp $(BIN_DIR) src/test/java/TextFileIndexerTest.java

# Clean up
clean:
	rm -rf $(BIN_DIR)/*
