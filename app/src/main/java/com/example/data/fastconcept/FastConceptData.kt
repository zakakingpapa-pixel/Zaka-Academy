package com.example.data.fastconcept

import com.example.model.FastConcept

object FastConceptData {

    val concepts: List<FastConcept> = listOf(
        FastConcept(
            id = "ram",
            name = "RAM (Random Access Memory)",
            romanUrduNames = listOf("ram", "ram kya hai", "ram kya hota ha", "ram kia hai", "random access memory"),
            aliases = listOf("primary memory", "main memory", "volatile memory"),
            definition = "RAM (Random Access Memory) is the primary volatile internal memory of a computer that temporarily holds data, program instructions, and the operating system actively being executed by the CPU.",
            simpleExplanation = "RAM is like your study desk. When you work on your homework, you place books and notebooks on your desk so you can reach them immediately. When you turn off your lamp and leave, everything goes back into the cupboard (Storage).",
            detailedExplanation = "RAM is byte-addressable semiconductor memory allowing direct read and write access to any memory location in constant time O(1) regardless of physical location. Because RAM is volatile, it requires continuous electrical power to maintain state; all stored contents are lost immediately upon system power loss. Modern computers use DDR4 and DDR5 SDRAM chips operating at multi-gigahertz frequencies with multiple memory channels communicating directly with the CPU memory controller.",
            examples = "When opening Google Chrome with 10 tabs or running a Python program, the code and open web pages are loaded from your SSD/HDD directly into RAM so the CPU can execute them without delay.",
            relatedConcepts = listOf("ROM", "Cache", "Virtual Memory", "CPU", "Storage", "DRAM", "SRAM"),
            importantPoints = listOf(
                "Volatile: loses data when electricity is turned off.",
                "Directly accessible by the CPU via the system memory bus.",
                "Much faster than secondary storage (SSD/HDD), but slower than CPU Cache.",
                "Capacity is typically measured in Gigabytes (e.g., 8 GB, 16 GB, 32 GB)."
            ),
            commonConfusion = "Students often confuse RAM with Storage (Hard Drive/SSD). Storage keeps your files permanently even after shutdown; RAM only holds what is running right now.",
            category = "Hardware & Memory"
        ),
        FastConcept(
            id = "rom",
            name = "ROM (Read-Only Memory)",
            romanUrduNames = listOf("rom", "rom kya hai", "rom kya hota ha", "rom kia ha", "read only memory"),
            aliases = listOf("non-volatile memory", "firmware chip", "bios chip"),
            definition = "ROM (Read-Only Memory) is permanent, non-volatile memory in a computer that stores critical startup firmware instructions (such as the BIOS/UEFI) required to boot the computer.",
            simpleExplanation = "ROM is like a printed textbook. Once the manufacturer writes words into it, you can read it anytime, but you cannot easily erase or rewrite it, and it never disappears when you turn off the lights.",
            detailedExplanation = "ROM chips retain their electrical bit patterns indefinitely without any electrical power. During the boot process (POST - Power-On Self-Test), the CPU reads the very first reset vector instruction from ROM, tests motherboard hardware, initializes memory controllers, and bootstraps the Operating System from the SSD/HDD into RAM. Varieties include PROM, EPROM, and modern electrically erasable Flash EEPROM used for UEFI firmware updates.",
            examples = "The BIOS/UEFI chip on your motherboard that shows the manufacturer logo and boots Windows or Linux; firmware inside a microwave, calculator, or washing machine.",
            relatedConcepts = listOf("RAM", "BIOS", "Firmware", "Booting", "EEPROM", "Motherboard"),
            importantPoints = listOf(
                "Non-volatile: data is preserved permanently even when power is off.",
                "Under normal operation, data can only be read, not written or modified.",
                "Stores POST (Power-On Self-Test) and bootstrap loader programs.",
                "Typically much smaller in capacity (MegaBytes) compared to RAM (GigaBytes)."
            ),
            commonConfusion = "Do not confuse ROM with smartphone storage. Phone specs advertising '128 GB ROM' colloquially refer to internal Flash Storage, not pure Read-Only Memory.",
            category = "Hardware & Memory"
        ),
        FastConcept(
            id = "cpu",
            name = "CPU (Central Processing Unit)",
            romanUrduNames = listOf("cpu", "cpu kya hai", "cpu ka kam kya ha", "cpu kya hota hai", "processor"),
            aliases = listOf("processor", "microprocessor", "brain of computer"),
            definition = "The CPU (Central Processing Unit) is the primary electronic component of a computer that interprets, fetches, decodes, and executes program instructions and coordinates all other hardware subsystems.",
            simpleExplanation = "The CPU is the 'brain' of the computer. Just like your brain receives information from your eyes, thinks about what to do, and tells your hands to write, the CPU takes data, performs calculations, and produces results.",
            detailedExplanation = "A CPU contains three primary architectural sub-components: (1) ALU (Arithmetic Logic Unit) which performs mathematical calculations and Boolean logic, (2) CU (Control Unit) which directs the instruction cycle (Fetch-Decode-Execute) and generates timing signals, and (3) Registers, ultra-fast internal static storage locations (such as Program Counter, Instruction Register, and Accumulator). Modern CPUs integrate multi-level cache (L1, L2, L3) and multiple cores capable of superscalar pipelining and simultaneous multithreading.",
            examples = "Intel Core i7, AMD Ryzen 7, Apple M3, Qualcomm Snapdragon 8 inside mobile phones.",
            relatedConcepts = listOf("ALU", "Control Unit", "Registers", "Cache", "Clock Speed", "Instruction Cycle"),
            importantPoints = listOf(
                "Performs the continuous Fetch-Decode-Execute instruction cycle.",
                "Speed measured in Gigahertz (GHz) representing billions of clock cycles per second.",
                "Modern processors feature multiple independent physical execution cores.",
                "Requires cooling (heatsink/fan) due to thermal dissipation from billions of microscopic transistors."
            ),
            commonConfusion = "The desktop computer tower case is not the CPU. The CPU is a small silicon chip installed inside the socket on the motherboard.",
            category = "Hardware & Architecture"
        ),
        FastConcept(
            id = "gpu",
            name = "GPU (Graphics Processing Unit)",
            romanUrduNames = listOf("gpu", "gpu kya hai", "graphics card kya hai", "graphics card"),
            aliases = listOf("graphics card", "video card", "display adapter"),
            definition = "A GPU (Graphics Processing Unit) is a specialized electronic circuit designed to rapidly manipulate and alter memory to accelerate the creation of images, 3D graphics, and massively parallel matrix computations.",
            simpleExplanation = "While a CPU is like a brilliant mathematics professor who solves one very hard problem at a time, a GPU is like a team of thousands of students who solve thousands of simple math problems simultaneously in parallel.",
            detailedExplanation = "Unlike general-purpose CPUs which have a few cores optimized for sequential instruction latency, GPUs feature an SIMD (Single Instruction Multiple Data) massively parallel architecture with thousands of smaller, efficient stream cores. They excel at matrix multiplication, floating-point vector transformations, polygon rasterization, ray tracing, and deep learning neural network tensor operations via CUDA and OpenCL.",
            examples = "NVIDIA GeForce RTX 4080, AMD Radeon RX 7900, rendering realistic 3D gaming worlds, training AI transformer models.",
            relatedConcepts = listOf("CPU", "VRAM", "Parallel Computing", "Machine Learning", "3D Rendering"),
            importantPoints = listOf(
                "Optimized for parallel throughput rather than single-threaded low latency.",
                "Equipped with its own high-bandwidth memory called VRAM (GDDR6/HBM).",
                "Heavily utilized in AI model training, video editing, and 3D simulation."
            ),
            commonConfusion = "GPUs are not only for gaming; they are now the primary computing engine powering modern Artificial Intelligence and scientific supercomputing.",
            category = "Hardware & Architecture"
        ),
        FastConcept(
            id = "cache",
            name = "Cache Memory",
            romanUrduNames = listOf("cache", "cache memory", "cache kya hai", "cache kya hota hai"),
            aliases = listOf("cpu cache", "l1 cache", "l2 cache", "l3 cache"),
            definition = "Cache memory is a small, extremely high-speed type of volatile SRAM (Static RAM) located directly on or adjacent to the CPU chip, storing frequently used instructions and data to reduce access latency.",
            simpleExplanation = "If RAM is your study desk, Cache is your shirt pocket. Items you need every single second (like your pen) are kept in your shirt pocket so you don't even have to reach across the desk.",
            detailedExplanation = "Cache bridges the performance gap between fast CPU clock cycles (nanosecond fractions) and slower system DRAM. Arranged in hierarchical tiers: Level 1 (L1) is fastest, smallest, core-private (e.g., 64 KB); Level 2 (L2) is slightly larger and slower (e.g., 1 MB); Level 3 (L3) is shared among all cores (e.g., 32 MB). Uses spatial and temporal locality principles: if an address is accessed, nearby addresses will likely be needed soon.",
            examples = "Loop counters and repetitive inner loop computations inside a program reside in L1 cache during execution.",
            relatedConcepts = listOf("CPU", "RAM", "SRAM", "DRAM", "Memory Hierarchy", "Latency"),
            importantPoints = listOf(
                "Built using Static RAM (SRAM) which does not require continuous capacitor refreshing.",
                "Fastest memory in the computer besides CPU internal registers.",
                "Much more expensive per megabyte than standard DRAM."
            ),
            commonConfusion = "Cache is not secondary storage. It cannot be bought as a standalone drive; it is permanently integrated directly onto the CPU silicon die.",
            category = "Hardware & Memory"
        ),
        FastConcept(
            id = "memory",
            name = "Computer Memory",
            romanUrduNames = listOf("memory", "computer memory", "computer memory kya hai"),
            aliases = listOf("primary storage", "internal memory"),
            definition = "Computer memory refers to the internal electronic semiconductor storage components that store binary data and program instructions temporarily or permanently for rapid CPU access.",
            simpleExplanation = "Memory is any component in the computer that can hold information so that the computer remembers what it is currently doing or what it needs to do next.",
            detailedExplanation = "Memory is categorized into Primary Memory (directly accessible by the CPU: Registers, Cache, RAM, ROM) and Secondary Memory (mass storage: SSD, HDD). Primary memory operates at semiconductor speeds (nanoseconds) across memory buses, whereas secondary storage involves I/O controller buses (SATA, NVMe PCIe) with microsecond to millisecond latency.",
            examples = "RAM modules inserted into DIMM slots, BIOS ROM chips soldered on motherboards, L1/L2/L3 cache on the processor.",
            relatedConcepts = listOf("RAM", "ROM", "Storage", "Cache", "Virtual Memory"),
            importantPoints = listOf(
                "Organized hierarchically from fastest/smallest (Registers) to slowest/largest (Secondary storage).",
                "Primary memory is directly accessible via the CPU address bus."
            ),
            commonConfusion = "Colloquially people say 'I need more memory' when they mean hard drive space. Technically, 'memory' means RAM, while disk space is 'storage'.",
            category = "Hardware & Memory"
        ),
        FastConcept(
            id = "storage",
            name = "Secondary Storage",
            romanUrduNames = listOf("storage", "storage kya hai", "secondary storage"),
            aliases = listOf("secondary memory", "auxiliary storage", "permanent storage"),
            definition = "Secondary storage refers to non-volatile data storage devices designed to permanently retain vast amounts of files, operating systems, and programs even when electrical power is completely disconnected.",
            simpleExplanation = "Storage is like the big metal bookshelf or filing cabinet in your room. When you save a photo, game, or document, it stays safely locked in the cabinet until you choose to open it again next year.",
            detailedExplanation = "Secondary storage retains data using magnetic (HDD), optical (CD/DVD/Blu-ray), or solid-state flash memory (NAND flash SSD). Unlike primary memory, the CPU cannot execute code directly from secondary storage; data must first be copied into RAM via system I/O buses (PCIe, NVMe, SATA).",
            examples = "1TB NVMe M.2 SSD inside a laptop, 4TB Western Digital external hard drive, USB flash thumb drives.",
            relatedConcepts = listOf("SSD", "HDD", "RAM", "File System", "Non-volatile"),
            importantPoints = listOf(
                "Non-volatile: completely retains data without electrical power.",
                "Much higher capacity and lower cost per gigabyte than RAM.",
                "Significantly slower read/write speeds compared to system RAM."
            ),
            commonConfusion = "Storage is permanent; RAM is temporary. A 512 GB SSD does not make programs run in memory simultaneously like 16 GB of RAM does.",
            category = "Hardware & Storage"
        ),
        FastConcept(
            id = "ssd",
            name = "SSD (Solid-State Drive)",
            romanUrduNames = listOf("ssd", "ssd kya hai", "ssd kya hota hai", "solid state drive"),
            aliases = listOf("solid state disk", "nvme ssd", "flash drive"),
            definition = "An SSD (Solid-State Drive) is a modern, high-speed, non-volatile storage device that uses semiconductor NAND flash memory chips with no moving mechanical parts to store persistent data.",
            simpleExplanation = "An SSD is like a giant, supercharged USB thumb drive built permanently into your computer. Because it has no spinning disks or moving needles, it opens files and boots your system almost instantly.",
            detailedExplanation = "SSDs replace mechanical platters with arrays of non-volatile floating-gate or charge-trap NAND flash memory cells managed by a dedicated microcontroller. NVMe (Non-Volatile Memory Express) SSDs communicate directly over high-speed PCIe lanes, achieving sequential read speeds exceeding 7,000 MB/s and random access latency under 20 microseconds, eliminating the mechanical seek latency of HDDs.",
            examples = "Samsung 990 Pro NVMe M.2 SSD, Kingston SATA SSD, smartphone internal UFS storage.",
            relatedConcepts = listOf("HDD", "Storage", "Flash Memory", "NVMe", "SATA"),
            importantPoints = listOf(
                "No moving parts: silent, shock-resistant, low power consumption.",
                "Boot times are drastically reduced (typically under 10 seconds).",
                "Up to 10 to 50 times faster data transfer than traditional mechanical hard drives."
            ),
            commonConfusion = "SSDs are not immune to wearing out; each flash cell has a finite number of write/erase cycles, managed automatically by wear-leveling firmware algorithms.",
            category = "Hardware & Storage"
        ),
        FastConcept(
            id = "hdd",
            name = "HDD (Hard Disk Drive)",
            romanUrduNames = listOf("hdd", "hard disk", "hard drive kya hai", "hard drive"),
            aliases = listOf("hard disk", "hard drive", "magnetic storage"),
            definition = "An HDD (Hard Disk Drive) is an electromechanical data storage device that uses magnetic storage to store and retrieve digital information using rapidly rotating platters coated with magnetic material.",
            simpleExplanation = "An HDD works like an old record player. Metal discs spin around at thousands of rotations per minute while a tiny mechanical arm moves across them to read and write magnetic patterns.",
            detailedExplanation = "HDDs store data on rigid magnetic platters rotating at standard speeds of 5400 or 7200 RPM (Rotations Per Minute). A read/write actuator head floats on a cushion of air nanometers above the spinning surface. Performance is constrained by physical mechanics: seek time (time for the arm to move to the correct track) and rotational latency (time for the sector to spin under the head), yielding typical sequential speeds of 100-200 MB/s.",
            examples = "Seagate Barracuda 2TB internal hard drive, Western Digital external backup drive.",
            relatedConcepts = listOf("SSD", "Storage", "Magnetic Media", "RPM", "Sectors"),
            importantPoints = listOf(
                "Electromechanical with spinning platters and moving heads.",
                "Very cost-effective for mass storage of bulk archival data (movies, backups).",
                "Vulnerable to mechanical failure, physical shock, and drops."
            ),
            commonConfusion = "HDDs are mechanical, whereas SSDs are entirely solid-state electronic chips.",
            category = "Hardware & Storage"
        ),
        FastConcept(
            id = "motherboard",
            name = "Motherboard",
            romanUrduNames = listOf("motherboard", "motherboard kya hai", "mainboard", "system board"),
            aliases = listOf("mainboard", "system board", "logic board", "mobo"),
            definition = "The motherboard is the primary printed circuit board (PCB) in a computer that houses the CPU, memory, and expansion slots, and interconnects all internal hardware components through electronic buses.",
            simpleExplanation = "The motherboard is like the nervous system or spinal cord of the computer. It connects the brain (CPU), the working memory (RAM), and the limbs (peripherals) so they can communicate seamlessly.",
            detailedExplanation = "The motherboard contains the CPU socket, RAM DIMM slots, PCIe expansion slots, power delivery circuits (VRMs), chipset (Northbridge/Southbridge or unified PCH), SATA/NVMe storage connectors, and external I/O ports. High-speed microscopic copper traces printed into multiple layers of the PCB route address, data, and control bus signals across components with precise clock synchronization.",
            examples = "ASUS ROG Strix B650 motherboard, MSI B550 Gaming Plus, Apple MacBook logic board.",
            relatedConcepts = listOf("CPU", "RAM", "Bus", "Chipset", "BIOS", "PCIe"),
            importantPoints = listOf(
                "Connects all internal and external computer hardware components together.",
                "Contains the BIOS/UEFI chip and CMOS battery.",
                "Determines which CPU generation, RAM type, and expansion cards the system supports."
            ),
            commonConfusion = "The motherboard does not do high-level computation itself; it provides the communication pathways and electrical routing for the processor, memory, and devices.",
            category = "Hardware & Architecture"
        ),
        FastConcept(
            id = "operating_system",
            name = "Operating System (OS)",
            romanUrduNames = listOf("operating system", "os kya hai", "operating system kya hota hai", "os"),
            aliases = listOf("OS", "system software", "kernel"),
            definition = "An Operating System (OS) is essential system software that manages computer hardware and software resources, provides common services for computer programs, and acts as an intermediary between users and hardware.",
            simpleExplanation = "The OS is like the manager of a busy restaurant. It makes sure the chef (CPU) gets orders, tables (RAM) are assigned to guests, and bills (files) are filed away safely, so the customers (you) can just enjoy the meal.",
            detailedExplanation = "The core of the OS is the Kernel, which runs in privileged supervisor mode. Core responsibilities include: (1) Process Management (CPU scheduling, context switching, multitasking), (2) Memory Management (virtual memory paging, RAM allocation), (3) File System Management (NTFS, ext4, hierarchical directory trees), (4) Device Management (device drivers, hardware abstraction), and (5) Security and user access controls.",
            examples = "Microsoft Windows 11, Linux (Ubuntu, Debian, Fedora), macOS, Android, iOS.",
            relatedConcepts = listOf("Kernel", "Software", "Process", "Thread", "Virtual Memory", "File System"),
            importantPoints = listOf(
                "First software loaded into RAM during the computer boot process.",
                "Provides user interfaces: GUI (Graphical User Interface) and CLI (Command Line Interface).",
                "Ensures applications cannot directly crash hardware or interfere with each other's memory."
            ),
            commonConfusion = "Application software (like MS Word or Chrome) runs ON TOP of the operating system; they cannot interact with hardware directly without the OS.",
            category = "Software & Systems"
        ),
        FastConcept(
            id = "software",
            name = "Software",
            romanUrduNames = listOf("software", "software kya hai", "software kya hota hai"),
            aliases = listOf("computer program", "application", "code"),
            definition = "Software is a collection of instructions, data, or programs used to operate computers and execute specific tasks. It is the intangible, non-physical component of a computer system.",
            simpleExplanation = "If hardware is your physical body, software is your mind, thoughts, and knowledge. You cannot physically touch software, but without it, the hardware cannot do anything useful.",
            detailedExplanation = "Software is broadly divided into two main categories: (1) System Software, which controls internal hardware operations (Operating Systems, Device Drivers, Firmware, Compilers), and (2) Application Software, designed to assist users in completing personal, educational, or business tasks (Web Browsers, Word Processors, Games, Calculators).",
            examples = "System software: Linux, Windows, NVIDIA driver. Application software: VS Code, Google Docs, WhatsApp.",
            relatedConcepts = listOf("Hardware", "Operating System", "Application", "Firmware", "Programming"),
            importantPoints = listOf(
                "Intangible: cannot be physically touched.",
                "Written in programming languages (C++, Python, Kotlin) and compiled into binary machine code.",
                "Easily upgraded, modified, or reinstalled without replacing physical parts."
            ),
            commonConfusion = "Software is not physical media like DVDs or USB sticks; the data stored electronically inside them is the software.",
            category = "Foundations"
        ),
        FastConcept(
            id = "hardware",
            name = "Hardware",
            romanUrduNames = listOf("hardware", "hardware kya hai", "hardware kya hota hai"),
            aliases = listOf("computer hardware", "physical components"),
            definition = "Hardware refers to the physical, tangible electronic and electromechanical components of a computer system that you can physically touch and see.",
            simpleExplanation = "Hardware is anything in the computer you can physically kick or hold in your hand: keyboard, monitor, wires, chips, and mouse.",
            detailedExplanation = "Hardware comprises five major functional categories according to computer architecture: (1) Input devices (keyboard, mouse, scanner), (2) Processing devices (CPU, GPU), (3) Storage devices (RAM, ROM, SSD, HDD), (4) Output devices (monitor, printer, speakers), and (5) Communication/Connectivity devices (motherboard, network card, cables).",
            examples = "Keyboard, Monitor, RAM stick, CPU chip, Hard drive, Power supply unit (PSU).",
            relatedConcepts = listOf("Software", "Firmware", "CPU", "Motherboard", "Peripherals"),
            importantPoints = listOf(
                "Tangible and physical.",
                "Subject to physical wear, tear, and electronic degradation over time.",
                "Cannot perform tasks without software instructions."
            ),
            commonConfusion = "Hardware and software are interdependent: hardware cannot operate without software instructions, and software cannot execute without hardware circuits.",
            category = "Foundations"
        ),
        FastConcept(
            id = "algorithm",
            name = "Algorithm",
            romanUrduNames = listOf("algorithm", "algorithm kya hai", "algorithm kya hota ha", "algorithm asan lafzon ma samjhao"),
            aliases = listOf("step-by-step procedure", "logic plan"),
            definition = "An algorithm is a finite, ordered sequence of unambiguous, well-defined instructions designed to solve a specific computational problem or perform a calculation.",
            simpleExplanation = "An algorithm is like a cooking recipe. If you follow the recipe step 1, step 2, step 3 in exact order, you will always bake a delicious cake without mistakes.",
            detailedExplanation = "Properties of an algorithm: (1) Input (zero or more values), (2) Output (at least one result), (3) Definiteness (each instruction is clear and unambiguous), (4) Finiteness (must terminate after a countable number of steps), and (5) Effectiveness (operations must be basic and feasible). Algorithms are analyzed for efficiency using Big-O time and space complexity.",
            examples = "Algorithm to find the largest of two numbers: Step 1: Start. Step 2: Read A and B. Step 3: If A > B print A else print B. Step 4: Stop.",
            relatedConcepts = listOf("Flowchart", "Pseudocode", "Big-O", "Sorting", "Searching", "Data Structures"),
            importantPoints = listOf(
                "Language-independent: can be written in English, pseudocode, or implemented in Python, C++, Java.",
                "Must always stop (finite steps) and never run infinitely.",
                "Forms the foundational core of computer programming and problem solving."
            ),
            commonConfusion = "An algorithm is not programming code; it is the logical plan that exists BEFORE you write code in any specific language.",
            category = "Algorithms & Logic"
        ),
        FastConcept(
            id = "flowchart",
            name = "Flowchart",
            romanUrduNames = listOf("flowchart", "flowchart kya hai", "flow chart kya hota hai", "flowchart symbols"),
            aliases = listOf("logic diagram", "process flow"),
            definition = "A flowchart is a standardized graphical or visual representation of an algorithm using specialized geometric symbols interconnected by arrows to indicate process flow.",
            simpleExplanation = "A flowchart is a visual map of a plan. Instead of reading lines of text, you follow arrows through boxes and diamonds to see what steps to take.",
            detailedExplanation = "Standard ANSI/ISO flowchart symbols: (1) Oval/Rounded rectangle: Terminator (Start/Stop), (2) Parallelogram: Input/Output operations (Read A, Print B), (3) Rectangle: Process/Calculation (Total = A + B), (4) Diamond: Decision/Condition (Is A > 10? with Yes/No branch paths), (5) Arrow lines: Flow of control, and (6) Small Circle: Connector.",
            examples = "A flowchart determining whether a student passed an exam: Start -> Input marks -> Is marks >= 50? (Yes -> Print Pass; No -> Print Fail) -> Stop.",
            relatedConcepts = listOf("Algorithm", "Pseudocode", "Decision Making", "Control Flow"),
            importantPoints = listOf(
                "Makes complex algorithmic logic easy to understand visually.",
                "Helps in debugging and tracing logic before coding.",
                "Flow direction is normally from top to bottom and left to right."
            ),
            commonConfusion = "Rectangles are for calculations or actions; parallelograms are strictly reserved for Input and Output.",
            category = "Algorithms & Logic"
        ),
        FastConcept(
            id = "binary",
            name = "Binary Number System",
            romanUrduNames = listOf("binary", "binary number", "binary kya hai", "binary kya hota hai", "binary system"),
            aliases = listOf("base-2", "machine code", "bits"),
            definition = "The binary number system is a base-2 positional numeral system that uses only two discrete numerical symbols: 0 and 1, called bits (binary digits).",
            simpleExplanation = "Inside a computer, everything is made of millions of tiny microscopic electric switches (transistors). A switch can only be OFF (0) or ON (1). Therefore, computers use binary to count and store everything.",
            detailedExplanation = "Each position in a binary number represents a power of 2 (starting with 2^0 from the right: 1, 2, 4, 8, 16, 32, 64, 128...). For example, the binary number 1011 in base 10 is: (1*8) + (0*4) + (1*2) + (1*1) = 11. All computer data—numbers, text (ASCII), images, audio, and video—are fundamentally encoded into binary bit streams.",
            examples = "Binary 0000 = 0, 0001 = 1, 0010 = 2, 0011 = 3, 0100 = 4, 1000 = 8, 1111 = 15.",
            relatedConcepts = listOf("Decimal", "Hexadecimal", "Bit", "Byte", "ASCII", "Logic Gates"),
            importantPoints = listOf(
                "Base 2 numbering system; uses only digits 0 and 1.",
                "1 Byte = 8 consecutive bits.",
                "The native internal language understood directly by computer hardware."
            ),
            commonConfusion = "Binary numbers are read mathematically by powers of two, not as thousands or hundreds. '10' in binary is two, not ten!",
            category = "Data Representation"
        ),
        FastConcept(
            id = "decimal",
            name = "Decimal Number System",
            romanUrduNames = listOf("decimal", "decimal system", "decimal kya hai", "base 10"),
            aliases = listOf("base-10", "denary", "arabic numerals"),
            definition = "The decimal number system is a base-10 positional numeral system used by humans, utilizing ten distinct digits: 0, 1, 2, 3, 4, 5, 6, 7, 8, and 9.",
            simpleExplanation = "The decimal system is the regular everyday counting system humans use because we have ten fingers on our hands.",
            detailedExplanation = "Each positional place in decimal represents an increasing power of 10 (units 10^0, tens 10^1, hundreds 10^2, thousands 10^3). Computers must convert human decimal inputs into binary for digital processing, and convert binary back to decimal for human output display.",
            examples = "Number 245 = (2 * 100) + (4 * 10) + (5 * 1).",
            relatedConcepts = listOf("Binary", "Hexadecimal", "Octal", "Data Representation"),
            importantPoints = listOf(
                "Base 10 system with digits 0 through 9.",
                "Standard universal numerical language for human mathematical calculation."
            ),
            commonConfusion = "Computers do not natively think in decimal; they convert everything to base-2 binary transistors.",
            category = "Data Representation"
        ),
        FastConcept(
            id = "hexadecimal",
            name = "Hexadecimal Number System",
            romanUrduNames = listOf("hexadecimal", "hex kya hai", "hexadecimal kya hota hai", "base 16"),
            aliases = listOf("base-16", "hex"),
            definition = "Hexadecimal is a base-16 positional numeral system that uses 16 symbols: 0–9 to represent values zero to nine, and A–F to represent values ten to fifteen.",
            simpleExplanation = "Binary numbers are very long and hard for humans to read (e.g. 11111010). Hexadecimal is a convenient human shorthand where exactly 4 binary bits are neatly compressed into a single symbol.",
            detailedExplanation = "Values: A=10, B=11, C=12, D=13, E=14, F=15. Since 2^4 = 16, each hex digit corresponds exactly to a 4-bit nibble. For example, binary 1111 0000 becomes 0xF0 in hex. Hexadecimal is heavily utilized in computer engineering for memory addresses, IPv6 networking, and web color codes.",
            examples = "Color code #FF0000 (pure red: Red=255, Green=0, Blue=0); RAM memory address 0x7FFF5FBFFD04.",
            relatedConcepts = listOf("Binary", "Decimal", "Bit", "Byte", "Memory Address", "Color Codes"),
            importantPoints = listOf(
                "Base 16 system; symbols 0-9 and A-F.",
                "One hex digit represents exactly 4 binary bits (one nibble).",
                "Two hex digits represent exactly 1 byte (8 bits)."
            ),
            commonConfusion = "Remember that A is 10, B is 11, C is 12, D is 13, E is 14, and F is 15.",
            category = "Data Representation"
        ),
        FastConcept(
            id = "database",
            name = "Database",
            romanUrduNames = listOf("database", "database kya hai", "database kya hota hai", "db"),
            aliases = listOf("DBMS", "data store", "RDBMS"),
            definition = "A database is an organized, structured collection of digital data stored electronically in a computer system, managed by a Database Management System (DBMS).",
            simpleExplanation = "A database is like a digital library with super-smart librarians. It keeps millions of student records neatly organized in tables so anyone can find a specific record in a fraction of a second.",
            detailedExplanation = "Relational Databases (RDBMS) organize data into structured tables with rows (records) and columns (attributes) governed by schemas, primary keys, foreign keys, and ACID (Atomicity, Consistency, Isolation, Durability) guarantees, queried using SQL. Non-relational (NoSQL) databases store unstructured or semi-structured data as JSON documents, key-value pairs, or graphs.",
            examples = "MySQL, PostgreSQL, SQLite (used in mobile apps), Oracle, MongoDB.",
            relatedConcepts = listOf("SQL", "Table", "Primary Key", "Foreign Key", "RDBMS", "ACID"),
            importantPoints = listOf(
                "Ensures data integrity, security, and concurrent multi-user access.",
                "Prevents unnecessary data redundancy and duplication.",
                "Managed via software called DBMS (Database Management System)."
            ),
            commonConfusion = "A database is not just a spreadsheet like Excel; a DBMS handles millions of records with transaction safety, security permissions, and relational integrity.",
            category = "Databases"
        ),
        FastConcept(
            id = "network",
            name = "Computer Network",
            romanUrduNames = listOf("network", "computer network kya hai", "network kya hota hai"),
            aliases = listOf("data network", "LAN", "WAN"),
            definition = "A computer network is a system of two or more interconnected computing devices that communicate and share resources, files, and data using common communication protocols.",
            simpleExplanation = "A network is like a group of friends connected with walkie-talkies. Because they are connected, they can talk to each other, share notes, and share a single game console.",
            detailedExplanation = "Networks are classified by geographical scope: LAN (Local Area Network - single room/building), MAN (Metropolitan Area Network - city), and WAN (Wide Area Network - country or global). Devices (nodes) connect via wired transmission media (Ethernet twisted pair, fiber optics) or wireless media (Wi-Fi, Bluetooth) routed via switches, routers, and firewalls.",
            examples = "Your school computer lab LAN, home Wi-Fi connecting your phone, laptop, and smart TV.",
            relatedConcepts = listOf("LAN", "WAN", "Internet", "Router", "Switch", "IP Address", "Topology"),
            importantPoints = listOf(
                "Enables hardware sharing (e.g., multiple computers sharing one printer).",
                "Enables rapid file sharing and centralized communication.",
                "Governed by standard protocol suites (TCP/IP)."
            ),
            commonConfusion = "The Internet is a massive global network, but any two computers connected with a cable in a room also form a network.",
            category = "Networking"
        ),
        FastConcept(
            id = "internet",
            name = "The Internet",
            romanUrduNames = listOf("internet", "internet kya hai", "internet kya hota hai"),
            aliases = listOf("world wide network", "network of networks", "web"),
            definition = "The Internet is a vast, global system of interconnected computer networks that uses the standardized Internet Protocol Suite (TCP/IP) to link billions of devices worldwide.",
            simpleExplanation = "The Internet is the ultimate highway system connecting every computer network on planet Earth together so you can send a message to someone on the other side of the globe in milliseconds.",
            detailedExplanation = "The Internet originated from ARPANET in the late 1960s. It is decentralized: no single entity owns or controls it. It operates across undersea fiber optic cables, satellite links, and major internet exchange points (IXPs). Services running on top of the Internet include the World Wide Web (HTTP/HTTPS), Email (SMTP/IMAP), File Transfer (FTP), and VoIP.",
            examples = "Browsing websites, sending emails, streaming YouTube videos, video calling on WhatsApp.",
            relatedConcepts = listOf("Network", "WWW", "IP Address", "DNS", "TCP/IP", "HTTP"),
            importantPoints = listOf(
                "A global 'network of networks'.",
                "Relies on the standard TCP/IP protocol suite.",
                "The World Wide Web (WWW) is a service ON the internet, not the internet itself."
            ),
            commonConfusion = "The Internet and the World Wide Web are not the same thing. The Internet is the physical network infrastructure; the Web (pages viewed in browsers) is just one service running on it.",
            category = "Networking"
        ),
        FastConcept(
            id = "ip_address",
            name = "IP Address",
            romanUrduNames = listOf("ip address", "ip address kya hai", "ip kya hota hai", "internet protocol address"),
            aliases = listOf("IP", "network address", "logical address"),
            definition = "An IP (Internet Protocol) address is a unique numerical identifier assigned to each device connected to a computer network that uses the Internet Protocol for communication.",
            simpleExplanation = "An IP address is like your house's postal mailing address. If the postman doesn't have your address, letters and packages cannot reach your doorstep.",
            detailedExplanation = "Two primary versions exist: (1) IPv4: 32-bit address represented as four decimal numbers separated by dots (e.g. 192.168.1.1), providing approximately 4.3 billion unique addresses; (2) IPv6: 128-bit address represented in hexadecimal separated by colons (e.g. 2001:0db8:85a3::8a2e:0370:7334), created to solve IPv4 address exhaustion with 340 undecillion addresses.",
            examples = "192.168.0.1 (common router private IP), 8.8.8.8 (Google Public DNS server).",
            relatedConcepts = listOf("DNS", "Internet", "Router", "IPv4", "IPv6", "Subnet"),
            importantPoints = listOf(
                "Enables packets of data to find their correct destination across global routers.",
                "Can be static (permanent) or dynamic (assigned temporarily via DHCP).",
                "Divided into Network ID and Host ID portions."
            ),
            commonConfusion = "An IP address is a logical network address that changes when you connect to a different network; a MAC address is a permanent physical hardware identifier burned into your network card.",
            category = "Networking"
        ),
        FastConcept(
            id = "dns",
            name = "DNS (Domain Name System)",
            romanUrduNames = listOf("dns", "dns kya hai", "dns kya hota hai", "domain name system"),
            aliases = listOf("phonebook of the internet", "domain resolver"),
            definition = "DNS (Domain Name System) is the hierarchical decentralized naming system that translates human-friendly domain names (like google.com) into machine-readable numerical IP addresses (like 142.250.190.46).",
            simpleExplanation = "DNS is the phonebook of the Internet. You don't memorize everyone's 10-digit phone number in your head; you tap their name (e.g., 'Ali'), and your phone looks up their number automatically.",
            detailedExplanation = "When you type a URL into a browser, a DNS resolution query travels through a recursive hierarchy: (1) Browser local cache, (2) Recursive Resolver (ISP), (3) Root Name Servers, (4) Top-Level Domain (TLD) servers (e.g. .com, .org, .pk), and (5) Authoritative Name Server, which returns the definitive A or AAAA record pointing to the server's IP address.",
            examples = "Translating 'wikipedia.org' into 208.80.154.224.",
            relatedConcepts = listOf("IP Address", "Internet", "URL", "HTTP", "Web Server"),
            importantPoints = listOf(
                "Eliminates the need for humans to memorize complex numeric IP addresses.",
                "Uses port 53 over UDP/TCP.",
                "Employs distributed caching to speed up subsequent website lookups."
            ),
            commonConfusion = "Without DNS, the internet would still work if you typed raw IP addresses, but nobody could use names like google.com or youtube.com.",
            category = "Networking"
        ),
        FastConcept(
            id = "compiler",
            name = "Compiler",
            romanUrduNames = listOf("compiler", "compiler kya hai", "compiler kya hota hai", "compiler vs interpreter"),
            aliases = listOf("code translator", "build tool"),
            definition = "A compiler is a specialized software program that translates entire source code written in a high-level programming language into machine code (binary) in one single go before program execution.",
            simpleExplanation = "A compiler is like a book translator who takes an entire English book, translates every page into Urdu, and prints a complete new Urdu book. Once translated, you can read the Urdu book anytime very quickly without the translator.",
            detailedExplanation = "Compilation involves structured phases: Lexical Analysis (tokenization), Syntax Analysis (parsing parse trees), Semantic Analysis (type checking), Intermediate Code Generation, Code Optimization, and Target Code Generation (producing .exe or object binaries). If there are any syntax errors, compilation halts and reports all errors at once.",
            examples = "GCC (translates C and C++), rustc (Rust), javac (compiles Java source into bytecode).",
            relatedConcepts = listOf("Interpreter", "Assembler", "Source Code", "Machine Code", "Syntax Error"),
            importantPoints = listOf(
                "Translates the entire program at once before execution.",
                "Generates an independent executable file (.exe or binary).",
                "Program runs very fast once compiled because translation is already complete."
            ),
            commonConfusion = "A compiler does not run your program; it transforms source code into an executable file that the OS and CPU run later.",
            category = "Programming & Compilers"
        ),
        FastConcept(
            id = "interpreter",
            name = "Interpreter",
            romanUrduNames = listOf("interpreter", "interpreter kya hai", "interpreter kya hota hai"),
            aliases = listOf("line by line translator", "script engine"),
            definition = "An interpreter is a language processor that translates and executes high-level source code line-by-line, instruction-by-instruction, directly at runtime without producing a standalone executable file.",
            simpleExplanation = "An interpreter is like a live speech translator standing next to a foreign diplomat. As soon as the diplomat speaks one sentence, the interpreter immediately translates that sentence before moving to the next.",
            detailedExplanation = "An interpreter reads a statement, analyzes it, converts it into immediate machine actions, executes it, and then proceeds to the next line. If an error occurs on line 50, lines 1 through 49 execute successfully, and execution immediately stops at line 50 with a runtime traceback.",
            examples = "CPython (standard Python interpreter), JavaScript V8 engine inside browsers and Node.js, Ruby interpreter.",
            relatedConcepts = listOf("Compiler", "Source Code", "Runtime", "Python", "JavaScript"),
            importantPoints = listOf(
                "Translates and executes code line-by-line.",
                "Stops immediately upon finding the first error.",
                "Execution speed is generally slower than pre-compiled binaries, but debugging is faster and more interactive."
            ),
            commonConfusion = "Python is commonly called an interpreted language, though CPython internally compiles source into .pyc bytecode before interpreting it in the Python Virtual Machine.",
            category = "Programming & Compilers"
        ),
        FastConcept(
            id = "programming_language",
            name = "Programming Language",
            romanUrduNames = listOf("programming language", "programming language kya hai", "coding language"),
            aliases = listOf("coding language", "source language"),
            definition = "A programming language is a formal system of notation comprising a set of syntax rules and vocabulary used by programmers to write software instructions that a computer can execute.",
            simpleExplanation = "A programming language is the bridge language between human thinking and computer silicon. It lets you write commands in understandable words (like 'if', 'print', 'while') that get turned into computer actions.",
            detailedExplanation = "Classified into Low-Level Languages (Machine code 0s/1s, Assembly with mnemonics like MOV/ADD directly tied to CPU architecture) and High-Level Languages (Python, Java, C++, C#, Kotlin) which abstract hardware details with readable English-like syntax and automated memory management.",
            examples = "Python, C++, Java, JavaScript, Kotlin, C, Rust.",
            relatedConcepts = listOf("Compiler", "Interpreter", "Syntax", "Algorithm", "Variables"),
            importantPoints = listOf(
                "Has strict syntax (grammar) rules; errors prevent execution.",
                "High-level languages are portable across different computer hardware architectures.",
                "Low-level languages provide maximum execution speed and direct hardware control."
            ),
            commonConfusion = "HTML and CSS are markup and styling languages, not general-purpose programming languages, because they lack logical control flow (loops, conditionals, algorithms).",
            category = "Programming & Compilers"
        ),
        FastConcept(
            id = "variable",
            name = "Variable",
            romanUrduNames = listOf("variable", "variable kya hai", "variable kya hota hai"),
            aliases = listOf("identifier", "memory container"),
            definition = "A variable is a named storage location in computer memory (RAM) that holds a value which can be inspected, modified, or updated during program execution.",
            simpleExplanation = "A variable is like a cardboard box with a label on it. If you label the box 'age' and put the number 15 inside, whenever you ask for 'age', the computer looks inside the box and gives you 15.",
            detailedExplanation = "A variable possesses an identifier (name), a data type (e.g. integer, float, string, boolean), an address in memory where the bits are stored, and a value. In statically typed languages (C++, Java), the type must be declared explicitly; in dynamically typed languages (Python, JavaScript), types are bound at runtime.",
            examples = "In Python: age = 15; student_name = 'Zaka'; score = 98.5.",
            relatedConcepts = listOf("Constant", "Data Types", "Memory", "Scope", "Assignment"),
            importantPoints = listOf(
                "Value can change (vary) while the program is running.",
                "Must follow identifier naming rules (cannot start with a digit, no spaces, no reserved keywords).",
                "Reserves memory space based on its data type."
            ),
            commonConfusion = "The equals sign '=' in programming is an assignment operator (put the right-hand value into the left-hand variable), not a mathematical test of equality.",
            category = "Programming Concepts"
        ),
        FastConcept(
            id = "function",
            name = "Function (Subroutine)",
            romanUrduNames = listOf("function", "function kya hai", "function kya hota hai", "methods"),
            aliases = listOf("method", "subroutine", "procedure"),
            definition = "A function is a self-contained, reusable block of organized code designed to perform a single specific task, which can accept input parameters and return a result.",
            simpleExplanation = "A function is like a blender or kitchen appliance. You give it ingredients (inputs/parameters), it blends them according to a fixed recipe, and pours out a glass of juice (return value).",
            detailedExplanation = "Functions enforce the DRY (Don't Repeat Yourself) principle. When a function is called, the CPU pushes return addresses and local variables onto the call stack, jumps program counter to the function body, executes instructions, and pops the stack frame when returning. Functions can be built-in (e.g. print(), len()) or user-defined.",
            examples = "def calculate_average(a, b): return (a + b) / 2",
            relatedConcepts = listOf("Parameter", "Return Value", "Recursion", "Scope", "Call Stack"),
            importantPoints = listOf(
                "Promotes code reusability, modularity, and easy debugging.",
                "Accepts inputs called parameters/arguments and yields output via return.",
                "Local variables declared inside a function exist only during its execution."
            ),
            commonConfusion = "Defining a function does not execute it. A function only runs when you explicitly call it by name with parentheses (e.g., my_function()).",
            category = "Programming Concepts"
        ),
        FastConcept(
            id = "loop",
            name = "Loop (Iteration)",
            romanUrduNames = listOf("loop", "loop kya hai", "loop kya hota hai", "for loop", "while loop"),
            aliases = listOf("iteration", "repetition", "cycle"),
            definition = "A loop is a control flow structure in programming that repeatedly executes a block of code as long as a specified Boolean condition remains true.",
            simpleExplanation = "A loop is like a runner doing laps around a track. You tell the runner: 'Keep running around the track until you complete 10 laps, then stop.'",
            detailedExplanation = "Major loop types include: (1) Counter-controlled loop (e.g., 'for' loop) when the exact iteration count is predetermined, and (2) Condition-controlled loop (e.g., 'while' loop) when repetition depends on a dynamic runtime condition. Every loop requires initialization, a condition check, and an update/increment step to avoid infinite looping.",
            examples = "for i in range(5): print('Class 9 CS') — prints the text 5 times.",
            relatedConcepts = listOf("Condition", "Infinite Loop", "Break", "Continue", "Recursion"),
            importantPoints = listOf(
                "Automates repetitive computational tasks without repeating code lines.",
                "Must always have a termination condition to prevent infinite freezing loops.",
                "Common keywords: 'for', 'while', 'break' (exit loop), 'continue' (skip to next iteration)."
            ),
            commonConfusion = "If you forget to increment your counter inside a while loop, the condition will stay true forever, causing an infinite loop and hanging your program.",
            category = "Programming Concepts"
        ),
        FastConcept(
            id = "array",
            name = "Array",
            romanUrduNames = listOf("array", "array kya hai", "array kya hota hai", "list"),
            aliases = listOf("list", "indexed collection", "vector"),
            definition = "An array is a linear data structure consisting of a collection of elements, each identified by at least one array index, stored in contiguous memory locations.",
            simpleExplanation = "An array is like an egg carton with numbered slots (0, 1, 2, 3...). Each slot holds an item, and you can grab the item in slot number 3 immediately.",
            detailedExplanation = "Because elements are stored contiguously in physical RAM, any element can be accessed in constant time O(1) using the formula: Base_Address + (Index * Element_Size). In most programming languages, array indices are 0-based (first item is at index 0). Standard arrays are fixed in size; dynamic arrays (like Python lists or ArrayLists) resize dynamically.",
            examples = "numbers = [10, 20, 30, 40, 50]; numbers[0] gives 10.",
            relatedConcepts = listOf("Data Structures", "Index", "Linked List", "String", "Memory"),
            importantPoints = listOf(
                "Zero-indexed: the first item is at index 0, not index 1.",
                "Provides ultra-fast O(1) random access to any element by index.",
                "Inserting or deleting elements from the middle requires shifting elements (O(n))."
            ),
            commonConfusion = "Accessing index 5 in an array of size 5 will throw an 'Index Out of Bounds' error because indices run from 0 to 4.",
            category = "Data Structures"
        ),
        FastConcept(
            id = "class_object",
            name = "Class and Object (OOP)",
            romanUrduNames = listOf("class", "object", "oop kya hai", "class kya hai", "object kya hai"),
            aliases = listOf("OOP", "blueprint", "instance"),
            definition = "A Class is an extensible program-code-template (blueprint) for creating objects, providing initial values for state (attributes) and implementations of behavior (methods). An Object is an active instance of a class.",
            simpleExplanation = "A Class is the architectural blueprint of a house. The blueprint itself is paper you cannot sleep in. An Object is the actual brick-and-mortar house built from that blueprint.",
            detailedExplanation = "The four pillars of Object-Oriented Programming (OOP) are: (1) Encapsulation (bundling data and methods together and restricting direct access), (2) Inheritance (deriving new classes from existing parent classes), (3) Polymorphism (allowing entities to take multiple forms), and (4) Abstraction (hiding internal complex details and showing only necessary interfaces).",
            examples = "Class: Car (blueprint with color, speed, drive()). Object: my_car = Car('Red', 120).",
            relatedConcepts = listOf("OOP", "Inheritance", "Encapsulation", "Polymorphism", "Abstraction"),
            importantPoints = listOf(
                "A class defines attributes (data) and methods (actions).",
                "Multiple independent objects can be instantiated from one single class.",
                "Fundamental paradigm behind Java, C++, Python, Kotlin, and C#."
            ),
            commonConfusion = "You don't put data inside a class; you put data inside the instantiated object created from that class.",
            category = "Programming Concepts"
        ),
        FastConcept(
            id = "python",
            name = "Python Programming Language",
            romanUrduNames = listOf("python", "python kya hai", "pyhton", "python programming"),
            aliases = listOf("python3", "scripting language"),
            definition = "Python is a high-level, interpreted, dynamically typed, general-purpose programming language renowned for its elegant, human-readable syntax and extensive standard libraries.",
            simpleExplanation = "Python is the friendliest programming language in the world. Writing code in Python feels almost like writing plain English sentences.",
            detailedExplanation = "Created by Guido van Rossum in 1991. Employs whitespace indentation rather than curly braces to define code block scope. Highly versatile across Web development (Django, Flask), Artificial Intelligence & Machine Learning (TensorFlow, PyTorch), Data Science (Pandas, NumPy), Automation, and Educational CS curricula.",
            examples = "print('Hello, ZAKA Academy!')",
            relatedConcepts = listOf("Programming Language", "Interpreter", "Variables", "AI", "Libraries"),
            importantPoints = listOf(
                "Clean, readable syntax that minimizes lines of code.",
                "Huge ecosystem of open-source third-party packages via pip.",
                "Cross-platform: runs on Windows, macOS, Linux, Raspberry Pi."
            ),
            commonConfusion = "Indentation in Python is not optional styling; incorrect indentation will trigger an 'IndentationError'.",
            category = "Languages"
        ),
        FastConcept(
            id = "html",
            name = "HTML (HyperText Markup Language)",
            romanUrduNames = listOf("html", "html kya hai", "html kya hota hai"),
            aliases = listOf("markup language", "web structure"),
            definition = "HTML (HyperText Markup Language) is the standard markup language used to structure web pages and display text, images, links, and multimedia in web browsers.",
            simpleExplanation = "HTML is the skeleton of a website. Just like your bones hold up your body, HTML tags give structure to paragraphs, headings, and images on a web page.",
            detailedExplanation = "HTML consists of nested elements represented by tags (e.g. <html>, <head>, <body>, <h1>, <p>, <a>, <img>). Modern HTML5 introduced semantic tags (<article>, <section>, <nav>) and native multimedia elements (<video>, <audio>, <canvas>). HTML documents form the DOM (Document Object Model) parsed by browsers.",
            examples = "<h1>Welcome to Zaka Academy</h1><p>Computer Science learning platform.</p>",
            relatedConcepts = listOf("CSS", "JavaScript", "DOM", "Web Browser", "HTTP"),
            importantPoints = listOf(
                "Not a programming language; it is a markup language.",
                "Uses opening tags like <p> and closing tags like </p>.",
                "Forms the structural foundation of every page on the World Wide Web."
            ),
            commonConfusion = "HTML does not contain logic, variables, or math; it only defines structural layout and elements.",
            category = "Web Technologies"
        ),
        FastConcept(
            id = "css",
            name = "CSS (Cascading Style Sheets)",
            romanUrduNames = listOf("css", "css kya hai", "css kya hota hai"),
            aliases = listOf("styling language", "web design"),
            definition = "CSS (Cascading Style Sheets) is a stylesheet language used to describe the visual presentation, styling, layout, and responsiveness of an HTML document.",
            simpleExplanation = "If HTML is the bare skeleton, CSS is the skin, clothes, makeup, and hair. It adds colors, stylish fonts, borders, and margins to make the website attractive.",
            detailedExplanation = "CSS rules consist of a selector and a declaration block (properties and values). Supports modern layout systems like Flexbox and CSS Grid, responsive design via Media Queries, and smooth animations. Styles cascade through specificity rules: inline styles > IDs > classes > element tags.",
            examples = "body { background-color: #0d1b2a; color: white; font-family: sans-serif; }",
            relatedConcepts = listOf("HTML", "JavaScript", "Responsive Design", "Flexbox"),
            importantPoints = listOf(
                "Separates presentation and styling from content structure.",
                "Enables websites to adapt automatically to mobile phones and large monitors.",
                "Supports transitions, gradients, animations, and typography control."
            ),
            commonConfusion = "CSS does not manipulate database records or handle backend logic; it strictly controls visual rendering in the user's browser.",
            category = "Web Technologies"
        ),
        FastConcept(
            id = "javascript",
            name = "JavaScript (JS)",
            romanUrduNames = listOf("javascript", "js kya hai", "javascript kya hai", "java scrpit"),
            aliases = listOf("JS", "client-side script", "web language"),
            definition = "JavaScript is a high-level, lightweight, interpreted or JIT-compiled programming language with first-class functions, famous as the scripting language for interactive web pages.",
            simpleExplanation = "If HTML is the skeleton and CSS is the clothing, JavaScript is the muscles and nervous system that makes the webpage move, react to button clicks, and animate.",
            detailedExplanation = "Standardized under ECMAScript. Executes client-side inside web browsers (using engines like V8 or SpiderMonkey) and server-side via Node.js. Supports event-driven, asynchronous programming using Promises and async/await for dynamic AJAX network calls and real-time DOM manipulation.",
            examples = "document.getElementById('quiz-btn').addEventListener('click', checkAnswer);",
            relatedConcepts = listOf("HTML", "CSS", "DOM", "Node.js", "TypeScript"),
            importantPoints = listOf(
                "Runs natively inside every modern web browser without plugins.",
                "Enables dynamic interactivity: dropdowns, form validation, popups, and live games.",
                "Completely different from Java; they are distinct languages with different goals."
            ),
            commonConfusion = "Java and JavaScript are two completely different languages! 'Java is to JavaScript as Car is to Carpet.'",
            category = "Languages & Web"
        ),
        FastConcept(
            id = "sql",
            name = "SQL (Structured Query Language)",
            romanUrduNames = listOf("sql", "sql kya hai", "sql kya hota hai", "structured query language"),
            aliases = listOf("database language", "sequel"),
            definition = "SQL (Structured Query Language) is a standardized domain-specific programming language designed for managing, querying, inserting, updating, and deleting data held in a relational database management system.",
            simpleExplanation = "SQL is the language you use to ask questions from a database. You tell it: 'SELECT all students WHERE marks are greater than 80', and it instantly gives you the list.",
            detailedExplanation = "Categorized into sublanguages: DDL (Data Definition Language: CREATE, ALTER, DROP), DML (Data Manipulation Language: SELECT, INSERT, UPDATE, DELETE), DCL (Data Control Language: GRANT, REVOKE), and TCL (Transaction Control Language: COMMIT, ROLLBACK). Supports powerful table JOINs, subqueries, indexing, and aggregation functions (COUNT, SUM, AVG).",
            examples = "SELECT name, marks FROM students WHERE class = 9 ORDER BY marks DESC;",
            relatedConcepts = listOf("Database", "Table", "Primary Key", "Relational Database"),
            importantPoints = listOf(
                "Declarative language: you describe WHAT data you want, not how to fetch it.",
                "Universal standard across MySQL, PostgreSQL, SQLite, SQL Server, and Oracle.",
                "Protects data integrity using relational constraints and transactions."
            ),
            commonConfusion = "SQL is not a general-purpose programming language for building apps; it is strictly a language for communicating with relational databases.",
            category = "Databases"
        ),
        FastConcept(
            id = "api",
            name = "API (Application Programming Interface)",
            romanUrduNames = listOf("api", "api kya hai", "api kya hota hai"),
            aliases = listOf("interface", "endpoint", "web service"),
            definition = "An API (Application Programming Interface) is a set of defined rules, protocols, and tools that allows different software applications to communicate and exchange data with each other.",
            simpleExplanation = "An API is like a waiter in a restaurant. You sit at your table and look at the menu, tell the waiter your order, the waiter carries your order to the kitchen (server), and brings the delicious food back to your table.",
            detailedExplanation = "Modern web applications communicate via RESTful APIs or GraphQL over HTTP/HTTPS, exchanging structured data typically in JSON format. Client apps issue GET, POST, PUT, DELETE requests to API endpoints, enabling modular separation between frontend user interfaces and backend cloud databases.",
            examples = "A weather app on your phone calling the OpenWeatherMap API to get today's temperature; an app calling the Google Gemini API to get an AI answer.",
            relatedConcepts = listOf("HTTP", "JSON", "REST", "Server", "Client"),
            importantPoints = listOf(
                "Enables integration between separate software systems without knowing internal source code.",
                "Restricts access and provides security keys and rate limiting.",
                "Standard web APIs return lightweight JSON responses."
            ),
            commonConfusion = "An API is not a user interface with buttons you click; it is a code-level bridge that computers use to talk to other computers.",
            category = "Web & Systems"
        ),
        FastConcept(
            id = "ai",
            name = "Artificial Intelligence (AI)",
            romanUrduNames = listOf("ai", "artificial intelligence kya hai", "ai kya hai", "ai kya hota hai"),
            aliases = listOf("machine intelligence", "smart systems"),
            definition = "Artificial Intelligence (AI) is the branch of computer science dedicated to developing computer systems capable of performing tasks that normally require human intelligence.",
            simpleExplanation = "AI is teaching computers to think, learn, recognize patterns, and make decisions, just like a human brain learns from practice and experience.",
            detailedExplanation = "AI encompasses Machine Learning, Deep Learning, Natural Language Processing (NLP), Computer Vision, and Robotics. Modern Generative AI leverages Large Language Models (LLMs) built on deep transformer neural network architectures trained on billions of parameters to understand context, generate code, and answer questions.",
            examples = "ZAKA AI Teacher, self-driving cars, facial recognition unlocking smartphones, chess computers beating world champions.",
            relatedConcepts = listOf("Machine Learning", "Neural Networks", "Deep Learning", "LLM", "Algorithm"),
            importantPoints = listOf(
                "Enables computers to learn from data rather than following rigid hardcoded rules.",
                "Categorized into Narrow AI (specialized in one task, like today's AI) and General AI (AGI, human-level across all tasks).",
                "Heavily relies on linear algebra, calculus, probability, and powerful GPUs."
            ),
            commonConfusion = "AI is not magic or conscious; it is sophisticated mathematical statistical pattern recognition running on high-speed hardware.",
            category = "AI & ML"
        ),
        FastConcept(
            id = "machine_learning",
            name = "Machine Learning (ML)",
            romanUrduNames = listOf("machine learning", "ml kya hai", "machine learning kya hai"),
            aliases = listOf("ML", "data learning", "model training"),
            definition = "Machine Learning (ML) is a core subfield of Artificial Intelligence focused on building algorithms that learn from data and improve their performance over time without being explicitly programmed.",
            simpleExplanation = "Instead of writing 10,000 rules to tell a computer what a cat looks like, you feed the computer 100,000 photos of cats and dogs. The computer studies the pictures and figures out the difference on its own.",
            detailedExplanation = "Three primary paradigms: (1) Supervised Learning (training on labeled input-output pairs; e.g. classification, regression), (2) Unsupervised Learning (finding hidden patterns in unlabeled data; e.g. clustering), and (3) Reinforcement Learning (agents learning optimal actions via trial-and-error reward signals).",
            examples = "Spam email filters learning to detect phishing messages, Netflix recommending movies based on your viewing history.",
            relatedConcepts = listOf("AI", "Deep Learning", "Neural Networks", "Algorithm", "Data"),
            importantPoints = listOf(
                "Trained on historical data to make predictions on unseen future data.",
                "Performance improves as quality and volume of training data increases.",
                "Subsumes Deep Learning and neural networks."
            ),
            commonConfusion = "Machine Learning is a subset of AI. All Machine Learning is AI, but not all AI is Machine Learning (some AI uses rule-based expert logic).",
            category = "AI & ML"
        )
    )

    fun findConcept(query: String): FastConcept? {
        val clean = query.trim().lowercase()
            .replace("?", "")
            .replace("!", "")
            .replace("what is", "")
            .replace("what are", "")
            .replace("explain", "")
            .replace("tell me about", "")
            .replace("kya hai", "")
            .replace("kya hota hai", "")
            .replace("kya hota ha", "")
            .replace("kia hai", "")
            .replace("kia ha", "")
            .replace("ka kam kya ha", "")
            .replace("asan lafzon ma samjhao", "")
            .replace("samjhao", "")
            .trim()

        // 1. Direct ID match
        concepts.firstOrNull { it.id.equals(clean, ignoreCase = true) }?.let { return it }

        // 2. Name or alias match
        concepts.firstOrNull { concept ->
            concept.name.lowercase().contains(clean) ||
            clean.contains(concept.id) ||
            concept.aliases.any { alias -> alias.equals(clean, ignoreCase = true) || clean.contains(alias) } ||
            concept.romanUrduNames.any { rn -> rn.equals(clean, ignoreCase = true) || clean.contains(rn) }
        }?.let { return it }

        // 3. Keyword matching within query tokens
        val tokens = clean.split(" ", "_", "-").filter { it.length >= 2 }
        for (token in tokens) {
            concepts.firstOrNull { it.id == token }?.let { return it }
            concepts.firstOrNull { it.name.lowercase().split(" ", "(", ")").contains(token) }?.let { return it }
        }

        return null
    }
}
