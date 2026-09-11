package com.example.data.course

import com.example.model.*

object Chapter3DataRepresentation {
    val chapter = ChapterItem(
        id = 3,
        number = 3,
        title = "Data Representation & Number Systems",
        summary = "Binary, Decimal, Octal, Hexadecimal conversions, 1's and 2's complement, ASCII, Unicode, and text encoding.",
        iconName = "pin",
        topics = listOf(
            TopicNote(
                topicId = "ch3_top1",
                chapterId = 3,
                chapterTitle = "Data Representation & Number Systems",
                title = "Number Systems (Binary, Decimal, Hexadecimal) & Conversions",
                definition = "A number system is a mathematical technique for expressing and representing quantities using a designated set of digits or symbols with positional base values (radix).",
                easyExplanation = "Humans have 10 fingers, so we count in Decimal (Base 10: 0 to 9). Computers only have electronic switches with two states (OFF = 0, ON = 1), so they count in Binary (Base 2: 0 and 1). Hexadecimal (Base 16) is a smart human shortcut that compresses 4 binary bits into a single character.",
                detailedExplanation = "Positional number systems calculate value as the sum of each digit multiplied by the base raised to its positional power. Key bases include:\n1. Decimal (Base 10): Digits 0-9. Positional weights: 10^0, 10^1, 10^2...\n2. Binary (Base 2): Digits 0-1. Positional weights: 2^0=1, 2^1=2, 2^2=4, 2^3=8, 2^4=16, 2^5=32, 2^6=64, 2^7=128...\n3. Octal (Base 8): Digits 0-7. Positional weights: 8^0, 8^1, 8^2...\n4. Hexadecimal (Base 16): Symbols 0-9 and letters A=10, B=11, C=12, D=13, E=14, F=15.\nConversion between Decimal and Binary uses successive division by 2 (recording remainders from bottom to top) for integers, and successive multiplication by 2 for fractional parts.",
                realWorldExample = "Web Colors: When you write CSS color `#FF0000`, FF in hexadecimal equals 255 in decimal, representing maximum red intensity with zero green and blue. An IPv6 address `2001:0db8::` uses hexadecimal notation to compress 128 binary bits.",
                technicalExample = "Converting Binary `101101_2` to Decimal:\n= (1 * 2^5) + (0 * 2^4) + (1 * 2^3) + (1 * 2^2) + (0 * 2^1) + (1 * 2^0)\n= 32 + 0 + 8 + 4 + 0 + 1 = 45_10.\n\nConverting 45_10 to Binary:\n45 / 2 = 22 remainder 1\n22 / 2 = 11 remainder 0\n11 / 2 = 5 remainder 1\n5 / 2 = 2 remainder 1\n2 / 2 = 1 remainder 0\n1 / 2 = 0 remainder 1\nReading remainders backwards yields `101101_2`.",
                importantPoints = listOf(
                    "Bit = Binary Digit (smallest unit of digital data: 0 or 1).",
                    "Nibble = 4 bits (represented exactly by 1 Hexadecimal digit).",
                    "Byte = 8 bits (can represent 2^8 = 256 distinct characters or numbers).",
                    "Kilobyte (KB) = 1,024 bytes (in binary computing 2^10); Megabyte (MB) = 1,024 KB."
                ),
                keyTerms = listOf(
                    "Radix / Base" to "The total count of unique numerical digits or symbols used by a number system.",
                    "MSB (Most Significant Bit)" to "The leftmost bit carrying the greatest numerical positional weight.",
                    "LSB (Least Significant Bit)" to "The rightmost bit carrying the smallest numerical positional weight.",
                    "ASCII" to "American Standard Code for Information Interchange (7-bit or 8-bit character encoding)."
                ),
                applications = listOf(
                    "Low-level computer memory addressing (Hexadecimal addresses in RAM).",
                    "Digital logic gate circuitry design inside microprocessors.",
                    "RGB color rendering in graphics engines and web browsers.",
                    "Subnet masks and IP configuration in computer networking."
                ),
                advantages = listOf(
                    "Binary matches physical transistor hardware states (voltage High vs Low) perfectly.",
                    "High noise immunity: digital circuits distinguish between 0V and 5V reliably without analog degradation.",
                    "Hexadecimal reduces lengthy 32-bit binary strings to readable 8-character codes."
                ),
                disadvantages = listOf(
                    "Binary strings are long and unintuitive for human reading and communication.",
                    "Requires continuous mathematical conversion layers between human user interfaces and machine processors."
                ),
                typesCategories = listOf(
                    "Binary (Base 2): Symbols {0, 1}",
                    "Octal (Base 8): Symbols {0, 1, 2, 3, 4, 5, 6, 7}",
                    "Decimal (Base 10): Symbols {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}",
                    "Hexadecimal (Base 16): Symbols {0-9, A, B, C, D, E, F}"
                ),
                comparisons = listOf(
                    "Binary vs Hexadecimal" to "Binary uses 2 symbols and represents individual bits; Hexadecimal uses 16 symbols where each single hex digit cleanly packs exactly 4 binary bits.",
                    "ASCII vs Unicode" to "ASCII uses 7 or 8 bits (max 256 characters, primarily English alphabet); Unicode uses UTF-8/UTF-16 (up to 32 bits, encoding over 140,000 global characters, emojis, Urdu, Arabic, Chinese)."
                ),
                commonMistakes = listOf(
                    "Writing remainders in forward order instead of reading bottom-to-top when converting decimal to binary.",
                    "Forgetting that A=10 and F=15 in hexadecimal (stopping at 14 or confusing letters with numbers)."
                ),
                examFocusedPoints = listOf(
                    "Practice double-dabble decimal to binary conversions and binary to hexadecimal conversions thoroughly.",
                    "Know by heart: 1 Byte = 8 bits, 1 Nibble = 4 bits, 1 KB = 1024 Bytes.",
                    "State the decimal values of Hex digits A through F (A=10, B=11, C=12, D=13, E=14, F=15)."
                ),
                shortQuestions = listOf(
                    QuestionAnswer("How many unique digits are in the hexadecimal system? Name them.", "There are 16 unique digits in Hexadecimal: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, A (10), B (11), C (12), D (13), E (14), and F (15)."),
                    QuestionAnswer("Convert binary number 1101 to decimal.", "1101_2 = (1*8) + (1*4) + (0*2) + (1*1) = 8 + 4 + 0 + 1 = 13_10."),
                    QuestionAnswer("What is the difference between a Bit and a Byte?", "A bit is a single binary digit (0 or 1). A byte is a consecutive group of 8 bits capable of representing 256 values.")
                ),
                longQuestions = listOf(
                    QuestionAnswer(
                        "Explain the significance of binary and hexadecimal number systems in computer technology, and convert decimal number 156 into both binary and hexadecimal.",
                        "Computers use binary because microscopic transistors operate with two physical voltage states (ON and OFF). Hexadecimal serves as an efficient human-readable shorthand for binary, where 4 bits equal 1 hex digit.\n\nConverting 156 to Binary:\n156 / 2 = 78 (rem 0)\n78 / 2 = 39 (rem 0)\n39 / 2 = 19 (rem 1)\n19 / 2 = 9 (rem 1)\n9 / 2 = 4 (rem 1)\n4 / 2 = 2 (rem 0)\n2 / 2 = 1 (rem 0)\n1 / 2 = 0 (rem 1)\nBinary = 10011100_2.\n\nConverting 156 to Hexadecimal:\nGroup binary into 4-bit nibbles: [1001] [1100]\n1001_2 = 9_16\n1100_2 = 12_10 = C_16\nHexadecimal = 9C_16."
                    )
                ),
                mcqs = listOf(
                    McqItem("mcq_ch3_1", "What is the base of the hexadecimal number system?", listOf("2", "8", "10", "16"), 3, "Hexadecimal is a base-16 positional system."),
                    McqItem("mcq_ch3_2", "In hexadecimal, the letter 'E' represents which decimal value?", listOf("12", "13", "14", "15"), 2, "A=10, B=11, C=12, D=13, E=14, F=15."),
                    McqItem("mcq_ch3_3", "A group of 4 binary bits is technically termed a:", listOf("Byte", "Word", "Nibble", "Octet"), 2, "A 4-bit bundle is known as a nibble.")
                ),
                practiceQuestions = listOf(
                    "Convert decimal 75 into binary using the repeated division method.",
                    "Convert binary 11110101 into hexadecimal by grouping into 4-bit nibbles."
                ),
                revisionSummary = "Computers utilize Binary (base 2) because transistors operate on binary electric voltages. Hexadecimal (base 16) condenses 4 binary bits per digit. 1 Byte = 8 bits, 1 Nibble = 4 bits.",
                flashcards = listOf(
                    FlashcardItem("fc_ch3_1", 3, "Data Representation", "How many bits are in one Byte?", "8 bits."),
                    FlashcardItem("fc_ch3_2", 3, "Data Representation", "What decimal value does Hexadecimal 'F' represent?", "15.")
                ),
                relatedConcepts = listOf("Binary", "Decimal", "Hexadecimal", "ASCII", "Bit", "Byte"),
                visualDiagram = """
+-------------------------------------------------------+
|              NUMBER SYSTEM BITWEIGHT TABLE            |
+-------------------------------------------------------+
| Bit Position:   | 2^7 | 2^6 | 2^5 | 2^4 | 2^3 | 2^2 | 2^1 | 2^0 |
| Decimal Value:  | 128 | 64  | 32  | 16  |  8  |  4  |  2  |  1  |
+-----------------+-----+-----+-----+-----+-----+-----+-----+-----+
| Example (45):   |  0  |  0  |  1  |  0  |  1  |  1  |  0  |  1  |
| 32 + 8 + 4 + 1  |                       => 101101 in Binary   |
+-------------------------------------------------------+
                """.trimIndent()
            )
        )
    )
}
