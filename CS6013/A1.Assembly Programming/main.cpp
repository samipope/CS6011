#include <stdio.h>
#include <iostream>
#include <sys/time.h>

extern "C"{
	struct timeval myGTOD(); 
	int myPuts(const char* s, int len);
	void sayHello();
}


int main() {
    std::cout << "MyPuts output:  " << std::endl;
    myPuts("Hello!", 6);
           
    std::cout << "sayHello output:  " <<std::endl;
    sayHello();

     timeval tv = myGTOD();
     timeval tv1;
    gettimeofday(&tv1,0);
    std::cout << "myGTOD Seconds: " << tv.tv_sec << ", Microseconds: " << tv.tv_usec << std::endl;
    std::cout << "gettimeofday: Seconds = " << tv1.tv_sec << ", Microseconds = " << tv1.tv_usec << std::endl;

    return 0;
}
