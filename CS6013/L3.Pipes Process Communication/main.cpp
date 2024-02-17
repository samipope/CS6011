//////////////////////////////////////////////////
/**
 * Samantha Pope
 * CS6014 Lab3
 * 02.05.2024
 *
 * This is a simple program that spawns a new process using fork()
 * The parent should print "parent" then wait for the child process to exit
 * The child should print child and then read in a word from the command line and send the message through a pipe to the child
 *
 */
////////////////////////////////////////////////

#include <iostream>
#include <unistd.h>
#include <sys/wait.h>
#include <cstring>
#include <cerrno>

int main(int argc, char *argv[]) {
    errno;

    if (argc < 2) {
        std::cerr << "usage: " << argv[0] << " <message you type in terminal>" << std::endl;
        exit(1); //if user doesn't type anything into the command line
    }

    int pipefd[2];
    if (pipe(pipefd) == -1) {
        perror("error is:");
        exit(1);
    }

    pid_t pid = fork();
    if (pid == -1) {
        perror("error is"); //using erno to display what went wrong
        exit(1);
    }

    if (pid > 0) { // parent process
        close(pipefd[0]); // close read end, not needed here

        const char *message = argv[1];
        int length = strlen(message) + 1; // +1 for null terminator

        // send message length and message to child
        if (write(pipefd[1], &length, sizeof(length)) == -1 || write(pipefd[1], message, length) == -1) {
            perror("error is"); //using erno to display what went wrong
            exit(1);
        }

        close(pipefd[1]); // done writing

        // wait for child to finish
        int status;
        waitpid(pid, &status, 0);
        std::cout << "Parent" << std::endl;
    } else { // child process
        close(pipefd[1]); // close write end, not needed here

        int messageLength;
        // read message length first
        if (read(pipefd[0], &messageLength, sizeof(messageLength)) == -1) {
            perror("error is"); //using erno to display what went wrong
            exit(1);
        }

        // allocate space for the message and read it
        char *message = new char[messageLength];
        if (read(pipefd[0], message, messageLength) == -1) {
            perror("error is"); //using erno to display what went wrong
            exit(1);
        }

        std::cout << "child says: " << message << std::endl;
        delete[] message;

        close(pipefd[0]); // done reading
    }

    return 0;
}
