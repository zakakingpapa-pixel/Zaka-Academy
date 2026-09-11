package com.example.data.course

import com.example.model.*

object Chapter6Networks {
    val chapter = ChapterItem(
        id = 6,
        number = 6,
        title = "Computer Networks & The Internet",
        summary = "LAN, WAN, network topologies (Star, Bus, Ring, Mesh), OSI 7 layers, TCP/IP, IP addressing, DNS, and protocols.",
        iconName = "hub",
        topics = listOf(
            TopicNote(
                topicId = "ch6_top1",
                chapterId = 6,
                chapterTitle = "Computer Networks & The Internet",
                title = "Network Topologies, Architecture & Protocols",
                definition = "A computer network is an interconnected group of autonomous computing devices capable of exchanging data and sharing resources (hardware, software, storage) using standardized communication protocols.",
                easyExplanation = "Think of a computer network like a road network in a city. Individual computers are houses, network cables and Wi-Fi are the roads, routers are traffic signals that direct traffic, and data packets are delivery trucks carrying letters and parcels between houses.",
                detailedExplanation = "Networks are categorized by geographic scale: PAN (Personal Area Network, ~10m, Bluetooth), LAN (Local Area Network, single room/building/campus), MAN (Metropolitan Area Network, across an entire city), and WAN (Wide Area Network, global scale like the Internet).\nPhysical Topologies describe geometric device layout:\n1. Star Topology: Every device connects to a central hub/switch. If one cable fails, only that node goes down; if the central switch fails, the entire network drops.\n2. Bus Topology: All devices share a single linear backbone cable with terminators at ends. Inexpensive, but a break in the backbone halts all communication.\n3. Ring Topology: Devices form a closed token-ring loop. Data travels in one direction; a single break interrupts transmission unless dual rings exist.\n4. Mesh Topology: Every node connects directly to every other node (Full Mesh: n*(n-1)/2 links). Highest fault tolerance and redundancy, but costly to cable.\nNetwork Models:\nOSI 7 Layers: Physical, Data Link, Network, Transport, Session, Presentation, Application.\nTCP/IP 4 Layers: Network Access, Internet (IP), Transport (TCP/UDP), Application (HTTP, DNS, SMTP).",
                realWorldExample = "School Computer Lab: 30 student computers connect to a 48-port Ethernet switch in a Star Topology. All students print assignments to a single network laser printer and access the internet through a central fiber router.",
                technicalExample = "Sending a WhatsApp message over the internet: Text is packaged into an Application HTTP payload, encapsulated into a TCP segment (ensuring reliable delivery and packet ordering), wrapped into an IP packet with source and destination IP addresses, framed into Ethernet/Wi-Fi frames with MAC addresses, and modulated as radio waves.",
                importantPoints = listOf(
                    "Star topology is the most widely deployed topology in modern LAN environments.",
                    "TCP (Transmission Control Protocol) is connection-oriented and guarantees error-free packet delivery.",
                    "UDP (User Datagram Protocol) is connectionless and fast, favored for live video streaming and gaming.",
                    "Routers operate at the Network Layer (Layer 3) to route packets between disparate networks."
                ),
                keyTerms = listOf(
                    "LAN" to "Local Area Network; connects computers across a small geographic area like a building.",
                    "WAN" to "Wide Area Network; connects networks over vast geographic areas across countries and continents.",
                    "Topology" to "The physical or logical geometric arrangement of nodes and links in a network.",
                    "Router" to "An intelligent Layer 3 network device that routes data packets between different IP networks."
                ),
                applications = listOf(
                    "World Wide Web browsing and e-commerce transactions.",
                    "VoIP digital telephone calls and Zoom/Google Meet video conferencing.",
                    "Cloud storage synchronization (Google Drive, OneDrive).",
                    "Multiplayer online gaming servers."
                ),
                advantages = listOf(
                    "Resource Sharing: Multiple computers share expensive hardware like printers and NAS storage.",
                    "Cost Savings: Software licenses and centralized internet connections reduce overall costs.",
                    "Instant Communication: Rapid exchange of emails, instant messages, and files."
                ),
                disadvantages = listOf(
                    "Security Threats: Connected networks are vulnerable to hacking, ransomware, and virus propagation.",
                    "Centralized Dependence: Failure of core routers or switches can paralyze an entire organization."
                ),
                typesCategories = listOf(
                    "By Scope: PAN (Personal), LAN (Local), MAN (Metropolitan), WAN (Wide Area).",
                    "By Architecture: Client-Server Network vs Peer-to-Peer (P2P) Network.",
                    "By Topology: Star, Bus, Ring, Mesh, Hybrid."
                ),
                comparisons = listOf(
                    "LAN vs WAN" to "LAN covers a small area (room, school), high speed (1 Gbps+), low error rate, privately owned; WAN covers vast countries, lower bandwidth, higher error latency, uses public telecom lines (e.g. Internet).",
                    "TCP vs UDP" to "TCP is connection-oriented, reliable, retransmits lost packets (used in web, email); UDP is connectionless, fast, does not retransmit (used in live voice, video streaming, gaming)."
                ),
                commonMistakes = listOf(
                    "Confusing the Internet with the Web (WWW): Internet is the physical network infrastructure; the Web is an application service running on it using HTTP.",
                    "Believing Mesh topology is cheap: Mesh topology requires the largest number of cables and ports of all topologies."
                ),
                examFocusedPoints = listOf(
                    "Draw and label the 4 main network topologies: Star, Bus, Ring, and Mesh.",
                    "Write the comparison table between LAN and WAN with at least four distinct parameters.",
                    "List the 7 layers of the OSI Reference Model in correct order from Physical to Application."
                ),
                shortQuestions = listOf(
                    QuestionAnswer("What is a Local Area Network (LAN)?", "A LAN (Local Area Network) is a network that connects computers and devices within a limited geographic radius such as a single room, school lab, or office building."),
                    QuestionAnswer("What is the main advantage of Star topology?", "In Star topology, if one cable or device fails, only that single node is affected while all other computers in the network continue functioning normally."),
                    QuestionAnswer("Differentiate between TCP and UDP.", "TCP provides reliable, guaranteed, ordered delivery of packets with retransmission. UDP is faster, connectionless, and does not guarantee packet delivery (ideal for real-time video/gaming).")
                ),
                longQuestions = listOf(
                    QuestionAnswer(
                        "Explain four types of physical network topologies (Star, Bus, Ring, Mesh) with their advantages and disadvantages.",
                        "1. Star Topology: All nodes connect to a central switch. Advantage: Easy to install and troubleshoot; cable break affects only one node. Disadvantage: Failure of central switch collapses the entire network.\n\n2. Bus Topology: All nodes tap into a single linear backbone cable. Advantage: Inexpensive, uses minimal cabling. Disadvantage: Difficult to isolate faults; backbone cable break disconnects all nodes.\n\n3. Ring Topology: Nodes connect in a circular daisy-chain where data flows in one direction. Advantage: Equal access time using tokens. Disadvantage: Break in the loop halts transmission.\n\n4. Mesh Topology: Every node has dedicated point-to-point connections to every other node. Advantage: Maximum reliability and fault tolerance. Disadvantage: Extremely expensive and complex to wire."
                    )
                ),
                mcqs = listOf(
                    McqItem("mcq_ch6_1", "Which network topology connects all devices to a single central switch or hub?", listOf("Bus", "Star", "Ring", "Mesh"), 1, "Star topology connects all network nodes radially to a central switch."),
                    McqItem("mcq_ch6_2", "Which protocol guarantees reliable, ordered packet delivery over the Internet?", listOf("UDP", "IP", "TCP", "DNS"), 2, "TCP (Transmission Control Protocol) is connection-oriented and guarantees delivery."),
                    McqItem("mcq_ch6_3", "A network spanning an entire city is categorized as a:", listOf("LAN", "MAN", "PAN", "WAN"), 1, "MAN stands for Metropolitan Area Network, covering a city.")
                ),
                practiceQuestions = listOf(
                    "Calculate how many individual physical links are needed for a fully connected mesh network of 8 computers using formula n*(n-1)/2.",
                    "Explain the role of DNS in resolving 'www.google.com' into an IP address when typing it into a browser."
                ),
                revisionSummary = "Networks share data and resources. Topologies include Star, Bus, Ring, and Mesh. LAN covers local buildings; WAN covers the globe. TCP ensures reliable delivery, while UDP prioritizes speed. OSI model has 7 layers.",
                flashcards = listOf(
                    FlashcardItem("fc_ch6_1", 6, "Networks", "What is Star topology?", "A network layout where all individual devices connect directly to a central hub or switch."),
                    FlashcardItem("fc_ch6_2", 6, "Networks", "What is the difference between TCP and UDP?", "TCP is reliable and connection-oriented; UDP is fast and connectionless.")
                ),
                relatedConcepts = listOf("Network", "Internet", "IP Address", "DNS", "Router", "Topology"),
                visualDiagram = """
+-------------------------------------------------------+
|                 NETWORK TOPOLOGIES                    |
+-------------------------------------------------------+
|  [ STAR ]                [ BUS ]                      |
|    Node                   Node1   Node2   Node3       |
|      \                      |       |       |         |
|   Node--[SWITCH]--Node     === BACKBONE CABLE ===     |
|      /                      |       |       |         |
|    Node                   Node4   Node5   Node6       |
+-------------------------------------------------------+
|  [ RING ]                [ MESH ]                     |
|   Node1 ---> Node2         Node1 ----- Node2          |
|     ^          |             |  \     /  |            |
|     |          v             |    \ /    |            |
|   Node4 <--- Node3         Node3 ----- Node4          |
+-------------------------------------------------------+
                """.trimIndent()
            )
        )
    )
}
