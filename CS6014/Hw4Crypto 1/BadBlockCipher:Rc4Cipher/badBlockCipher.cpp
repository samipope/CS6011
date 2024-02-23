//
// Created by Samantha Pope on 2/22/24.
//
#include <vector>
#include <algorithm> // For std::shuffle
#include <random>    // For std::default_random_engine
/**
 *
 *
 *
 */

class badBlockCipher{
private:
    std::vector<uint8_t> key{8, 0}; // Initialize key with 8 zeros
    std::vector<std::vector<uint8_t>> subTables; // To hold 8 substitution tables
    std::vector<std::vector<uint8_t>> reverseSubTables; // For decryption

    // Utility function to rotate state
    void rotateLeft(std::vector<uint8_t>& state) {
        uint8_t carry = state[0] >> 7; // Save the leftmost bit to carry over
        for (int i = 0; i < 7; ++i) {
            state[i] = (state[i] << 1) | (state[i + 1] >> 7);
        }
        state[7] = (state[7] << 1) | carry;
    }

    // Utility function to rotate state to the right, used in decryption
    void rotateRight(std::vector<uint8_t>& state) {
        uint8_t carry = state[7] & 1; // Save the rightmost bit to carry over
        for (int i = 7; i > 0; --i) {
            state[i] = (state[i] >> 1) | (state[i - 1] << 7);
        }
        state[0] = (state[0] >> 1) | (carry << 7);
    }

public:
    badBlockCipher(const std::string& password) {
        generateEncryptionKey(password);
        generateSubstitutionTables();
        generateReverseSubstitutionTables();
    }

    void generateEncryptionKey(const std::string& password) {
        for (size_t i = 0; i < password.length(); i++) {
            uint8_t passwordByte = static_cast<uint8_t>(password[i]);
            key[i % 8] ^= passwordByte;
        }
    }

    void generateSubstitutionTables() {
        // generate 8 substitution tables
        for(int i = 0; i < 8; i++) {
            std::vector<uint8_t> table(256);
            // Fill the table with 0 to 255
            std::iota(table.begin(), table.end(), 0);
            // Shuffle using Fisher-Yates
            auto rng = std::default_random_engine{};
            std::shuffle(table.begin(), table.end(), rng);
            subTables.push_back(table);
        }
    }

    void generateReverseSubstitutionTables() {
        for (auto& table : subTables) {
            std::vector<uint8_t> reverseTable(256);
            for (size_t i = 0; i < table.size(); ++i) {
                reverseTable[table[i]] = i;
            }
            reverseSubTables.push_back(reverseTable);
        }
    }

    // placeholder for encryption/decryption methods
    std::vector<uint8_t> encrypt(std::vector<uint8_t>& input) {
        for (int round = 0; round < 16; ++round) {
            for (int i = 0; i < 8; ++i) {
                input[i] ^= key[i];
                input[i] = subTables[i][input[i]];
            }
            rotateLeft(input);
        }
        return input;
    }
    std::vector<uint8_t> decrypt(std::vector<uint8_t> input) {
        for (int round = 0; round < 16; ++round) {
            rotateRight(input);
            for (int i = 0; i < 8; ++i) {
                input[i] = reverseSubTables[i][input[i]];
                input[i] ^= key[i];
            }
        }
        return input;
    }
};

