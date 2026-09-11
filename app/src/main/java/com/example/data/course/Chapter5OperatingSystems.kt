package com.example.data.course

import com.example.model.*

object Chapter5OperatingSystems {
    val chapter = ChapterItem(
        id = 5,
        number = 5,
        title = "Operating Systems & System Software",
        summary = "Operating system functions, kernel, process management, memory paging, file systems, and user interfaces (GUI vs CLI).",
        iconName = "settings_suggest",
        topics = listOf(
            TopicNote(
                topicId = "ch5_top1",
                chapterId = 5,
                chapterTitle = "Operating Systems & System Software",
                title = "Operating System Functions, Architecture & Process Management",
                definition = "An Operating System (OS) is core system software that acts as an intermediary bridge between user applications and raw hardware, managing processor scheduling, memory allocation, storage files, and peripheral I/O.",
                easyExplanation = "Think of an Operating System as the principal and administration of a school. Students (apps) want classrooms (RAM) and teachers (CPU time). Without a principal scheduling who uses what room, there would be chaos and clashes. The OS keeps everything running peacefully.",
                detailedExplanation = "The core engine of the OS is the Kernel, which executes in privileged CPU Ring 0 (Kernel Mode) with unrestricted hardware access. User software runs in unprivileged Ring 3 (User Mode) and must execute System Calls (syscalls) to request kernel services. Key subsystems include:\n1. Process Management: Handles process creation, context switching, multitasking, and scheduling algorithms (Round Robin, FCFS, Priority).\n2. Memory Management: Allocates RAM pages, prevents process memory overwriting, and orchestrates Virtual Memory (paging swap file on disk when RAM is full).\n3. File System Management: Organizes files into directories, tracks metadata, and manages storage sectors (NTFS, FAT32, ext4, APFS).\n4. Device Management: Coordinates device drivers and buffering for printers, graphics cards, and input controllers.",
                realWorldExample = "Multitasking on your smartphone: You are listening to Spotify in the background while texting on WhatsApp and downloading a PDF in Chrome. The Android OS kernel rapidly schedules CPU time slices among all three apps so all three appear to run smoothly at the same time.",
                technicalExample = "A C program calling `fopen('notes.txt', 'r')`: The code issues an interrupt `int 0x80` or `syscall` instruction. The CPU switches from User Mode to Kernel Mode. The kernel checks file permissions, reads disk sectors via device driver, loads bytes into RAM buffer, and returns a file descriptor.",
                importantPoints = listOf(
                    "Kernel is the heart of the operating system, permanently resident in RAM.",
                    "GUI (Graphical User Interface) provides visual icons, windows, and mouse clicks.",
                    "CLI (Command Line Interface) requires typing exact textual commands (e.g. bash, cmd, PowerShell).",
                    "Virtual Memory allows computers to run programs larger than available physical RAM by swapping pages to disk."
                ),
                keyTerms = listOf(
                    "Kernel" to "The fundamental central core of an OS that manages hardware resources directly in supervisor mode.",
                    "Process" to "A program in active execution loaded into memory with its own stack, heap, and registers.",
                    "Multitasking" to "The ability of an OS to execute multiple software tasks concurrently by sharing CPU time.",
                    "Virtual Memory" to "A memory management technique where secondary storage space is used as an extension of RAM."
                ),
                applications = listOf(
                    "Personal computing: Windows 11, macOS Sequoia, Ubuntu Linux.",
                    "Mobile operating systems: Android OS, Apple iOS.",
                    "Real-Time Embedded Systems (RTOS): Automotive ABS brakes, aircraft flight instruments, pacemaker control."
                ),
                advantages = listOf(
                    "Hardware Abstraction: Developers write code once without worrying about specific motherboard brands.",
                    "Memory Protection: A crashed application cannot overwrite or corrupt another program's private memory.",
                    "Security & Access Control: Enforces passwords, file permissions, and user accounts."
                ),
                disadvantages = listOf(
                    "Resource Overhead: The OS consumes significant RAM and processor cycles just to maintain background services.",
                    "System Crash Impact: If the OS kernel crashes (Kernel Panic / Blue Screen of Death), all running programs die."
                ),
                typesCategories = listOf(
                    "Single-User Single-Tasking: MS-DOS (only one user, one program at a time).",
                    "Single-User Multitasking: Windows, macOS (one user running multiple concurrent apps).",
                    "Multi-User / Time-Sharing: Linux/Unix servers (hundreds of remote users logged in simultaneously).",
                    "Real-Time OS (RTOS): QNX, FreeRTOS (guaranteed response within strict microsecond deadlines)."
                ),
                comparisons = listOf(
                    "GUI vs CLI" to "GUI (Graphical User Interface) is user-friendly, uses windows/icons/mouse, consumes more RAM; CLI (Command Line Interface) is fast, lightweight, requires memorizing text commands, favored by system administrators.",
                    "Process vs Thread" to "A process is an independent executing program with its own isolated memory space; a thread is a lightweight sub-unit of execution within a process that shares memory with sibling threads."
                ),
                commonMistakes = listOf(
                    "Believing that an OS is an application program: The OS is foundational System Software.",
                    "Thinking Virtual Memory makes a computer faster: Virtual memory prevents out-of-memory crashes, but excessive swapping ('thrashing') drastically slows down performance because disks are slower than RAM."
                ),
                examFocusedPoints = listOf(
                    "State and explain the four primary functions of an operating system (Process, Memory, File, Device management).",
                    "Compare GUI and CLI with at least four distinct comparative differences.",
                    "Explain the concept of Virtual Memory and why it is essential."
                ),
                shortQuestions = listOf(
                    QuestionAnswer("What is an Operating System?", "An Operating System is essential system software that acts as an interface between computer hardware and user software, managing CPU, memory, files, and devices."),
                    QuestionAnswer("What is the difference between GUI and CLI?", "GUI uses graphical icons, windows, and mouse input (easy to learn, visually rich). CLI uses textual commands typed into a terminal prompt (lightweight, powerful, requires memorizing commands)."),
                    QuestionAnswer("What is Virtual Memory?", "Virtual Memory is a technique that uses a portion of secondary storage (SSD/HDD) as an extension of physical RAM, allowing systems to run large applications even when RAM is insufficient.")
                ),
                longQuestions = listOf(
                    QuestionAnswer(
                        "Explain in detail the major functions performed by an operating system in a modern computer.",
                        "The primary functions of an OS include:\n1. Processor / Process Management: Allocates CPU time slices to active processes using scheduling algorithms and handles context switching.\n2. Memory Management: Keeps track of every memory byte, allocates and deallocates memory dynamically, and manages virtual memory paging.\n3. File System Management: Organizes files into directories/folders, maintains file access permissions, and handles storage allocation (NTFS, ext4).\n4. Device / I/O Management: Communicates with peripheral devices through device drivers, buffering, and spooling.\n5. Security & Protection: Enforces authentication (passwords, biometrics) and prevents unauthorized access to user data."
                    )
                ),
                mcqs = listOf(
                    McqItem("mcq_ch5_1", "What is the central core component of an Operating System called?", listOf("Shell", "Kernel", "Compiler", "BIOS"), 1, "The kernel is the foundational core of the OS with complete control over hardware."),
                    McqItem("mcq_ch5_2", "Which operating system interface is entirely text-based and command-driven?", listOf("GUI", "CLI", "Touch UI", "Voice UI"), 1, "CLI (Command Line Interface) requires typing textual commands in a shell prompt."),
                    McqItem("mcq_ch5_3", "The technique of using secondary storage as an extension of RAM is called:", listOf("Cache Memory", "Virtual Memory", "ROM", "Buffer Memory"), 1, "Virtual Memory swaps pages between physical RAM and disk storage.")
                ),
                practiceQuestions = listOf(
                    "Describe the state transition diagram of a process (New -> Ready -> Running -> Waiting -> Terminated).",
                    "Why do mission-critical medical devices and space shuttles use RTOS rather than standard consumer OS?"
                ),
                revisionSummary = "The Operating System manages hardware and software. The Kernel runs at the lowest level. Major OS functions: Process, Memory, File, and Device management. GUI is user-friendly; CLI is efficient and scriptable.",
                flashcards = listOf(
                    FlashcardItem("fc_ch5_1", 5, "Operating Systems", "What is the Kernel?", "The central core component of the OS that directly interacts with and manages computer hardware."),
                    FlashcardItem("fc_ch5_2", 5, "Operating Systems", "What does GUI stand for?", "Graphical User Interface.")
                ),
                relatedConcepts = listOf("Kernel", "Process", "Virtual Memory", "File System", "System Software"),
                visualDiagram = """
+-------------------------------------------------------+
|              OPERATING SYSTEM ARCHITECTURE            |
+-------------------------------------------------------+
|  [ User Applications ] (Browser, Word, Games, Python) |
|                          |                            |
|             --- System Call Interface ---             |
|                          v                            |
|  [ OS KERNEL ]                                        |
|  * Process Scheduler  * Memory & Virtual Memory Mgr   |
|  * File System Mgr    * Device Drivers & I/O Buffers  |
|                          |                            |
|             --- Hardware Abstraction Layer ---        |
|                          v                            |
|  [ PHYSICAL HARDWARE ] (CPU, RAM, Disks, Network NIC) |
+-------------------------------------------------------+
                """.trimIndent()
            )
        )
    )
}
