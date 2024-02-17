#include <iostream>
#include "shelpers.h" //include for helper methods
#include <unistd.h>
#include <sys/wait.h>
#include <vector>
#include <string>
#include <cctype>
#include <cstdlib>

int main() {
    std::string line;
    while (std::getline(std::cin, line)) {
        if (line.empty() || std::all_of(line.begin(), line.end(), [](unsigned char c){ return std::isspace(c); })) {
            // input is empty or whitespace only, continue to the next iteration of the loop, lets us press enter
            continue;
        }
        if (line == "exit") {
            break; // exit the shell by typing "exit"
        }
        std::vector<Command> commands = getCommands(tokenize(line));
        std::vector<int> pipefds; // for storing pipe file descriptors
        std::vector<std::string> tokens = tokenize(line); // helps us parse things like cd users/samanthapope

        if (tokens.empty()) continue; // skip empty lines

        size_t equalPos = line.find('=');
        if (equalPos != std::string::npos && !tokens.empty()) {
            std::string varName = tokens[0].substr(0, equalPos);
            std::string varValue = line.substr(equalPos + 1);
            if (setenv(varName.c_str(), varValue.c_str(), 1) != 0) {
                perror("setenv");
            }
            continue; // Move to the next input
        }

        for (auto& token : tokens) {
            if (token[0] == '$') {
                // Assume the variable name is the rest of the token
                std::string varName = token.substr(1);
                char* varValue = getenv(varName.c_str());

                if (varValue != nullptr) {
                    // Replace token with its value from the environment
                    token = std::string(varValue);
                } else {
                    // Handle the case where the environment variable is not set
                    std::cerr << "Error: Environment variable " << varName << " not set.\n";
                }
            }
        }


        // Assuming `commands` is a vector<Command> filled by getCommands(tokenize(line));
        for (auto& cmd : commands) {
            for (auto& arg : cmd.argv) {
                if (arg != nullptr && arg[0] == '$') {
                    // Extract the environment variable name
                    std::string varName = std::string(arg).substr(1); // Skip the '$'
                    char* varValue = getenv(varName.c_str());

                    if (varValue != nullptr) {
                        // Replace the arg with the value of the environment variable
                        free(arg); // Free the original arg allocated by strdup
                        arg = strdup(varValue); // Allocate new string with the variable value
                    } else {
                        std::cerr << "Error: Environment variable " << varName << " not set.\n";
                    }
                }
            }
        }


        if (tokens[0] == "cd") { //if the command is to go to current directory
            std::string targetDir = (tokens.size() == 1) ? getenv("HOME") : tokens[1];
            if (chdir(targetDir.c_str()) != 0) {
                perror("chdir");
            }
            continue; // skip the rest of the loop since it's a built-in command
        }
        if (tokens[0] == "exit") {
            break; // exit the shell
        }

        for (size_t i = 0; i < commands.size(); ++i) {
            int fds[2];
            if (i < commands.size() - 1) { // No pipe for the last command
                if (pipe(fds) == -1) {
                    perror("pipe");
                    exit(EXIT_FAILURE);
                }
                commands[i].outputFd = fds[1];
                commands[i + 1].inputFd = fds[0];
                pipefds.push_back(fds[0]);
                pipefds.push_back(fds[1]);
            }

            pid_t pid = fork();
            if (pid == 0) { // Child process
                if (commands[i].inputFd != STDIN_FILENO) {
                    dup2(commands[i].inputFd, STDIN_FILENO);
                    close(commands[i].inputFd);
                }
                if (commands[i].outputFd != STDOUT_FILENO) {
                    dup2(commands[i].outputFd, STDOUT_FILENO);
                    close(commands[i].outputFd);
                }

                // Close all other pipe fds in the child process
                for (int fd : pipefds) {
                    close(fd);
                }

                execvp(commands[i].argv[0], commands[i].argv.data());
                std::cerr << "Failed to execute command: " << line << std::endl;
                exit(EXIT_FAILURE);
            } else if (pid < 0) {
                std::cerr << "Failed to fork" << std::endl;
                exit(EXIT_FAILURE);
            }
        }

        // Parent process closes all pipe fds
        for (int fd : pipefds) {
            close(fd);
        }

        // Wait for all child processes to finish
        for (size_t i = 0; i < commands.size(); ++i) {
            int status;
            wait(&status);
        }
    }
    return 0;
}
