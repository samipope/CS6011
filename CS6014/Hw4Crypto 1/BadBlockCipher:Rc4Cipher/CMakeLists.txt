cmake_minimum_required(VERSION 3.27)
project(HW4Crypto1)

set(CMAKE_CXX_STANDARD 17)

add_executable(HW4Crypto1 main.cpp
        badBlockCipher.cpp
        RC4Cipher.cpp)
