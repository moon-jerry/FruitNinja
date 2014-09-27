.phony: all run clean

# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)
NAME = "Main"

all:
	@echo "Compiling..."
	javac *.java

run: all
	@echo "Running..."
	java $(NAME)

clean:
	rm *.class
