package com.example.data.course

import com.example.model.*

object Chapter8Programming {
    val chapter = ChapterItem(
        id = 8,
        number = 8,
        title = "Programming & Logic in Python",
        summary = "Python syntax, variables, basic data types, operators, if-elif-else conditionals, while and for loops, functions, and debugging.",
        iconName = "terminal",
        topics = listOf(
            TopicNote(
                topicId = "ch8_top1",
                chapterId = 8,
                chapterTitle = "Programming & Logic in Python",
                title = "Python Fundamentals: Variables, Control Structures & Loops",
                definition = "Computer programming is the process of designing, writing, testing, and debugging source code instructions in a formal language (such as Python) that a computer executes to perform specific computations.",
                easyExplanation = "Writing a Python program is like writing an exact instruction manual for a robot. The robot will do EXACTLY what you write down. If you tell it: 'Take 5 steps, check if the door is open, and if open, walk inside', the robot executes those actions with zero confusion.",
                detailedExplanation = "Python is a modern high-level, interpreted, dynamically typed language created by Guido van Rossum. Fundamental programming constructs include:\n1. Variables & Types: Named storage locations in RAM; standard data types include Integer (`int`), Floating-point (`float`), String (`str`), and Boolean (`bool`).\n2. Operators: Arithmetic (`+`, `-`, `*`, `/`, `//` floor division, `%` modulo remainder), Comparison (`==`, `!=`, `<`, `>`, `<=`, `>=`), and Logical (`and`, `or`, `not`).\n3. Conditional Statements: `if`, `elif`, `else` branch execution paths based on boolean evaluations.\n4. Iteration / Loops: `for` loops iterate over sequences or ranges (e.g. `for i in range(1, 11):`), and `while` loops repeat code as long as a condition evaluates to True.\n5. Functions: Reusable, modular code blocks declared with `def name(parameters):` that return values using `return`.\n6. Error Debugging: Syntax errors (violating grammar rules), Runtime errors (ZeroDivisionError, IndexError), and Logic errors (program runs but produces incorrect mathematical output).",
                realWorldExample = "Grading student marks in Python:\n```python\nmarks = int(input('Enter marks: '))\nif marks >= 80:\n    grade = 'A-1'\nelif marks >= 70:\n    grade = 'A'\nelif marks >= 60:\n    grade = 'B'\nelse:\n    grade = 'Needs Improvement'\nprint(f'Your grade is: {grade}')\n```",
                technicalExample = "Sum of numbers 1 to 10 using a while loop:\n```python\ntotal = 0\ncounter = 1\nwhile counter <= 10:\n    total += counter\n    counter += 1\nprint('Total sum:', total) # Output: 55\n```",
                importantPoints = listOf(
                    "Python uses indentation (4 spaces) instead of curly braces {} to define code blocks.",
                    "The single equals sign `=` is for assignment; the double equals `==` checks for equality.",
                    "Variables in Python do not require explicit type declaration (dynamically typed).",
                    "Comments begin with the `#` symbol and are ignored by the Python interpreter."
                ),
                keyTerms = listOf(
                    "Variable" to "A named reference pointing to a value stored in computer memory.",
                    "Indentation" to "Leading spaces at the start of a code line denoting membership in a block/scope.",
                    "Modulo Operator (%)" to "An arithmetic operator that returns the integer remainder of a division.",
                    "Syntax Error" to "An error caused by violating the formal grammar rules of the programming language."
                ),
                applications = listOf(
                    "Artificial Intelligence and Machine Learning model development.",
                    "Web application backends (Django, Flask, FastAPI).",
                    "Data analysis, statistics, and scientific calculations.",
                    "Task automation, web scraping, and file processing scripts."
                ),
                advantages = listOf(
                    "Clean, readable syntax that reduces development time compared to C or Java.",
                    "Extensive standard library and thousands of open-source third-party packages.",
                    "Cross-platform execution across Windows, macOS, Linux, and Android."
                ),
                disadvantages = listOf(
                    "Execution speed is slower than compiled languages like C++ or Rust due to interpretation.",
                    "High memory consumption compared to low-level systems programming languages."
                ),
                typesCategories = listOf(
                    "Data Types: int, float, str, bool, list, tuple, dict.",
                    "Errors: Syntax Errors, Runtime Errors, Logical Errors."
                ),
                comparisons = listOf(
                    "for loop vs while loop" to "`for` loop is ideal when the number of iterations is known in advance (e.g. iterate 10 times); `while` loop is preferred when looping depends on an indefinite boolean condition (e.g. keep asking until user inputs correct password).",
                    "Compiler vs Interpreter in Python" to "Python uses an interpreter (CPython) which internally compiles source into bytecode (.pyc) and interprets it line-by-line via the Python Virtual Machine (PVM)."
                ),
                commonMistakes = listOf(
                    "Mixing up `=` (assignment) with `==` (equality comparison) inside `if` statements.",
                    "Inconsistent indentation (mixing tabs and spaces), causing `IndentationError`.",
                    "Creating an infinite loop in a `while` statement by forgetting to update the loop counter variable."
                ),
                examFocusedPoints = listOf(
                    "Write Python code to check if a number is Even or Odd using the `%` modulo operator.",
                    "Explain the three main types of programming errors: Syntax, Runtime, and Logical.",
                    "Demonstrate the syntax of `for` loops using the `range()` function."
                ),
                shortQuestions = listOf(
                    QuestionAnswer("What is the purpose of indentation in Python?", "Indentation is syntactically mandatory in Python; it defines the scope and hierarchy of code blocks within functions, loops, and conditional statements."),
                    QuestionAnswer("What is the difference between '=' and '==' in Python?", "'=' is the assignment operator used to store a value into a variable. '==' is the comparison operator used to test if two values are equal, returning True or False."),
                    QuestionAnswer("Explain what a logic error is.", "A logic error is a flaw in the program's algorithm where the code runs without crashing or syntax errors, but produces incorrect or unexpected results.")
                ),
                longQuestions = listOf(
                    QuestionAnswer(
                        "Explain the different types of control structures in Python with syntax and code examples.",
                        "Python features three core control structures:\n1. Sequential Structure: Code executes line-by-line in sequential order from top to bottom.\n2. Conditional / Selection Structure: Branches execution based on Boolean conditions using `if`, `elif`, and `else`.\nExample:\nif score >= 50:\n    print('Pass')\nelse:\n    print('Fail')\n\n3. Iteration / Repetition Structure: Repeats code execution using `for` (definite loops over ranges or lists) and `while` (indefinite loops until condition is false).\nExample:\nfor i in range(1, 6):\n    print('Count:', i)"
                    )
                ),
                mcqs = listOf(
                    McqItem("mcq_ch8_1", "Which symbol is used to write single-line comments in Python?", listOf("//", "/*", "#", "--"), 2, "In Python, the hash symbol '#' denotes a single-line comment."),
                    McqItem("mcq_ch8_2", "What is the output of `15 % 4` in Python?", listOf("3", "3.75", "1", "0"), 0, "The modulo operator % returns the remainder. 15 divided by 4 leaves remainder 3."),
                    McqItem("mcq_ch8_3", "Which operator tests whether two values are equal in Python?", listOf("=", "==", "===", "equals"), 1, "The double equals '==' is the relational equality operator.")
                ),
                practiceQuestions = listOf(
                    "Write a Python script that takes a user's age as input and prints whether they are eligible to vote (age >= 18).",
                    "Write a Python for loop that prints all even numbers between 2 and 20."
                ),
                revisionSummary = "Python is an interpreted, readable programming language. It uses mandatory indentation for code blocks. Variables hold values. Control flow includes if-elif-else conditions, while loops, and for loops. Code is organized into reusable functions.",
                flashcards = listOf(
                    FlashcardItem("fc_ch8_1", 8, "Python Programming", "What is the difference between = and == in Python?", "'=' assigns a value to a variable; '==' tests if two values are equal."),
                    FlashcardItem("fc_ch8_2", 8, "Python Programming", "What is an IndentationError in Python?", "An error triggered when spaces or tabs at the beginning of a line do not match expected code block structure.")
                ),
                relatedConcepts = listOf("Python", "Variable", "Loop", "Function", "Algorithm", "Interpreter"),
                visualDiagram = """
+-------------------------------------------------------+
|                PYTHON CONTROL FLOW LOGIC              |
+-------------------------------------------------------+
|  [ Sequential ]    =>   [ Selection / Branching ]     |
|   Statement 1                     |                   |
|        |                     < Condition? >           |
|   Statement 2                 /          \            |
|        |                  [True]        [False]       |
|   Statement 3               |              |          |
|                          Action A       Action B      |
+-------------------------------------------------------+
|  [ Iteration / Looping ]                              |
|           -----> < Condition Still True? >            |
|          |              /           \                 |
|          |           [Yes]          [No]              |
|          |             |              |               |
|      Loop Body         v              v               |
|          |        Execute Code   Exit Loop            |
|           -------------                               |
+-------------------------------------------------------+
                """.trimIndent()
            )
        )
    )
}
