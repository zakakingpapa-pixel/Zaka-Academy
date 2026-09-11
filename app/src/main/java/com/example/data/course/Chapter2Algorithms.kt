package com.example.data.course

import com.example.model.*

object Chapter2Algorithms {
    val chapter = ChapterItem(
        id = 2,
        number = 2,
        title = "Computational Thinking & Algorithms",
        summary = "Problem solving, step-by-step algorithm design, standard flowchart symbols, traceability, and efficiency.",
        iconName = "account_tree",
        topics = listOf(
            TopicNote(
                topicId = "ch2_top1",
                chapterId = 2,
                chapterTitle = "Computational Thinking & Algorithms",
                title = "Algorithm Design & Flowchart Representation",
                definition = "An algorithm is a finite, ordered sequence of unambiguous and executable steps designed to solve a specific problem. A flowchart is its standardized graphical diagrammatic representation.",
                easyExplanation = "An algorithm is like a step-by-step navigation map on Google Maps. If you follow turn-by-turn instructions accurately, you will always reach your destination. A flowchart is the same journey drawn visually with road signs, turns, and checkpoints.",
                detailedExplanation = "Algorithmic computational thinking involves four pillars: Decomposition (breaking complex problems into smaller subproblems), Pattern Recognition (finding regularities), Abstraction (focusing only on essential details while ignoring irrelevancies), and Algorithm Design (creating step-by-step logic). Standard flowchart symbols governed by ISO/ANSI include: Oval (Terminator for Start/End), Parallelogram (Input/Output data transfer), Rectangle (Processing, calculations, and assignments), Diamond (Decision/Conditional branching with boolean true/false paths), and Flowlines (arrows denoting direction of control).",
                realWorldExample = "ATM Cash Withdrawal: Start -> Insert Debit Card -> Enter 4-digit PIN -> Is PIN correct? If No, alert user and eject card. If Yes -> Enter withdrawal amount -> Is balance >= amount? If Yes, dispense cash, deduct balance, print receipt -> Stop.",
                technicalExample = "Finding maximum of three numbers A, B, and C:\nStep 1: Start\nStep 2: Read A, B, C\nStep 3: If A >= B and A >= C, Max = A\nStep 4: Else if B >= C, Max = B\nStep 5: Else Max = C\nStep 6: Display Max\nStep 7: Stop",
                importantPoints = listOf(
                    "Finiteness: An algorithm must always terminate after a countable number of execution steps.",
                    "Definiteness: Each step must be precisely defined without ambiguity.",
                    "Input & Output: Must take 0 or more inputs and generate at least 1 verifiable output.",
                    "Language Independent: Can be implemented equally in Python, C++, Java, or Kotlin."
                ),
                keyTerms = listOf(
                    "Algorithm" to "A finite sequence of clear, step-by-step instructions to solve a problem.",
                    "Flowchart" to "A diagrammatic representation of an algorithm using standard geometric symbols.",
                    "Pseudocode" to "An informal, high-level description of an algorithm combining English words with programming structures.",
                    "Decision Box" to "A diamond-shaped flowchart symbol that tests a boolean condition and splits execution paths."
                ),
                applications = listOf(
                    "Search engines sorting and indexing billions of web pages (e.g. PageRank).",
                    "E-commerce recommendation systems suggesting related products.",
                    "Encryption protocols securing financial data over banking networks.",
                    "Route optimization in logistics and autonomous vehicle navigation."
                ),
                advantages = listOf(
                    "Clear Logic: Uncovers logical bugs and corner-cases before any code is typed.",
                    "Language Portability: One algorithm can be coded into any programming language.",
                    "Visual Accessibility: Flowcharts allow non-programmers and domain experts to verify system rules."
                ),
                disadvantages = listOf(
                    "Maintenance Complexity: Modifying large, intricate flowcharts can require redrawing entire sheets.",
                    "Time Consuming: Drawing exhaustive flowcharts for massive enterprise software is often impractical."
                ),
                typesCategories = listOf(
                    "Sequential Algorithms: Steps execute strictly one after another from top to bottom.",
                    "Conditional / Branching Algorithms: Execution paths diverge based on condition evaluation (if-else).",
                    "Iterative / Looping Algorithms: Repeat a block of steps until a boundary condition is met (while, for)."
                ),
                comparisons = listOf(
                    "Algorithm vs Program" to "An algorithm is conceptual logic written in plain human language or pseudocode; a program is that algorithm translated into concrete syntax of a specific programming language executable by a computer.",
                    "Flowchart vs Pseudocode" to "Flowchart is visual/diagrammatic using geometric shapes; Pseudocode is textual using structured English keywords."
                ),
                commonMistakes = listOf(
                    "Using a rectangular box for Input/Output (Input/Output MUST strictly be inside a parallelogram).",
                    "Forgetting terminal conditions, leading to infinite loops where an algorithm never stops.",
                    "Leaving decision diamonds without labeling 'Yes'/'No' or 'True'/'False' branches."
                ),
                examFocusedPoints = listOf(
                    "Always memorize the exact names and shapes of the 5 core flowchart symbols: Terminator, Input/Output, Process, Decision, Flowline.",
                    "Practice writing clean step-by-step algorithms for: sum of N numbers, finding largest of three numbers, and checking even/odd.",
                    "Ensure arrows point in the correct direction of execution flow."
                ),
                shortQuestions = listOf(
                    QuestionAnswer("State three key properties of an algorithm.", "Three key properties are: 1. Finiteness (must stop after finite steps), 2. Definiteness (each instruction is clear and unambiguous), and 3. Effectiveness (all steps are practically doable)."),
                    QuestionAnswer("Which flowchart symbol is used for testing conditions?", "The Diamond symbol (Decision Box) is used for testing conditions, having one incoming flowline and two outgoing branches labeled True/False or Yes/No."),
                    QuestionAnswer("Differentiate between an algorithm and pseudocode.", "An algorithm is the general conceptual step-by-step solution. Pseudocode is a specific textual format of writing algorithms mimicking code structure with indented loops and conditionals.")
                ),
                longQuestions = listOf(
                    QuestionAnswer(
                        "Explain the significance of flowcharts in software engineering and describe five standard flowchart symbols with their shapes and functions.",
                        "Flowcharts provide a visual blueprint of program logic, enabling engineers to trace algorithmic pathways, identify infinite loops, and communicate system processes across teams.\n\nFive standard symbols:\n1. Oval (Terminator): Indicates START or STOP of the program.\n2. Parallelogram: Represents INPUT (e.g. Read A, B) or OUTPUT (e.g. Print Sum).\n3. Rectangle: Represents PROCESSING or computation (e.g. Sum = A + B).\n4. Diamond: Represents DECISION making with conditional branching (e.g. Is A > B?).\n5. Arrowed Line (Flowline): Shows the exact direction of program control flow."
                    )
                ),
                mcqs = listOf(
                    McqItem("mcq_ch2_1", "Which symbol in a flowchart represents an input or output operation?", listOf("Rectangle", "Oval", "Parallelogram", "Diamond"), 2, "A parallelogram is strictly reserved for Input and Output statements."),
                    McqItem("mcq_ch2_2", "A decision box in a flowchart has the geometric shape of a:", listOf("Circle", "Diamond", "Square", "Triangle"), 1, "The diamond represents conditional decision making with two or more branch outcomes."),
                    McqItem("mcq_ch2_3", "An algorithm must always terminate after a countable number of steps. This property is known as:", listOf("Definiteness", "Finiteness", "Effectiveness", "Generality"), 1, "Finiteness guarantees the algorithm will not loop endlessly and will conclude.")
                ),
                practiceQuestions = listOf(
                    "Design a step-by-step algorithm to calculate the simple interest: Interest = (P * R * T) / 100.",
                    "Draw the complete flowchart logic to determine whether a given positive integer is Prime or Composite."
                ),
                revisionSummary = "Algorithms are finite, unambiguous procedural solutions. Flowcharts visually map these solutions using standard symbols: Oval (Start/Stop), Parallelogram (I/O), Rectangle (Process), and Diamond (Decision).",
                flashcards = listOf(
                    FlashcardItem("fc_ch2_1", 2, "Algorithms", "What does a Parallelogram represent in a flowchart?", "Input or Output operations (e.g. Read X, Print Result)."),
                    FlashcardItem("fc_ch2_2", 2, "Algorithms", "What is the Finiteness property of an algorithm?", "The algorithm must guarantee termination after a finite number of steps.")
                ),
                relatedConcepts = listOf("Flowchart", "Pseudocode", "Big-O Notation", "Control Structures"),
                visualDiagram = """
       ( START )
           |
           v
    [/ Input Marks /]
           |
           v
       < Marks >= 50? >
        /             \
    [Yes]             [No]
      |                 |
      v                 v
[/ Print "PASS" /]   [/ Print "FAIL" /]
      \                 /
       \               /
        -----> ( STOP ) <-----
                """.trimIndent()
            )
        )
    )
}
