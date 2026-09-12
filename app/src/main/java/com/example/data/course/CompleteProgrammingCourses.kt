package com.example.data.course

import com.example.model.*

/**
 * COMPLETE PROGRAMMING LANGUAGES ACADEMY
 * Python, C, C++, Java, JavaScript, TypeScript, Go, Rust, PHP, SQL, Kotlin
 */

object ProgrammingCoursesData {

    // ==================== PYTHON COMPLETE COURSE ====================
    fun getPythonCourse(): ChapterItem {
        return ChapterItem(
            id = 101,
            number = 1,
            title = "Python Programming - Complete Mastery",
            summary = "Learn Python from zero to professional level with practical examples and exercises",
            iconName = "python",
            topics = listOf(
                TopicNote(
                    topicId = "py_001",
                    chapterId = 101,
                    chapterTitle = "Python Fundamentals",
                    title = "Introduction to Python",
                    definition = "Python is a high-level, interpreted programming language known for its simplicity and readability.",
                    easyExplanation = "Python ایک آسان زبان ہے جو عام لوگ بھی سیکھ سکتے ہیں۔ یہ انگریزی جیسا لگتا ہے۔",
                    detailedExplanation = """
                        Python اک interpreted language ہے:
                        - Code line by line چلتا ہے (compilation نہیں)
                        - Dynamic typing (variables کی قسم خود سمجھ جاتا ہے)
                        - Indentation سے code blocks بنتے ہیں
                        - REPL (interactive shell) میں test کر سکتے ہو
                    """.trimIndent(),
                    realWorldExample = "Web development (Django), Data Science (Pandas), AI/ML (TensorFlow)",
                    technicalExample = """
                        # Python میں سب سے پہلا پروگرام
                        print("السلام علیکم!")
                        
                        # Variable
                        name = "احمد"
                        age = 20
                        print(f"نام: {name}, عمر: {age}")
                    """.trimIndent(),
                    importantPoints = listOf(
                        "Python کی file .py extension میں ہوتی ہے",
                        "Indentation 4 spaces standard ہے",
                        "Comments # سے شروع ہوتے ہیں",
                        "Python interpreter install کرنا پڑتا ہے"
                    ),
                    keyTerms = listOf(
                        "Interpreter" to "جو code کو چلاتا ہے",
                        "Variable" to "ڈیٹا رکھنے کی جگہ",
                        "Indentation" to "code کی formatting"
                    ),
                    applications = listOf(
                        "Web applications (Flask, Django)",
                        "Data analysis (Pandas, NumPy)",
                        "Machine Learning (TensorFlow, PyTorch)",
                        "Automation scripts",
                        "Desktop applications"
                    ),
                    advantages = listOf(
                        "بہت آسان سیکھنا",
                        "Readable اور clean code",
                        "بہت ساری libraries",
                        "Cross-platform"
                    ),
                    disadvantages = listOf(
                        "Interpreted ہونے سے slow ہے",
                        "Mobile development کے لیے مشکل",
                        "Memory زیادہ استعمال کرتا ہے"
                    ),
                    typesCategories = listOf(
                        "Python 2 (پرانا، اب استعمال نہیں)",
                        "Python 3 (موجودہ، استعمال ہونے والا)"
                    ),
                    comparisons = listOf(
                        "Python vs Java" to "Python آسان ہے، Java تیز ہے",
                        "Python vs C" to "Python سیکھنا آسان، C faster",
                        "Python vs JavaScript" to "Python data science میں بہتر"
                    ),
                    commonMistakes = listOf(
                        "Indentation غلط کرنا (SyntaxError)",
                        "Variable کو quotes میں نہ لکھنا",
                        "Print() کو پرنٹ لکھنا (Python 2 سے confusion)"
                    ),
                    examFocusedPoints = listOf(
                        "Python کیا ہے؟",
                        "Interpreted اور Compiled میں فرق",
                        "Variables اور data types",
                        "print() function کا استعمال"
                    ),
                    shortQuestions = listOf(
                        QuestionAnswer(
                            question = "Python کیا ہے؟",
                            answer = "Python ایک high-level, interpreted programming language ہے جو سیکھنا آسان ہے۔",
                            keyPoints = listOf("high-level", "interpreted", "readable")
                        ),
                        QuestionAnswer(
                            question = "Python 2 اور Python 3 میں کیا فرق ہے؟",
                            answer = "Python 2 پرانا ہے اور اب support نہیں ہوتا۔ Python 3 موجودہ version ہے۔",
                            keyPoints = listOf("Python 2 discontinued", "Python 3 current")
                        )
                    ),
                    longQuestions = listOf(
                        QuestionAnswer(
                            question = "Python کو دوسری languages سے بہتر کیوں کہا جاتا ہے؟ تفصیل سے بتائیں۔",
                            answer = """
                                Python کو بہتر کہا جاتا ہے کیونکہ:
                                1. سیکھنا بہت آسان ہے - syntax انگریزی جیسا ہے
                                2. Readable code لکھنے میں مدد دیتا ہے
                                3. Data Science اور AI میں بہترین
                                4. بہت ساری libraries موجود ہیں
                                5. Cross-platform ہے
                            """.trimIndent(),
                            keyPoints = listOf("simplicity", "readability", "libraries", "versatility")
                        )
                    ),
                    mcqs = listOf(
                        McqItem(
                            id = "py_mcq_001",
                            question = "Python کا پہلا version کب release ہوا؟",
                            options = listOf("1989", "1995", "2000", "2005"),
                            correctOptionIndex = 0,
                            explanation = "Python Guido van Rossum نے 1989 میں بنایا تھا۔"
                        ),
                        McqItem(
                            id = "py_mcq_002",
                            question = "Python file کا extension کیا ہے؟",
                            options = listOf(".py", ".python", ".pyt", ".pyc"),
                            correctOptionIndex = 0,
                            explanation = ".py Python کا standard extension ہے۔"
                        )
                    ),
                    practiceQuestions = listOf(
                        "Python interpreter کو صرف کی ضرورت ہے؟",
                        "Python میں variables کو declare کرتے وقت type specify کرنا ضروری ہے؟",
                        "Python میں comment کیسے لکھتے ہیں؟"
                    ),
                    revisionSummary = """
                        Python:
                        - High-level, interpreted language
                        - Readable اور سیکھنا آسان
                        - Data Science اور AI میں بہترین
                        - .py extension میں save ہوتا ہے
                    """.trimIndent(),
                    flashcards = listOf(
                        FlashcardItem(
                            id = "py_fc_001",
                            chapterId = 101,
                            topicTitle = "Python کیا ہے",
                            front = "Python کیا ہے؟",
                            back = "Python ایک high-level, interpreted programming language ہے"
                        )
                    ),
                    relatedConcepts = listOf("Variables", "Data Types", "Functions"),
                    visualDiagram = """
                        Python Code
                           ↓
                        Python Interpreter
                           ↓
                        Bytecode (.pyc)
                           ↓
                        Python Virtual Machine
                           ↓
                        Output
                    """.trimIndent()
                ),
                // Variables اور Data Types
                TopicNote(
                    topicId = "py_002",
                    chapterId = 101,
                    chapterTitle = "Python Fundamentals",
                    title = "Variables اور Data Types",
                    definition = "Variables میں ڈیٹا store ہوتا ہے اور data types ڈیٹا کی قسم بتاتے ہیں۔",
                    easyExplanation = "Variable ایک box ہے جس میں قیمت رکھتے ہو۔",
                    detailedExplanation = """
                        Python میں variables:
                        - کوئی بھی نام دے سکتے ہو (alphanumeric + underscore)
                        - = سے value assign ہوتی ہے
                        - Dynamic typing - خود سمجھ جاتا ہے کون سی type ہے
                        
                        Main Data Types:
                        1. int - پوری تعداد (123, -45)
                        2. float - اعشاریہ (3.14, 2.5)
                        3. str - متن ("السلام علیکم")
                        4. bool - True/False
                        5. list - متعدد عناصر [1, 2, 3]
                        6. dict - key-value pairs {name: "احمد"}
                    """.trimIndent(),
                    realWorldExample = "name = 'احمد', age = 20, marks = 85.5, is_passed = True",
                    technicalExample = """
                        # Variables اور Data Types
                        name = "احمد"          # str
                        age = 20               # int
                        height = 5.9           # float
                        is_student = True      # bool
                        
                        # List اور Dictionary
                        subjects = ["Math", "Science", "Urdu"]  # list
                        person = {"name": "احمد", "age": 20}     # dict
                        
                        # Type check کریں
                        print(type(name))      # <class 'str'>
                        print(type(age))       # <class 'int'>
                    """.trimIndent(),
                    importantPoints = listOf(
                        "Variable کا نام کسی بھی حرف سے شروع ہو سکتا ہے لیکن number سے نہیں",
                        "Python میں dynamic typing ہے",
                        "= سے value assign ہوتی ہے",
                        "type() function سے قسم معلوم کر سکتے ہو"
                    ),
                    keyTerms = listOf(
                        "Variable" to "ڈیٹا رکھنے کی جگہ",
                        "Data Type" to "ڈیٹا کی قسم",
                        "Dynamic Typing" to "خود سے قسم معلوم کرنا"
                    ),
                    applications = listOf(
                        "User data store کرنا",
                        "Calculations میں استعمال",
                        "Database operations"
                    ),
                    advantages = listOf(
                        "Dynamic typing سے آسانی ہے",
                        "Flexible ہے",
                        "پڑھنے میں آسان"
                    ),
                    disadvantages = listOf(
                        "Type errors runtime میں آتی ہیں",
                        "Performance کم ہو سکتی ہے"
                    ),
                    typesCategories = listOf(
                        "Primitive: int, float, str, bool",
                        "Collections: list, tuple, dict, set"
                    ),
                    comparisons = listOf(
                        "int vs float" to "int پوری تعداد، float اعشاریہ",
                        "list vs dict" to "list میں index سے، dict میں key سے رسائی"
                    ),
                    commonMistakes = listOf(
                        "Variable کے نام میں space استعمال کرنا",
                        "Reserved keywords جیسے if, for کو variable کا نام بنانا",
                        "String میں quotes غلط کرنا"
                    ),
                    examFocusedPoints = listOf(
                        "Data types کیا ہیں؟",
                        "Variables کیسے declare ہوتے ہیں؟",
                        "Dynamic typing کیا ہے؟"
                    ),
                    shortQuestions = listOf(
                        QuestionAnswer(
                            question = "Data type کیا ہوتی ہے؟",
                            answer = "Data type ڈیٹا کی قسم بتاتی ہے جیسے int, str, float وغیرہ۔",
                            keyPoints = listOf("specifies data kind", "int, str, float, bool")
                        )
                    ),
                    longQuestions = listOf(),
                    mcqs = listOf(
                        McqItem(
                            id = "py_mcq_003",
                            question = "x = 'احمد' کی data type کیا ہے؟",
                            options = listOf("str", "int", "float", "bool"),
                            correctOptionIndex = 0,
                            explanation = "Quotes میں لکھی ہوئی value string (str) ہوتی ہے۔"
                        )
                    ),
                    practiceQuestions = listOf(
                        "ایک variable میں اپنا نام store کریں",
                        "3 مختلف data types کے variables بنائیں"
                    ),
                    revisionSummary = "Variables میں ڈیٹا store ہوتا ہے۔ Python میں dynamic typing ہے۔",
                    flashcards = listOf(),
                    relatedConcepts = listOf("Operators", "Input/Output"),
                    visualDiagram = ""
                ),
                // Input/Output
                TopicNote(
                    topicId = "py_003",
                    chapterId = 101,
                    chapterTitle = "Python Fundamentals",
                    title = "Input اور Output",
                    definition = "Output کا مطلب ہے ڈیٹا دکھانا، Input کا مطلب ہے صارف سے ��یٹا لینا۔",
                    easyExplanation = "print() سے output دیتے ہو، input() سے صارف سے سوال کرتے ہو۔",
                    detailedExplanation = """
                        Output کے لیے:
                        - print() function استعمال ہوتا ہے
                        - متعدد values comma سے الگ کر سکتے ہو
                        - f-string سے variables داخل کر سکتے ہو
                        
                        Input کے لیے:
                        - input() function استعمال ہوتا ہے
                        - صارف سے string لیتا ہے
                        - int() سے integer میں convert کرو
                    """.trimIndent(),
                    realWorldExample = """
                        name = input("نام بتائیں: ")
                        print(f"السلام علیکم {name}!")
                    """.trimIndent(),
                    technicalExample = """
                        # Output
                        print("ہیلو!")
                        x = 10
                        y = 20
                        print(x + y)
                        print(f"نتیجہ: {x + y}")
                        
                        # Input
                        name = input("نام: ")
                        age = int(input("عمر: "))
                        print(f"السلام علیکم {name}, آپ {age} سال کے ہیں")
                    """.trimIndent(),
                    importantPoints = listOf(
                        "print() میں متعدد arguments comma سے الگ ہو سکتے ہیں",
                        "input() ہمیشہ string میں ڈیٹا دیتا ہے",
                        "int() سے string کو integer میں convert کریں",
                        "f-string سے variables آسانی سے ڈال سکتے ہو"
                    ),
                    keyTerms = listOf(
                        "print()" to "output دینے کا function",
                        "input()" to "input لینے کا function",
                        "f-string" to "formatted string"
                    ),
                    applications = listOf(
                        "صارف سے ڈیٹا لینا",
                        "نتائج دکھانا",
                        "Debugging میں messages دیکھنا"
                    ),
                    advantages = listOf(
                        "سادہ اور سمجھنے میں آسان",
                        "تمام ڈیٹا types دیکھا سکتے ہو",
                        "f-string بہت طاقتور ہے"
                    ),
                    disadvantages = listOf(
                        "Console-based output",
                        "input() ہمیشہ string میں ہوتا ہے"
                    ),
                    typesCategories = listOf(
                        "print() - کئی طریقے",
                        "input() - صرف ایک طریقہ"
                    ),
                    comparisons = listOf(),
                    commonMistakes = listOf(
                        "input() کو integer سمجھنا",
                        "print() میں quotes غلط کرنا",
                        "f-string میں curly braces بھولنا"
                    ),
                    examFocusedPoints = listOf(
                        "print() کیسے کام کرتا ہے؟",
                        "input() سے کیا ملتا ہے؟",
                        "f-string کیا ہے؟"
                    ),
                    shortQuestions = listOf(),
                    longQuestions = listOf(),
                    mcqs = listOf(),
                    practiceQuestions = listOf(
                        "ایک پروگرام بنائیں جو صارف سے نام اور عمر لے",
                        "ان کو formatted طریقے سے دکھائیں"
                    ),
                    revisionSummary = "print() سے output، input() سے input لیتے ہیں۔",
                    flashcards = listOf(),
                    relatedConcepts = listOf("Strings", "Variables"),
                    visualDiagram = ""
                )
            )
        )
    }

    // ==================== C PROGRAMMING COMPLETE ====================
    fun getCProgrammingCourse(): ChapterItem {
        return ChapterItem(
            id = 102,
            number = 2,
            title = "C Programming - Foundation to Advanced",
            summary = "Master C language - the mother of all programming languages",
            iconName = "c_lang",
            topics = listOf(
                TopicNote(
                    topicId = "c_001",
                    chapterId = 102,
                    chapterTitle = "C Basics",
                    title = "Introduction to C",
                    definition = "C ایک procedural, compiled programming language ہے جو کمپیوٹر کے قریب ہے۔",
                    easyExplanation = "C سب سے طاقتور language ہے جو تقریباً سب جدید languages کی بنیاد ہے۔",
                    detailedExplanation = """
                        C کی خصوصیات:
                        - Compiled language (تیز رفتار)
                        - Low-level اور high-level دونوں کام کر سکتے ہو
                        - Pointers اور memory management
                        - File handling آسان ہے
                        - Operating systems میں استعمال ہوتا ہے
                    """.trimIndent(),
                    realWorldExample = "Linux kernel, Windows, Database systems",
                    technicalExample = """
                        #include <stdio.h>
                        
                        int main() {
                            printf("السلام علیکم!");
                            return 0;
                        }
                    """.trimIndent(),
                    importantPoints = listOf(
                        "Compilation ضروری ہے",
                        "stdio.h header file پڑھنا لکھنے کے لیے",
                        "main() function entry point ہے",
                        "return 0 program کو successfully ختم کرتا ہے"
                    ),
                    keyTerms = listOf(
                        "Header file" to ".h files جو functions define کرتی ہیں",
                        "stdio.h" to "input/output کے لیے"
                    ),
                    applications = listOf(
                        "Operating Systems",
                        "Embedded systems",
                        "Databases",
                        "Compilers"
                    ),
                    advantages = listOf(
                        "بہت تیز رفتار",
                        "Memory control",
                        "Portable",
                        "سادہ اور دیرپا ہے"
                    ),
                    disadvantages = listOf(
                        "سیکھنا مشکل ہے",
                        "Manual memory management",
                        "Debugging مشکل ہے"
                    ),
                    typesCategories = listOf(
                        "Procedural language",
                        "Compiled language"
                    ),
                    comparisons = listOf(
                        "C vs Python" to "C تیز ہے، Python سیکھنا آسان",
                        "C vs Java" to "C low-level، Java high-level"
                    ),
                    commonMistakes = listOf(
                        "Semicolon بھولنا",
                        "Header files شامل نہ کرنا",
                        "Brackets غلط کرنا"
                    ),
                    examFocusedPoints = listOf(
                        "C کیا ہے؟",
                        "Compilation کیا ہے؟",
                        "main() function کی ضرورت"
                    ),
                    shortQuestions = listOf(
                        QuestionAnswer(
                            question = "C کو 'mother of languages' کیوں کہا جاتا ہے؟",
                            answer = "کیونکہ اکثر جدید languages C کی بنیاد پر بنی ہیں اور C کے اصول استعمال کرتی ہیں۔",
                            keyPoints = listOf("foundation", "influenced others")
                        )
                    ),
                    longQuestions = listOf(),
                    mcqs = listOf(
                        McqItem(
                            id = "c_mcq_001",
                            question = "C ایک _____ language ہے؟",
                            options = listOf("compiled", "interpreted", "scripting", "markup"),
                            correctOptionIndex = 0,
                            explanation = "C compiled language ہے - پہلے compile کرنا پڑتا ہے۔"
                        )
                    ),
                    practiceQuestions = listOf(
                        "اپنا پہلا C پروگرام لکھیں جو 'ہیلو!' print کرے"
                    ),
                    revisionSummary = "C compiled language ہے۔ stdio.h سے printf/scanf استعمال ہوتے ہیں۔",
                    flashcards = listOf(),
                    relatedConcepts = listOf("Variables", "Data Types"),
                    visualDiagram = ""
                ),
                // C Variables اور Data Types
                TopicNote(
                    topicId = "c_002",
                    chapterId = 102,
                    chapterTitle = "C Basics",
                    title = "Variables اور Data Types in C",
                    definition = "C میں variables استعمال کرنے سے پہلے declare کرنا پڑتا ہے۔",
                    easyExplanation = "C میں ہمیں پہلے بتانا پڑتا ہے کہ variable کی type کیا ہے۔",
                    detailedExplanation = """
                        C میں Static Typing:
                        - int - پوری تعداد (2 یا 4 bytes)
                        - float - اعشاریہ (4 bytes)
                        - char - ایک حرف (1 byte)
                        - double - لمبے اعشاریہ (8 bytes)
                        - void - کوئی value نہیں
                        
                        Qualifiers:
                        - short - کم memory
                        - long - زیادہ memory
                        - unsigned - منفی نہیں
                        - signed - منفی ہو سکتے ہیں
                    """.trimIndent(),
                    realWorldExample = """
                        int age = 20;
                        float height = 5.9;
                        char gender = 'M';
                    """.trimIndent(),
                    technicalExample = """
                        #include <stdio.h>
                        
                        int main() {
                            int num = 10;
                            float marks = 85.5;
                            char grade = 'A';
                            
                            printf("Number: %d\\n", num);
                            printf("Marks: %f\\n", marks);
                            printf("Grade: %c\\n", grade);
                            
                            return 0;
                        }
                    """.trimIndent(),
                    importantPoints = listOf(
                        "C میں declare کرتے وقت type لکھنا ضروری ہے",
                        "Format specifiers: %d (int), %f (float), %c (char)",
                        "Memory size مختلف ہوتی ہے ہر type کی"
                    ),
                    keyTerms = listOf(
                        "int" to "integer type",
                        "float" to "decimal type",
                        "char" to "character type",
                        "Format specifier" to "printf میں value دیکھانے کے لیے"
                    ),
                    applications = listOf(
                        "User data store کرنا",
                        "Calculations",
                        "Data processing"
                    ),
                    advantages = listOf(
                        "Memory efficient",
                        "تیز رفتار",
                        "Type-safe"
                    ),
                    disadvantages = listOf(
                        "Static typing سے flexibility کم ہے",
                        "Compilation ہر بار ضروری ہے"
                    ),
                    typesCategories = listOf(
                        "Basic: int, float, char, double",
                        "Modified: unsigned, signed, short, long"
                    ),
                    comparisons = listOf(
                        "int vs float" to "int پوری تعداد، float اعشاریہ",
                        "char vs int" to "char ایک حرف، int تعداد"
                    ),
                    commonMistakes = listOf(
                        "Type declare نہ کرنا",
                        "Format specifier غلط لکھنا",
                        "Buffer overflow"
                    ),
                    examFocusedPoints = listOf(
                        "C میں data types کیا ہیں؟",
                        "Variable declare کیسے کرتے ہیں؟",
                        "Size of operators کا استعمال"
                    ),
                    shortQuestions = listOf(),
                    longQuestions = listOf(),
                    mcqs = listOf(),
                    practiceQuestions = listOf(
                        "مختلف data types کے variables بنائیں"
                    ),
                    revisionSummary = "C میں declare کرتے وقت type لکھنا ضروری ہے۔",
                    flashcards = listOf(),
                    relatedConcepts = listOf("Operators", "Input/Output"),
                    visualDiagram = ""
                ),
                // Operators in C
                TopicNote(
                    topicId = "c_003",
                    chapterId = 102,
                    chapterTitle = "C Basics",
                    title = "Operators in C",
                    definition = "Operators وہ symbols ہیں جو operations کے لیے استعمال ہوتے ہیں۔",
                    easyExplanation = "+, -, *, / جیسے symbols ہیں operators۔",
                    detailedExplanation = """
                        Arithmetic Operators:
                        + (جمع), - (تفریق), * (ضرب), / (تقسیم), % (remainder)
                        
                        Comparison Operators:
                        == (برابر), != (برابر نہیں), > (بڑا), < (چھوٹا)
                        
                        Logical Operators:
                        && (اور), || (یا), ! (نہیں)
                        
                        Assignment Operators:
                        =, +=, -=, *=, /=
                    """.trimIndent(),
                    realWorldExample = """
                        int a = 10, b = 5;
                        int sum = a + b;      // 15
                        int product = a * b;  // 50
                        int remainder = a % b; // 0
                    """.trimIndent(),
                    technicalExample = """
                        #include <stdio.h>
                        
                        int main() {
                            int a = 10, b = 3;
                            
                            printf("جمع: %d\\n", a + b);      // 13
                            printf("تفریق: %d\\n", a - b);    // 7
                            printf("ضرب: %d\\n", a * b);      // 30
                            printf("تقسیم: %d\\n", a / b);    // 3
                            printf("Remainder: %d\\n", a % b); // 1
                            
                            return 0;
                        }
                    """.trimIndent(),
                    importantPoints = listOf(
                        "Operator precedence important ہے",
                        "% operator صرف integers میں",
                        "Logical operators boolean value دیتے ہیں"
                    ),
                    keyTerms = listOf(),
                    applications = listOf(),
                    advantages = listOf(),
                    disadvantages = listOf(),
                    typesCategories = listOf(
                        "Arithmetic: +, -, *, /, %",
                        "Comparison: ==, !=, >, <, >=, <=",
                        "Logical: &&, ||, !",
                        "Bitwise: &, |, ^, ~, <<, >>"
                    ),
                    comparisons = listOf(),
                    commonMistakes = listOf(
                        "= (assignment) اور == (comparison) کو غلط کرنا",
                        "Operator precedence ignore کرنا"
                    ),
                    examFocusedPoints = listOf(),
                    shortQuestions = listOf(),
                    longQuestions = listOf(),
                    mcqs = listOf(),
                    practiceQuestions = listOf(
                        "مختلف operators کا استعمال کریں"
                    ),
                    revisionSummary = "Operators operations کے لیے استعمال ہوتے ہیں۔",
                    flashcards = listOf(),
                    relatedConcepts = listOf(),
                    visualDiagram = ""
                ),
                // Loops in C
                TopicNote(
                    topicId = "c_004",
                    chapterId = 102,
                    chapterTitle = "Control Flow",
                    title = "Loops in C",
                    definition = "Loops ایک code block کو بار بار چلاتے ہیں۔",
                    easyExplanation = "جب کچھ کام بار بار کرنا ہو تو loop استعمال ہوتے ہیں۔",
                    detailedExplanation = """
                        for Loop:
                        for(init; condition; increment) { code }
                        
                        while Loop:
                        while(condition) { code }
                        
                        do-while Loop:
                        do { code } while(condition);
                        
                        Loop Control:
                        break - loop سے نکل جانا
                        continue - اگلی iteration
                    """.trimIndent(),
                    realWorldExample = """
                        // 1 سے 10 تک print کریں
                        for(int i = 1; i <= 10; i++) {
                            printf("%d ", i);
                        }
                    """.trimIndent(),
                    technicalExample = """
                        #include <stdio.h>
                        
                        int main() {
                            // for loop
                            for(int i = 0; i < 5; i++) {
                                printf("%d ", i);  // 0 1 2 3 4
                            }
                            
                            printf("\\n");
                            
                            // while loop
                            int j = 0;
                            while(j < 5) {
                                printf("%d ", j);  // 0 1 2 3 4
                                j++;
                            }
                            
                            return 0;
                        }
                    """.trimIndent(),
                    importantPoints = listOf(
                        "for loop جب iterations معلوم ہوں",
                        "while loop جب condition نہ معلوم ہو",
                        "break سے loop نکل سکتے ہو",
                        "continue سے ایک iteration چھوڑ سکتے ہو"
                    ),
                    keyTerms = listOf(
                        "for" to "fixed iterations",
                        "while" to "conditional iterations",
                        "do-while" to "atleast once execute",
                        "break" to "exit loop",
                        "continue" to "skip iteration"
                    ),
                    applications = listOf(
                        "1 سے n تک sum",
                        "Table print کرنا",
                        "Array traverse کرنا"
                    ),
                    advantages = listOf(
                        "Code کی repetition کم ہوتی ہے",
                        "بڑے data sets handle کر سکتے ہو"
                    ),
                    disadvantages = listOf(
                        "Infinite loops کا خطرہ",
                        "Debugging مشکل ہو سکتی ہے"
                    ),
                    typesCategories = listOf(
                        "Deterministic: for",
                        "Conditional: while, do-while"
                    ),
                    comparisons = listOf(
                        "for vs while" to "for جب count معلوم، while جب condition"
                    ),
                    commonMistakes = listOf(
                        "Semicolon for loop میں نہیں ہونا",
                        "Infinite loop بن ��انا",
                        "Increment/decrement بھولنا"
                    ),
                    examFocusedPoints = listOf(
                        "Loop کے 3 قسم؟",
                        "for loop کی syntax؟",
                        "break اور continue کا فرق؟"
                    ),
                    shortQuestions = listOf(),
                    longQuestions = listOf(),
                    mcqs = listOf(),
                    practiceQuestions = listOf(
                        "1 سے 100 تک sum calculate کریں"
                    ),
                    revisionSummary = "Loops code کو بار بار چلاتے ہیں۔",
                    flashcards = listOf(),
                    relatedConcepts = listOf("Conditionals", "Arrays"),
                    visualDiagram = ""
                )
            )
        )
    }

    // ==================== JAVA PROGRAMMING ====================
    fun getJavaProgrammingCourse(): ChapterItem {
        return ChapterItem(
            id = 103,
            number = 3,
            title = "Java Programming - OOP Master",
            summary = "Complete Java from basics to advanced OOP concepts",
            iconName = "java",
            topics = listOf(
                TopicNote(
                    topicId = "java_001",
                    chapterId = 103,
                    chapterTitle = "Java Fundamentals",
                    title = "Introduction to Java",
                    definition = "Java ایک Object-Oriented, compiled programming language ہے جو 'Write Once, Run Anywhere' principle پر کام کرتا ہے۔",
                    easyExplanation = "Java ایک طاقتور language ہے جو بڑے applications میں استعمال ہوتی ہے۔",
                    detailedExplanation = """
                        Java کی خصوصیات:
                        - Object-Oriented Programming
                        - Platform Independent (JVM میں چلتا ہے)
                        - Strong type checking
                        - Garbage Collection
                        - Multi-threading support
                        - Rich library ecosystem
                    """.trimIndent(),
                    realWorldExample = "Android apps, Enterprise software, Web applications",
                    technicalExample = """
                        public class HelloWorld {
                            public static void main(String[] args) {
                                System.out.println("السلام علیکم!");
                            }
                        }
                    """.trimIndent(),
                    importantPoints = listOf(
                        "JVM (Java Virtual Machine) ضروری ہے",
                        "Class-based language ہے",
                        "main method entry point ہے",
                        "Semicolon ہر statement کے آخر میں"
                    ),
                    keyTerms = listOf(
                        "JVM" to "Java Virtual Machine",
                        "Bytecode" to ".class files",
                        "Platform Independent" to "ہر جگہ چل سکتا ہے"
                    ),
                    applications = listOf(
                        "Android Development",
                        "Web applications (Spring)",
                        "Enterprise software",
                        "Desktop applications"
                    ),
                    advantages = listOf(
                        "Platform independent",
                        "Secure",
                        "Multi-threaded",
                        "بہت سارے libraries"
                    ),
                    disadvantages = listOf(
                        "JVM کی ضرورت",
                        "Memory زیادہ استعمال",
                        "سیکھنا مشکل ہے"
                    ),
                    typesCategories = listOf(
                        "Compiled language (bytecode)",
                        "Object-Oriented"
                    ),
                    comparisons = listOf(
                        "Java vs C++" to "Java سادہ، C++ تیز",
                        "Java vs Python" to "Java typed، Python dynamic"
                    ),
                    commonMistakes = listOf(
                        "public class نام غلط کرنا",
                        "main method کی signature غلط کرنا"
                    ),
                    examFocusedPoints = listOf(
                        "Java کیا ہے؟",
                        "JVM کیا ہے؟",
                        "Write Once Run Anywhere"
                    ),
                    shortQuestions = listOf(),
                    longQuestions = listOf(),
                    mcqs = listOf(),
                    practiceQuestions = listOf(),
                    revisionSummary = "Java OOP language ہے جو JVM میں چلتی ہے۔",
                    flashcards = listOf(),
                    relatedConcepts = listOf("OOP", "Classes"),
                    visualDiagram = ""
                )
            )
        )
    }

    // JavaScript, TypeScript, Go, Rust, PHP, SQL, Kotlin کے courses بھی بننے ہیں
    fun getJavaScriptCourse(): ChapterItem {
        return ChapterItem(
            id = 104,
            number = 4,
            title = "JavaScript - Web Development Master",
            summary = "Complete JavaScript from basics to advanced ES6+ and frameworks",
            iconName = "javascript",
            topics = emptyList() // Topics populate کریں گے
        )
    }

    fun getGoLanguageCourse(): ChapterItem {
        return ChapterItem(
            id = 105,
            number = 5,
            title = "Go Programming - Concurrent Systems",
            summary = "Master Go language for building scalable systems",
            iconName = "go",
            topics = emptyList()
        )
    }

    fun getRustProgrammingCourse(): ChapterItem {
        return ChapterItem(
            id = 106,
            number = 6,
            title = "Rust Programming - Memory Safety",
            summary = "Learn Rust - the language for systems programming",
            iconName = "rust",
            topics = emptyList()
        )
    }

    fun getSQLDatabaseCourse(): ChapterItem {
        return ChapterItem(
            id = 107,
            number = 7,
            title = "SQL - Database Fundamentals",
            summary = "Master SQL for database operations",
            iconName = "sql",
            topics = emptyList()
        )
    }

    fun getKotlinAndroidCourse(): ChapterItem {
        return ChapterItem(
            id = 108,
            number = 8,
            title = "Kotlin - Modern Android Development",
            summary = "Learn Kotlin for Android app development",
            iconName = "kotlin",
            topics = emptyList()
        )
    }

    fun getPHPWebCourse(): ChapterItem {
        return ChapterItem(
            id = 109,
            number = 9,
            title = "PHP - Backend Web Development",
            summary = "Learn PHP for server-side web development",
            iconName = "php",
            topics = emptyList()
        )
    }
}
