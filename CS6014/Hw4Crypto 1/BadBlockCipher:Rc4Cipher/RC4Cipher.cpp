//
// Created by Samantha Pope on 2/22/24.
//

#include <vector>
#include <string>

class RC4 {
private:
    std::vector<int> S;
    int i, j;

    void swap(int &a, int &b) {
        int temp = a;
        a = b;
        b = temp;
    }

public:
    RC4(const std::string &key) : i(0), j(0), S(256) {
        for (int k = 0; k < 256; ++k) S[k] = k;

        int j = 0;
        for (int i = 0; i < 256; ++i) {
            j = (j + S[i] + key[i % key.length()]) % 256;
            swap(S[i], S[j]);
        }
    }

    char getByte() {
        i = (i + 1) % 256;
        j = (j + S[i]) % 256;
        swap(S[i], S[j]);
        return S[(S[i] + S[j]) % 256];
    }

    std::string encryptDecrypt(const std::string &input) {
        std::string output = input;
        for (size_t k = 0; k < input.size(); ++k) {
            output[k] ^= getByte();
        }
        return output;
    }
};


