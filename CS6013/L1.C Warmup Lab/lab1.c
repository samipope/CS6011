/*********************************************************************
 *
 *Author: Samantha Pope
 *Class: CS 6013 Lab1
 *Date: January 12, 2024
 *Description: This is the implementation of the function definitions given to us in c code. A brief description of what the function does is given in each header comment.
 */

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h> // For strlen()
#include <assert.h> //assert library from ziz
#include <stdbool.h>


/*********************************************************************
 *
 * The C functions in this lab use patterns and functionality often found in
 * operating system code. Your job is to implement them.  Additionally, write some test
 * cases for each function (stick them in functions [called xyzTests(), etc or similar])
 * Call your abcTests(), etc functions in main().
 *
 * Write your own tests for each function you are testing, then share/combine
 * tests with a classmate.  Try to come up with tests that will break people's code!
 * Easy tests don't catch many bugs! [Note this is one specific case where you may
 * share code directly with another student.  The test function(s) from other students
 * must be clearly marked (add '_initials' to the function name) and commented with the
 * other student's name.
 *
 * Note: you may NOT use any global variables in your solution.
 *
 * Errata:
 *   - You can use global vars in your testing functions (if necessary).
 *   - Don't worry about testing the free_list(), or the draw_me() functions.
 *
 * You must compile in C mode (not C++).  If you compile from the commandline
 * run clang, not clang++. Submit your solution files on Canvas.
 *
 *********************************************************************/

/*********************************************************************
 *
 * byte_sort()
 *Author: Samantha Pope
 * specification: byte_sort() treats its argument as a sequence of
 * 8 bytes, and returns a new unsigned long integer containing the
 * same bytes, sorted numerically, with the smaller-valued bytes in
 * the lower-order byte positions of the return value
 *
 * EXAMPLE: byte_sort (0x0403deadbeef0201) returns 0xefdebead04030201
 * Ah, the joys of using bitwise operators!
 *
 * Hint: you may want to write helper functions for these two functions
 *
 *********************************************************************/

unsigned long byte_sort( unsigned long arg )
{
    
    unsigned char bytes [8];
      
      for(int i=0; i<8;i++){
        bytes[i] = (arg >>(i*8))&  0xFF;
      }
      
      
      for(int i=0;i<7;i++){
        for(int j=0;j<7-i;j++){
          if(bytes[j]>bytes[j+1]){
            unsigned char temp = bytes[j];
            bytes[j] = bytes[j+1];
            bytes[j+1] =temp;
          }
        }
      }
      
         unsigned long result = 0;
        for (int i = 0; i < 8; i++) {
            result |= ((unsigned long)bytes[i]) << (i * 8);
        }

        return result;
}

/*********************************************************************
 *
 * nibble_sort()
 *Author: Sami Pope
 * specification: nibble_sort() treats its argument as a sequence of 16 4-bit
 * numbers, and returns a new unsigned long integer containing the same nibbles,
 * sorted numerically, with smaller-valued nibbles towards the "small end" of
 * the unsigned long value that you return
 *
 * the fact that nibbles and hex digits correspond should make it easy to
 * verify that your code is working correctly
 *
 * EXAMPLE: nibble_sort (0x0403deadbeef0201) returns 0xfeeeddba43210000
 *
 *********************************************************************/

unsigned long nibble_sort (unsigned long arg)
{
    unsigned char nibbles[16];

        // extract each nibble
        for (int i = 0; i < 16; i++) {
            nibbles[i] = (arg >> (i * 4)) & 0xF;
        }

        // sort the nibbles - using bubble sort for demonstration
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15 - i; j++) {
                if (nibbles[j] > nibbles[j + 1]) {
                    // Swap nibbles
                    unsigned char temp = nibbles[j];
                    nibbles[j] = nibbles[j + 1];
                    nibbles[j + 1] = temp;
                }
            }
        }

        // recombine the nibbles
        unsigned long result = 0;
        for (int i = 0; i < 16; i++) {
            result |= ((unsigned long)nibbles[i]) << (i * 4);
        }

        return result;
    }

/*********************************************************************/

typedef struct elt {
  char val;
  struct elt *link;
} Elt;

/*********************************************************************/

/* Forward declaration of "free_list()"... This allows you to call   */
/* free_list() in name_list() [if you'd like].                       */
void free_list( Elt* head ); // [No code goes here!]

/*********************************************************************
 *
 * name_list()
 *Author: Sami Pope
 * specification: allocate and return a pointer to a linked list of
 * struct elts
 *
 * - the first element in the list should contain in its "val" field the first
 *   letter of your first name; the second element the second letter, etc.;
 *
 * - the last element of the linked list should contain in its "val" field
 *   the last letter of your first name and its "link" field should be a null
 *   pointer
 *
 * - each element must be dynamically allocated using a malloc() call
 *
 * - you must use the "name" variable (change it to be your name).
 *
 * Note, since we're using C, not C++ you can't use new/delete!
 * The analog to delete is the free() function
 *
 * - if any call to malloc() fails, your function must return NULL and must also
 *   free any heap memory that has been allocated so far; that is, it must not
 *   leak memory when allocation fails
 *
 * Implement print_list and free_list which should do what you expect.
 * Printing or freeing a nullptr should do nothing.
 *
 * Note: free_list() might be useful for error handling for name_list()...
 *
 *********************************************************************/

Elt *name_list()
{
    char *name = "Davison";
     Elt *head = NULL, *current = NULL, *previous = NULL;
     
     for (int i = 0; name[i] != '\0'; i++) {
         current = (Elt *)malloc(sizeof(Elt));
         if (current == NULL) {
             // If allocation fails, free the already allocated memory
             free_list(head);
             return NULL;
         }
         current->val = name[i];
         current->link = NULL;

         if (previous == NULL) {
             head = current; // Set head for the first element
         } else {
             previous->link = current;
         }
         previous = current;
     }
     return head;
}

/*********************************************************************/

void print_list( Elt* head )
{
    Elt *current = head;
      while (current != NULL) {
          printf("%c ", current->val);
          current = current->link;
      }
      printf("\n");
    
}

/*********************************************************************/

void free_list( Elt* head )
{
    Elt *current = head;
       Elt *temp;
       while (current != NULL) {
           temp = current;
           current = current->link;
           free(temp);
       }
}

/*********************************************************************
 *
 * draw_me()
 * Author: Sami Pope
 * This function creates a file called 'me.txt' which contains an ASCII-art
 * picture of you (it does not need to be very big).
 *
 * Use the C stdlib functions: fopen, fclose, fprintf, etc which live in stdio.h
 * - Don't use C++ iostreams
 *
 *********************************************************************/

void draw_me()
{FILE *file = fopen("me.txt", "w");
    
    if (file == NULL) {
        printf("Error opening file!\n");
        return;
    }

    const char *ascii_art[] = {
        "  ___  \n",
        " /' '\\n",
        "| o o |\n",
        "|  >  |\n",
        "|  0  |\n",
        " \\___/ \n"
    };

    for (int i = 0; i < sizeof(ascii_art) / sizeof(ascii_art[0]); i++) {
        fprintf(file, "%s", ascii_art[i]);
    }

    fclose(file);
}

/*********************************************************************
 *
 * Test Code - Place your test functions in this section:
 */

// bool testByteSort() { ... }
// ...
// ...
/*********************************************************************
 *
 * testByteSort_SP()
 * Author: Sami Pope
 * This function tests ByteSort and is written by me. I shared these with Ziz and Jessica
 *
 *********************************************************************/
void testByteSort_SP(){
    //given test by davison
        unsigned long test = 0x0403deadbeef0201;
        unsigned long sorted = byte_sort(test);
        assert(byte_sort(test)==sorted);
  
        // Test 2
        unsigned long test2 = 0xabcdef0123456789;
        unsigned long expected2 = 0xefcdab8967452301;
        assert(byte_sort(test2) == expected2);

        // Test 3
        unsigned long test3 = 0x0001020304050607;
        unsigned long expected3 = 0x0706050403020100;
        assert(byte_sort(test3) == expected3);

        // Test 4: All bytes are the same
        unsigned long test4 = 0x1111111111111111;
        unsigned long expected4 = 0x1111111111111111;
        assert(byte_sort(test4) == expected4);

}

/*********************************************************************
 *
 * testNibbleSort_SP()
 * Author: Sami Pope
 * This function tests NibbleSort and is written by me. I shared these with Ziz and Jessica
 *
 *********************************************************************/
void testNibbleSort_SP(){
    // given test by prof davison
    unsigned long test1 = 0x0403deadbeef0201;
    unsigned long expected1 = 0xfeeeddba43210000;
    assert(nibble_sort(test1) == expected1);

    // Test 2
    unsigned long test2 = 0x0000000000000000;
    unsigned long expected2 = 0x0000000000000000;
    assert(nibble_sort(test2) == expected2);

    // Test 3
    unsigned long test3 = 0x1111111111111111;
    unsigned long expected3 = 0x1111111111111111;
    assert(nibble_sort(test3) == expected3);

}

/*********************************************************************
 *
 * testNameList_SP()
 * Author: Sami Pope
 * This function tests NibbleSort and is written by me. I shared these with Ziz and Jessica
 *
 *********************************************************************/

void testNameList_SP(){
    Elt *list = name_list(); // Call without parameter
      const char *expected_name = "Davison";
      int i = 0;
      Elt *current = list;
      while (expected_name[i] != '\0' && current != NULL) {
          assert(current->val == expected_name[i]);
          current = current->link;
          i++;
      }
      assert(expected_name[i] == '\0' && current == NULL); // Ensure end of string and list are reached
      free_list(list);

}










/*********************************************************************
 *
    Below are Ziz's tests for the program since the assignment instructions required us to share with a classmate.
 *
 *
 *********************************************************************/

bool testByteSort_EF(){
  //the given example: (0x0403deadbeef0201) returns 0xefdebead04030201
  unsigned long input1 = 0x0403deadbeef0201;
  unsigned long test1 = byte_sort(input1);
  unsigned long expected1 = 0xefdebead04030201;
  assert(test1 == expected1);
  //test for a value of 0
  unsigned long input2 = 0;
  unsigned long test2 = byte_sort(input2);
  unsigned long expected2 = 0;
  assert(test2 == expected2);
  //test sorted reverse order
  unsigned long input3 = 0x01020304adbedeef;
  unsigned long test3 = byte_sort(input3);
  assert(test3 == expected1);
  //test already sorted
  unsigned long input4 = 0xefdebead04030201;
  unsigned long test4 = byte_sort(input4);
  assert(test4 == expected1);
  //test all 1s (avoid binary nonsense)
  unsigned long input5 = 0x1111111111111111;
  unsigned long test5 = byte_sort(input5);
  unsigned long expected5 = 0x1111111111111111;
  assert(test5 == expected5);
  return true;
}
bool testNibbleSort_EF(){
  //test 1
  //EXAMPLE: nibble_sort (0x0403deadbeef0201) returns 0xfeeeddba43210000
  unsigned long input1 = 0x0403deadbeef0201;
  unsigned long test1 = nibble_sort(input1);
  unsigned long expected1 = 0xfeeeddba43210000;
  assert(test1 == expected1);
  //test2 - a 0 input
  unsigned long input2 = 0;
  unsigned long test2 = nibble_sort(input2);
  unsigned long expected2 = 0;
  assert(test2 == expected2);
  //test another sort
  unsigned long input3 = 0x11110024fdabca56;
  unsigned long test3 = nibble_sort(input3);
  unsigned long expected3 = 0xfdcbaa6542111100;
  assert(test3 == expected3);
  //test reverse order
  unsigned long input4 = 0x0011112456aabcdf;
  unsigned long test4 = nibble_sort(input4);
  assert(test4 == expected3);
  //test already sorted
  unsigned long input5 = 0xfdcbaa6542111100;
  unsigned long test5 = nibble_sort(input5);
  assert(test5 == expected3);
  //test all 1's (binary nonsense)
  unsigned long input6 = 0x1111111111111111;
  unsigned long test6 = nibble_sort(input6);
  unsigned long expected6 = 0x1111111111111111;
  assert(test6 == expected6);
  return true;
}
bool testNameList_EF(){
  Elt* name = name_list();
  int index = 0;
  //to Sami and Jessica: put your name here
  char * correct = "Elisabeth";
  Elt* current_char = name;
  while(current_char->link != NULL){
    assert(current_char->val == correct[index]);
    index++;
    current_char = current_char->link;
  }
  free_list(name);
  return true;
}
bool testPrintList_EF(){
  //test a random list
  Elt* test1 = malloc(sizeof(Elt) );
  if(test1 == NULL){
    printf("ERROR, malloc failed\n");
    free_list(test1);
    exit(99);
  }
  test1->val = 'a';
  test1->link = NULL;
  print_list(test1);
  free_list(test1);
  //test with name
  Elt* name = name_list();
  print_list(name);
  free_list(name);
  //should do nothing
  print_list(NULL);
  return true;
}

/*********************************************************************
 *
 * main()
 *
 * The main driver program.  You can place your main() method in this
 * file to make compilation easier, or have it in a separate file.
 *
 *
 *********************************************************************/

int main()
{
  
    draw_me();
    
    //running test functions written by Sami Pope (me)
    testByteSort_SP();
    testNibbleSort_SP();
    testNameList_SP();
    
    //running test functions written by Ziz (partner)
    
    testByteSort_EF();
    testNibbleSort_EF();
    testNameList_EF();
    testPrintList_EF();
        
    return 0;
    
}
