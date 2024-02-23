#include <iostream>
#include "random"
#include <iomanip>
#include "badBlockCipher.cpp"
#include "RC4Cipher.cpp"


void printBytes(const std::vector<uint8_t>& bytes) {
    for (uint8_t byte : bytes) {
        std::cout << std::hex << std::setfill('0') << std::setw(2) << static_cast<int>(byte) << " ";
    }
    std::cout << std::dec << std::endl; // Switch back to decimal for any future prints
}


int main() {
    /**
     * programming part 1: Bad Block cipher
     */
    // The password used for encryption and decryption
    std::string password = "mySecret";
    // Create an instance of the cipher with the given password
    badBlockCipher* cipher = new badBlockCipher(password);
    // Example message to encrypt (must be 8 bytes for this simple cipher)
    std::vector<uint8_t> message = {'H', 'e', 'l', 'l', 'o', '!', '!', '!'};
    std::cout << "Bad Block Cipher: OG message: ";
    printBytes(message);
    // Encrypt the message
    std::vector<uint8_t> encryptedMessage = cipher->encrypt(message);
    std::cout << "Bad Block Cipher: Encrypted message: ";
    printBytes(encryptedMessage);
    // Decrypt the message
    std::vector<uint8_t> decryptedMessage = cipher->decrypt(encryptedMessage);
    std::cout << "Bad Block Cipher: Decrypted message: ";
    printBytes(decryptedMessage);
    delete cipher;



    /**
     * programming part 2: RC4Cipher
     */
    RC4 rc4Encrypt("SecretKey");
    std::string originalText = "Hello, World!";
    std::string encryptedText = rc4Encrypt.encryptDecrypt(originalText);
    RC4 rc4Decrypt("SecretKey");
    std::string decryptedText = rc4Decrypt.encryptDecrypt(encryptedText);
    std::cout << "RC4 Cipher: Original: " << originalText << std::endl;
    std::cout << "RC4 Cipher: Encrypted: ";
    for (char c : encryptedText) std::cout << std::hex << (int)c << std::endl;
    std::cout << std::endl;
    std::cout << "RC4 Cipher: Decrypted: " << decryptedText << std::endl;
    RC4 rc4WrongKey("WrongKey");
    std::string wrongDecryptedText = rc4WrongKey.encryptDecrypt(encryptedText);
    std::cout << "RC4 Cipher: Decrypted with wrong key: " << wrongDecryptedText << std::endl;

    return 0;
}


