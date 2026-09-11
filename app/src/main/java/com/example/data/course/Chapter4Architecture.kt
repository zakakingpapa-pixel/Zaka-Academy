package com.example.data.course

import com.example.model.*

object Chapter4Architecture {
    val chapter = ChapterItem(
        id = 4,
        number = 4,
        title = "Computer Architecture & Hardware",
        summary = "CPU internals (ALU, CU, Registers), instruction cycle, memory hierarchy (Cache, RAM, ROM), and system buses.",
        iconName = "memory",
        topics = listOf(
            TopicNote(
                topicId = "ch4_top1",
                chapterId = 4,
                chapterTitle = "Computer Architecture & Hardware",
                title = "Central Processing Unit (CPU) & Memory Hierarchy",
                definition = "Computer architecture specifies the operational structure and interconnection of the CPU, memory subsystems, and I/O buses based on the Von Neumann model.",
                easyExplanation = "Imagine a master chef's kitchen: The chef's hands and brain are the CPU (ALU and Control Unit). The small chopping board right next to their knife is Cache Memory (super fast, small). The large kitchen counter holding current recipe ingredients is RAM. The deep pantry in the basement holding 50 bags of flour is the Hard Drive / SSD (Storage).",
                detailedExplanation = "The CPU comprises three primary architectural sections:\n1. ALU (Arithmetic Logic Unit): Executes binary additions, subtractions, multiplications, divisions, and logical boolean evaluations (AND, OR, NOT, XOR, comparisons).\n2. CU (Control Unit): The traffic director of the processor that manages the Fetch-Decode-Execute cycle, issuing electrical timing pulses along the control bus.\n3. Registers: Ultra-fast, internal static storage cells directly within the CPU die, including the Program Counter (PC, holds address of next instruction), Instruction Register (IR, holds current opcode), Memory Address Register (MAR), Memory Data Register (MDR), and Accumulator (ACC).\nMemory Hierarchy orders storage by latency and cost: Registers (sub-nanosecond) -> L1/L2/L3 Cache (1-10 ns) -> RAM (15-50 ns) -> NVMe SSD (20-100 microseconds) -> Mechanical HDD (5-15 milliseconds).",
                realWorldExample = "When launching a video game: Game assets (textures, 3D models) load from the SSD into RAM. During active gameplay, character coordinates and physics calculations are streamed from RAM into CPU L3/L2/L1 cache and calculated inside the ALU registers 60 times per second.",
                technicalExample = "Fetch-Decode-Execute cycle:\n1. Fetch: Address in PC copied to MAR; instruction fetched from RAM via Data Bus into MDR, then copied to IR; PC incremented.\n2. Decode: CU decodes binary opcode in IR (e.g. opcode 0x01 = ADD).\n3. Execute: ALU adds register contents with accumulator; flags register updated.",
                importantPoints = listOf(
                    "RAM is volatile: contents evaporate when electrical power is cut.",
                    "ROM is non-volatile: retains firmware and BIOS bootstrap programs permanently.",
                    "Cache memory uses high-speed SRAM (Static RAM), whereas system RAM uses DRAM (Dynamic RAM) requiring constant capacitive refreshing.",
                    "System buses include Address Bus (unidirectional, specifies memory address), Data Bus (bidirectional, transports data), and Control Bus (transports clock signals and read/write commands)."
                ),
                keyTerms = listOf(
                    "ALU" to "Arithmetic Logic Unit; performs math (+, -, *, /) and boolean logic (<, >, ==).",
                    "Control Unit (CU)" to "Coordinates the Fetch-Decode-Execute cycle and directs internal data movement.",
                    "Program Counter (PC)" to "A CPU register holding the memory address of the next instruction to be fetched.",
                    "SRAM vs DRAM" to "SRAM (used in Cache) is faster and uses flip-flops; DRAM (used in RAM) is denser, uses capacitors, and requires periodic refreshing."
                ),
                applications = listOf(
                    "High-frequency algorithmic trading requiring microsecond execution latencies.",
                    "Smartphones managing multiple active apps in RAM with fast task switching.",
                    "BIOS/UEFI firmware initialization on PC motherboards via ROM chips."
                ),
                advantages = listOf(
                    "Hierarchical memory gives the illusion of a memory system as fast as the fastest component (Cache) and as large as the cheapest (SSD).",
                    "Multi-core CPUs enable true parallel execution of distinct software threads."
                ),
                disadvantages = listOf(
                    "SRAM cache is extremely costly per megabyte, limiting cache sizes to a few dozen megabytes.",
                    "Memory wall: CPU execution speeds have outpaced RAM access latencies, causing memory bottlenecks."
                ),
                typesCategories = listOf(
                    "Primary Memory: Registers, Cache (L1, L2, L3), RAM (DDR4, DDR5), ROM (EEPROM, Flash).",
                    "Secondary Storage: Magnetic HDDs, Solid-State Drives (NAND Flash SSD), Optical Discs."
                ),
                comparisons = listOf(
                    "RAM vs ROM" to "RAM is volatile (loses data on power loss), read-write, holds active programs; ROM is non-volatile (retains data permanently), read-only, holds startup BIOS/UEFI firmware.",
                    "SRAM vs DRAM" to "SRAM is faster, more expensive, uses 6 transistors per bit, does not need refreshing (used in Cache); DRAM is slower, cheaper, uses 1 transistor + 1 capacitor per bit, requires periodic refreshing (used in RAM)."
                ),
                commonMistakes = listOf(
                    "Thinking Cache is larger than RAM: Cache is typically 4 MB to 64 MB, while RAM is 8 GB to 32 GB.",
                    "Confusing the CPU Tower case with the CPU chip: The processor is the small silicon square inside."
                ),
                examFocusedPoints = listOf(
                    "Be ready to write the comprehensive table comparing RAM and ROM on 5 parameters (volatility, speed, capacity, cost, purpose).",
                    "Describe the three main components of the CPU (ALU, CU, Registers) and their respective functions.",
                    "Explain the three types of computer buses: Address Bus, Data Bus, and Control Bus."
                ),
                shortQuestions = listOf(
                    QuestionAnswer("What are the three main components of the CPU?", "The three main components are the Arithmetic Logic Unit (ALU), the Control Unit (CU), and Registers."),
                    QuestionAnswer("Why is RAM called volatile memory?", "RAM is volatile because it requires continuous electrical power to maintain its state. When the computer is turned off, all data in RAM is completely lost."),
                    QuestionAnswer("What is the function of the Program Counter (PC) register?", "The Program Counter register stores the memory address of the next instruction waiting to be fetched and executed by the CPU.")
                ),
                longQuestions = listOf(
                    QuestionAnswer(
                        "Describe the memory hierarchy of a computer system and differentiate comprehensively between RAM and ROM.",
                        "Memory Hierarchy arranges memory technologies in a pyramid:\n1. CPU Registers: Fastest, smallest capacity (bytes), zero latency.\n2. Cache Memory (L1, L2, L3): SRAM on-die, 1-10 ns latency, holds immediate loop data.\n3. Primary RAM: DRAM, multi-gigabyte capacity, 20-50 ns, holds active programs.\n4. Secondary Storage (SSD/HDD): Non-volatile, multi-terabyte, microsecond to millisecond latency.\n\nRAM vs ROM Comparison:\n- Volatility: RAM is volatile; ROM is non-volatile.\n- Operation: RAM allows read and write; ROM is primarily read-only.\n- Contents: RAM holds active OS and user apps; ROM holds permanent boot BIOS firmware.\n- Speed: RAM is much faster than ROM access.\n- Capacity: RAM is large (GBs); ROM is small (MBs)."
                    )
                ),
                mcqs = listOf(
                    McqItem("mcq_ch4_1", "Which CPU register stores the address of the next instruction to be fetched?", listOf("Accumulator", "Instruction Register", "Program Counter", "Memory Data Register"), 2, "The Program Counter (PC) automatically tracks the next instruction's memory address."),
                    McqItem("mcq_ch4_2", "Which memory type uses capacitors and requires periodic electrical refreshing?", listOf("SRAM", "DRAM", "ROM", "Registers"), 1, "DRAM (Dynamic RAM) stores bits in capacitors that leak charge and must be refreshed thousands of times per second."),
                    McqItem("mcq_ch4_3", "Which bus is strictly unidirectional in a computer system?", listOf("Data Bus", "Address Bus", "Control Bus", "USB"), 1, "The Address Bus is unidirectional; the CPU sends addresses to memory, but memory does not send addresses back.")
                ),
                practiceQuestions = listOf(
                    "Trace the role of MAR, MDR, and IR during the fetch phase of the instruction cycle.",
                    "Explain why modern CPUs integrate three levels of cache (L1, L2, L3) rather than a single large cache."
                ),
                revisionSummary = "The CPU comprises ALU (math/logic), CU (timing/control), and Registers (high-speed temporary data). Memory hierarchy balances speed and capacity from Registers to Cache, RAM, and Storage. RAM is volatile read/write; ROM is non-volatile read-only.",
                flashcards = listOf(
                    FlashcardItem("fc_ch4_1", 4, "Computer Architecture", "What does ALU stand for and what does it do?", "Arithmetic Logic Unit; performs arithmetic (+, -, *, /) and logical comparisons."),
                    FlashcardItem("fc_ch4_2", 4, "Computer Architecture", "Is RAM volatile or non-volatile?", "Volatile; all contents are lost when power is turned off.")
                ),
                relatedConcepts = listOf("CPU", "RAM", "ROM", "Cache", "Buses", "Registers"),
                visualDiagram = """
+-------------------------------------------------------+
|                 MEMORY HIERARCHY PYRAMID              |
+-------------------------------------------------------+
|                     /   Registers   \   (Fastest, Sub-ns, Bytes)
|                    /     L1 Cache    \
|                   /    L2 / L3 Cache  \
|                  /     Primary RAM     \  (Volatile, GBs, Nanosec)
|                 /   Solid-State Drive   \ (Non-volatile, TBs, Microsec)
|                /   Mechanical Hard Disk  \ (Slowest, Cost-effective)
+-------------------------------------------------------+
                """.trimIndent()
            )
        )
    )
}
