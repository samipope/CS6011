# Define the compiler to use
CXX = g++

# Compiler flags
# -std=c++11 : Compile for C++11 standard
# -Wall : Turn on most compiler warnings
CXXFLAGS = -std=c++11 -Wall

# Library path and library to link against
LIB_PATH = ../Lab5Library
LIBS = -L$(LIB_PATH) -lmsdscript

# Include path if your headers are not in the standard path
INCLUDES = -I./

# Define source (.cpp) and header (.hpp) files for the binary
SRCS = main.cpp 
HEADERS = cmdline.h Expr.h catch.h parse.h

# Define the target executable
TARGET = msdscript

# Rule to make the executable
$(TARGET):
	$(CXX) $(CXXFLAGS) $(INCLUDES) $(SRCS) $(LIBS) -o $(TARGET)

# Define clean command
.PHONY: clean
clean:
	rm -f $(TARGET) *.o

# Define a phony target to avoid problems with files named "all" or "clean"
.PHONY: all clean
