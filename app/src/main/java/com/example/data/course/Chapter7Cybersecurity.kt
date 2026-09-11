package com.example.data.course

import com.example.model.*

object Chapter7Cybersecurity {
    val chapter = ChapterItem(
        id = 7,
        number = 7,
        title = "Cybersecurity & Digital Ethics",
        summary = "CIA Triad, malware taxonomy (viruses, worms, trojans, ransomware), phishing attacks, authentication, and cryptography.",
        iconName = "security",
        topics = listOf(
            TopicNote(
                topicId = "ch7_top1",
                chapterId = 7,
                chapterTitle = "Cybersecurity & Digital Ethics",
                title = "Cybersecurity Principles, Malware Threats & Defensive Safeguards",
                definition = "Cybersecurity is the discipline and practice of defending computer systems, networks, programs, and digital data from digital attacks, theft, destruction, or unauthorized access.",
                easyExplanation = "Think of cybersecurity like securing a valuable museum. Confidentiality means locking precious jewels in display cases so only ticket-holders can view them. Integrity means ensuring nobody sneaks in to swap a real painting with a fake copy. Availability means making sure the museum doors open on time so visitors can enter.",
                detailedExplanation = "The core foundation of information security is the CIA Triad:\n1. Confidentiality: Ensuring sensitive information is shielded from unauthorized inspection or interception (achieved via AES encryption, passwords, multi-factor authentication).\n2. Integrity: Safeguarding the accuracy, consistency, and trustworthiness of data over its entire lifecycle, ensuring data is not modified, tampered with, or corrupted in transit (verified via cryptographic hash algorithms like SHA-256 and digital signatures).\n3. Availability: Ensuring authorized users have uninterrupted access to critical systems, networks, and data when needed (defended via redundant server failovers, UPS backups, and DDoS mitigation).\nMalware (Malicious Software) categories include:\n- Virus: Replicating malicious code that attaches itself to legitimate host files (.exe, .docx) and spreads upon execution.\n- Worm: Standalone malware that self-propagates across computer networks automatically without requiring human intervention or host files.\n- Trojan Horse: Deceptive software disguised as harmless or useful (like a game or tool) that covertly opens backdoors for hackers.\n- Ransomware: Encrypts the victim's critical files and demands cryptocurrency ransom payment for the decryption key.",
                realWorldExample = "Phishing attack email: An attacker sends an urgent email pretending to be from your bank claiming 'Your account is suspended, click here to verify password'. Clicking the link loads a fake replica banking login page designed to steal your credentials.",
                technicalExample = "Cryptographic hashing for password storage: A secure system never stores plain-text passwords. When a user creates password `P@ssword123`, the server computes SHA-256 with a unique salt: `sha256('salt' + 'P@ssword123') = 8f4...b2`. During login, the hash is recomputed and compared.",
                importantPoints = listOf(
                    "CIA Triad stands for Confidentiality, Integrity, and Availability.",
                    "Worms do NOT need a host file; Viruses require attachment to an executable host file.",
                    "Two-Factor Authentication (2FA) adds a critical second verification layer (e.g. SMS code, authenticator app).",
                    "HTTPS encrypts web traffic between browser and server using SSL/TLS encryption."
                ),
                keyTerms = listOf(
                    "Malware" to "Short for Malicious Software; any software intentionally created to cause damage to a computer, server, or network.",
                    "Phishing" to "A social engineering cyberattack where attackers impersonate trustworthy entities to deceive users into revealing credentials.",
                    "Encryption" to "The mathematical process of converting readable plaintext into unreadable ciphertext using an encryption key.",
                    "Firewall" to "A network security device that monitors and filters incoming and outgoing network traffic based on security rules."
                ),
                applications = listOf(
                    "Online banking systems and credit card payment gateways.",
                    "Healthcare medical record privacy compliance (HIPAA).",
                    "Government defense communications and national infrastructure protection.",
                    "Cloud identity management and Single Sign-On (SSO)."
                ),
                advantages = listOf(
                    "Safeguards personal privacy and prevents identity theft.",
                    "Protects business reputations and prevents catastrophic financial loss from ransomware.",
                    "Ensures compliance with international digital data protection laws."
                ),
                disadvantages = listOf(
                    "Security measures can introduce friction (longer login steps, password complexity rules).",
                    "Implementing enterprise-grade security infrastructure requires substantial capital investment."
                ),
                typesCategories = listOf(
                    "Threats: Viruses, Worms, Trojans, Ransomware, Spyware, Adware, Phishing, Denial-of-Service (DoS).",
                    "Defenses: Firewalls, Antivirus Software, Strong Passwords, 2FA/MFA, Regular Backups, Encryption."
                ),
                comparisons = listOf(
                    "Virus vs Worm" to "A Virus requires an executable host file and human action (launching the infected program) to spread; a Worm is self-contained and spreads automatically across networks without human intervention.",
                    "Symmetric vs Asymmetric Encryption" to "Symmetric uses one identical secret key for both encryption and decryption (fast, e.g. AES); Asymmetric uses a mathematically linked public-private key pair (secure key exchange, e.g. RSA)."
                ),
                commonMistakes = listOf(
                    "Using the same simple password across multiple websites.",
                    "Believing that having an antivirus makes you 100% immune to phishing scams (social engineering tricks the human, not the software)."
                ),
                examFocusedPoints = listOf(
                    "Explain each component of the CIA Triad (Confidentiality, Integrity, Availability) with illustrative examples.",
                    "Differentiate between a computer virus and a computer worm.",
                    "List five best practices for maintaining digital hygiene and password security."
                ),
                shortQuestions = listOf(
                    QuestionAnswer("What does the CIA Triad stand for in cybersecurity?", "The CIA Triad stands for Confidentiality, Integrity, and Availability. It forms the foundational model for information security policies."),
                    QuestionAnswer("How does a computer worm differ from a virus?", "A virus requires a host program and user action to spread, whereas a worm is a standalone program that automatically replicates and travels across networks independently."),
                    QuestionAnswer("What is Phishing?", "Phishing is a fraudulent social engineering attempt to steal sensitive user information (like passwords and credit card numbers) by disguising as a trustworthy organization in digital communications.")
                ),
                longQuestions = listOf(
                    QuestionAnswer(
                        "Define malware and explain four common types of malware threats along with practical defensive safeguards.",
                        "Malware (Malicious Software) is any program designed to infiltrate, damage, or compromise computer systems.\n\nFour Common Types:\n1. Virus: Infects legitimate program files and replicates when the infected host is executed.\n2. Worm: Standalone malware that exploits network vulnerabilities to self-replicate rapidly across computers without human intervention.\n3. Trojan Horse: Disguises itself as legitimate software (like a utility tool) but installs malicious backdoors.\n4. Ransomware: Encrypts files on a victim's storage drive and demands financial payment for decryption.\n\nDefensive Safeguards:\n- Install and regularly update certified Antivirus software.\n- Enable a network firewall to block unauthorized incoming ports.\n- Practice digital hygiene: do not open suspicious email attachments or pirated software.\n- Use Multi-Factor Authentication (MFA) and maintain offline backups."
                    )
                ),
                mcqs = listOf(
                    McqItem("mcq_ch7_1", "Which component of the CIA Triad guarantees that data has not been modified or tampered with?", listOf("Confidentiality", "Integrity", "Availability", "Authentication"), 1, "Integrity ensures data remains accurate, complete, and untampered."),
                    McqItem("mcq_ch7_2", "Malware that self-replicates across networks without requiring a host file is called a:", listOf("Virus", "Worm", "Trojan Horse", "Spyware"), 1, "A computer worm propagates across networks autonomously without host attachment."),
                    McqItem("mcq_ch7_3", "Fraudulent communication disguised as a reputable company to steal passwords is known as:", listOf("Spamming", "Phishing", "DDoS", "Cracking"), 1, "Phishing is a deceptive social engineering attack designed to harvest credentials.")
                ),
                practiceQuestions = listOf(
                    "Explain how Two-Factor Authentication (2FA) protects an account even if the attacker knows the password.",
                    "Describe the process of ransomware extortion and why maintaining an offline 3-2-1 backup strategy is the best defense."
                ),
                revisionSummary = "Cybersecurity rests on the CIA Triad: Confidentiality, Integrity, and Availability. Malware includes viruses, worms, trojans, and ransomware. Defensive safeguards include firewalls, strong passwords, 2FA, and updated antivirus.",
                flashcards = listOf(
                    FlashcardItem("fc_ch7_1", 7, "Cybersecurity", "What are the three pillars of the CIA Triad?", "Confidentiality, Integrity, and Availability."),
                    FlashcardItem("fc_ch7_2", 7, "Cybersecurity", "What is Ransomware?", "Malware that encrypts your files and demands ransom money to restore access.")
                ),
                relatedConcepts = listOf("Malware", "Phishing", "Encryption", "Firewall", "Authentication"),
                visualDiagram = """
+-------------------------------------------------------+
|                   THE CIA SECURITY TRIAD              |
+-------------------------------------------------------+
|                    [ CONFIDENTIALITY ]                |
|                    (Encryption, Access)               |
|                           /    \                      |
|                          /      \                     |
|                         /        \                    |
|                        /          \                   |
|               [ INTEGRITY ] ---- [ AVAILABILITY ]     |
|             (Hashes, Signatures)  (Redundancy, Backups)|
+-------------------------------------------------------+
                """.trimIndent()
            )
        )
    )
}
