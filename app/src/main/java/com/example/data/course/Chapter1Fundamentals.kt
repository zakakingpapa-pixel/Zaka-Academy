package com.example.data.course

import com.example.model.*

object Chapter1Fundamentals {
    val chapter = ChapterItem(
        id = 1,
        number = 1,
        title = "Fundamentals of Computer & Systems",
        summary = "Introduction to computer systems, historical generations, hardware vs software, IPO cycle, and classification.",
        iconName = "computer",
        topics = listOf(
            TopicNote(
                topicId = "ch1_top1",
                chapterId = 1,
                chapterTitle = "Fundamentals of Computer & Systems",
                title = "Computer System & The Information Processing Cycle (IPO)",
                definition = "A computer system is an electronic, programmable data-processing machine that accepts raw data as input, manipulates and processes it according to stored program instructions, produces meaningful information as output, and stores results for future retrieval.",
                easyExplanation = "Think of a computer like an automatic juice bar. You feed fresh oranges in (Input), the motorized blades squeeze and strain them (Processing), delicious fresh juice pours into your glass (Output), and extra bottles are kept in the refrigerator (Storage).",
                detailedExplanation = "The fundamental operation of all modern digital computers adheres to the Input-Process-Output-Storage (IPOS) cycle. In the Input stage, peripheral devices convert human-understandable signals into digital binary bit-streams (0s and 1s). In the Processing stage, the Central Processing Unit (ALU, CU, Registers) fetches instructions from RAM, decodes opcodes, and performs arithmetic or logical operations at gigahertz clock speeds. In the Output stage, processed binary results are translated back into perceptible human forms (visual pixels, sound waves, printed paper). In the Storage stage, data is persisted magnetically, optically, or in NAND flash non-volatile memory.",
                realWorldExample = "Scanning a barcode at a grocery store checkout: The laser scanner reads the barcode (Input), the POS computer queries the inventory database and calculates the price with tax (Processing), the monitor displays the price and a paper receipt prints (Output), while the sales transaction is logged to the server disk (Storage).",
                technicalExample = "A Python script executing `x = int(input())`; CPU reads keyboard buffer into register EAX, executes `ADD EAX, 10`, and sends string characters to stdout buffer via system call `sys_write`.",
                importantPoints = listOf(
                    "Raw, unorganized facts and figures are called Data; processed, meaningful data is called Information.",
                    "Computers operate strictly under the GIGO principle: Garbage In, Garbage Out.",
                    "The four primary functional stages are Input, Processing, Output, and Storage (IPOS).",
                    "A computer system is a synergy of Hardware, Software, Data, People, and Procedures."
                ),
                keyTerms = listOf(
                    "Data" to "Unprocessed, raw facts, figures, and symbols without contextual meaning.",
                    "Information" to "Processed, structured, and organized data that carries meaning and aids decision making.",
                    "IPO Cycle" to "The sequence of Input, Processing, and Output followed by all digital computers.",
                    "GIGO" to "Garbage In, Garbage Out; the accuracy of computer output depends directly on input accuracy."
                ),
                applications = listOf(
                    "Automated banking systems and ATM transactions.",
                    "Hospital patient monitoring and diagnostic imaging.",
                    "Scientific weather forecasting and climate modeling.",
                    "Aviation flight control and air traffic management."
                ),
                advantages = listOf(
                    "High Speed: Executes billions of calculations per second without fatigue.",
                    "Accuracy: Produces consistently error-free results when given correct instructions.",
                    "Diligence: Can perform monotonous, repetitive tasks endlessly without losing concentration.",
                    "Vast Storage: Stores millions of books, datasets, and multimedia in compact solid-state chips."
                ),
                disadvantages = listOf(
                    "Lack of Common Sense: Incapable of independent thinking or emotional judgment without programming.",
                    "Dependency on Electricity: Cannot operate without continuous power supplies or battery backup.",
                    "Cyber Vulnerabilities: Susceptible to malware, viruses, and unauthorized data breaches."
                ),
                typesCategories = listOf(
                    "Supercomputers: Fastest, most powerful systems for scientific research (e.g. Frontier, Summit).",
                    "Mainframe Computers: High-throughput systems handling massive concurrent enterprise transactions.",
                    "Minicomputers / Midrange: Multi-user departmental systems.",
                    "Microcomputers / Personal Computers: Desktop PCs, laptops, tablets, and smartphones designed for individual use."
                ),
                comparisons = listOf(
                    "Data vs Information" to "Data is raw, unorganized, and meaningless on its own (e.g., 'Ali, 95, 82'); Information is organized and contextualized (e.g., 'Ali scored 95% in Computer Science and ranked 1st in class').",
                    "Hardware vs Software" to "Hardware is physical and tangible (can touch, subject to wear); Software is digital instructions and intangible (cannot touch physically, updated easily)."
                ),
                commonMistakes = listOf(
                    "Confusing Data with Information: Data is raw input; Information is processed output.",
                    "Believing computers have human-like consciousness: Computers only follow programmed binary logic."
                ),
                examFocusedPoints = listOf(
                    "Be prepared to draw and label the block diagram of a computer system showing Input, CPU (ALU, CU, Registers), Memory, and Output.",
                    "State the clear distinction between Data and Information with at least two real-life examples.",
                    "Memorize the full form and definition of GIGO (Garbage In, Garbage Out)."
                ),
                shortQuestions = listOf(
                    QuestionAnswer("What is meant by the IPO cycle?", "The IPO (Input-Process-Output) cycle is the fundamental operational sequence where a computer accepts raw data (Input), executes program instructions on it via the CPU (Processing), and produces meaningful results (Output)."),
                    QuestionAnswer("Differentiate between data and information.", "Data represents raw, unprocessed facts and figures without context (e.g. 45, 92). Information is processed, structured data that carries meaningful context (e.g. Student's average score is 68.5%)."),
                    QuestionAnswer("What is the GIGO principle?", "GIGO stands for 'Garbage In, Garbage Out'. It signifies that if incorrect or corrupted data is entered into a computer, the resulting output will inevitably be incorrect.")
                ),
                longQuestions = listOf(
                    QuestionAnswer(
                        "Explain the basic architecture and functional units of a digital computer with a neat diagram.",
                        "A digital computer consists of four major functional units:\n1. Input Unit: Captures data from external sources and transforms it into binary signals (keyboard, mouse, scanner).\n2. Central Processing Unit (CPU): Contains the Arithmetic Logic Unit (ALU) for calculations, Control Unit (CU) to manage instruction execution, and Registers for high-speed temporary storage.\n3. Primary and Secondary Storage Unit: RAM and ROM for active execution; SSD/HDD for long-term data persistence.\n4. Output Unit: Translates binary results into human-readable visual, audio, or physical media (monitors, printers)."
                    )
                ),
                mcqs = listOf(
                    McqItem("mcq_ch1_1", "What does GIGO stand for in computer science?", listOf("Good Input Good Output", "Garbage In Garbage Out", "Global Internet Gateway Output", "General Input General Output"), 1, "GIGO emphasizes that the accuracy of computer output relies entirely on valid input."),
                    McqItem("mcq_ch1_2", "Raw, unprocessed facts and figures are scientifically known as:", listOf("Information", "Knowledge", "Data", "Wisdom"), 2, "Data refers to raw unorganized facts before computational processing."),
                    McqItem("mcq_ch1_3", "Which functional unit of the computer performs mathematical additions and comparisons?", listOf("Control Unit", "Arithmetic Logic Unit (ALU)", "ROM", "Output Unit"), 1, "The ALU is responsible for all arithmetic calculations (+, -, *, /) and logical comparisons (<, >, ==).")
                ),
                practiceQuestions = listOf(
                    "Identify the input, processing, and output steps in an automated fingerprint attendance machine.",
                    "Why is computer diligence considered superior to human endurance in industrial assembly lines?"
                ),
                revisionSummary = "A computer system executes the IPOS (Input, Process, Output, Storage) cycle. Raw data is converted into structured information by the CPU. Computers are fast, accurate, and diligent, but lack independent intuition.",
                flashcards = listOf(
                    FlashcardItem("fc_ch1_1", 1, "Computer System", "What are the 4 fundamental stages of the IPOS cycle?", "Input, Processing, Output, and Storage."),
                    FlashcardItem("fc_ch1_2", 1, "Computer System", "What is the primary difference between Data and Information?", "Data is raw facts; Information is processed, meaningful data.")
                ),
                relatedConcepts = listOf("CPU", "Hardware", "Operating System", "Von Neumann Architecture"),
                visualDiagram = """
+-------------------------------------------------------+
|                THE COMPUTER SYSTEM CYCLE              |
+-------------------------------------------------------+
|                                                       |
|   [ INPUT ]   ====>   [ CPU PROCESSING ]   ====> [ OUTPUT ]
|  (Keyboard,          (ALU + Control Unit +       (Monitor,
|   Mouse, Mic)           Internal Registers)       Printer)
|                                ^                      |
|                                |                      |
|                                v                      |
|                       [ STORAGE / MEMORY ]            |
|                        (RAM, Cache, SSD)              |
+-------------------------------------------------------+
                """.trimIndent()
            )
        )
    )
}
