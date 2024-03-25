
address_of_success = 0x0000000100003de0

password = b'A' * 24  # Fill the buffer.
password += (address_of_success).to_bytes(8, byteorder='little')

with open('password.txt', 'wb') as f:
    f.write(password)

