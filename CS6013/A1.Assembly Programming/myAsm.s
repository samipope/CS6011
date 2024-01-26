extern puts
section .rodata
helloString: db "hello",0


section .txt ; this says that we're about to write code instead of data
global sayHello ; this says that "sayHello is a symbol that the linker needs to know about
global myPuts ;again, making linker recognize symbol
global myGTOD ; linker recognize


sayHello:;function
	mov rdi, helloString ; move address of helloString into RDI
	call puts ;call the puts function
	ret ;return

myPuts: 
 ; Prepare arguments for the write syscall
    mov rax, 1          ; System call number for write (1)
    mov rdx, rsi
    mov rsi, rdi
    mov rdi, 1 ; "just how it is"
    ; Make the system call
    syscall
    ; The return value is already in RAX, so just return
    ret

myGTOD:
;seconds in the rax, microseconds in the rdx
; Allocate space for timeval struct (16 bytes)
    sub rsp, 16

; Setup arguments for gettimeofday syscall
;int gettimeofday(timeval*tv, timezone*tz)
    mov rax, 96         ; System call number for gettimeofday
    mov rdi, rsp  ;timeval*tv
    mov rsi, 0 ;timezone*tz we put in a null pointer here


    ; Make the system call
    syscall

    ; Move the timeval values into rax and rdx
    mov rax, [rsp]      ; Move tv_sec into rax
    mov rdx, [rsp + 8]  ; Move tv_usec into rdx
    ; Clean up the stack by adding in the space we took away originally
    add rsp, 16

    ; Return with ret (rax and rdx have the return values)
    ret
