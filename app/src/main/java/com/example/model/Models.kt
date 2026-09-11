package com.example.model

/**
 * Question and Answer model for short and long questions.
 */
data class QuestionAnswer(
    val question: String,
    val answer: String,
    val keyPoints: List<String> = emptyList()
)

/**
 * Multiple Choice Question with instant feedback data.
 */
data class McqItem(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

/**
 * Interactive Flashcard item for revision and active recall.
 */
data class FlashcardItem(
    val id: String,
    val chapterId: Int,
    val topicTitle: String,
    val front: String,
    val back: String,
    val difficulty: String = "Medium" // Easy, Medium, Hard
)

/**
 * Complete 22-part textbook-grade Note structure for each Computer Science topic.
 */
data class TopicNote(
    val topicId: String,
    val chapterId: Int,
    val chapterTitle: String,
    val title: String,
    val definition: String,
    val easyExplanation: String,
    val detailedExplanation: String,
    val realWorldExample: String,
    val technicalExample: String,
    val importantPoints: List<String>,
    val keyTerms: List<Pair<String, String>>, // Term to definition
    val applications: List<String>,
    val advantages: List<String>,
    val disadvantages: List<String>,
    val typesCategories: List<String>,
    val comparisons: List<Pair<String, String>>, // e.g. RAM vs ROM or Analog vs Digital
    val commonMistakes: List<String>,
    val examFocusedPoints: List<String>,
    val shortQuestions: List<QuestionAnswer>,
    val longQuestions: List<QuestionAnswer>,
    val mcqs: List<McqItem>,
    val practiceQuestions: List<String>,
    val revisionSummary: String,
    val flashcards: List<FlashcardItem>,
    val relatedConcepts: List<String>,
    val visualDiagram: String = "" // Flowchart or visual ASCII layout
)

/**
 * Chapter containing multiple structured topic notes.
 */
data class ChapterItem(
    val id: Int,
    val number: Int,
    val title: String,
    val summary: String,
    val iconName: String,
    val topics: List<TopicNote>
)

/**
 * Fast Answer Knowledge Concept item for instant offline query resolution.
 */
data class FastConcept(
    val id: String,
    val name: String,
    val romanUrduNames: List<String>,
    val aliases: List<String>,
    val definition: String,
    val simpleExplanation: String,
    val detailedExplanation: String,
    val examples: String,
    val relatedConcepts: List<String>,
    val importantPoints: List<String>,
    val commonConfusion: String,
    val category: String
)

/**
 * AI Teaching Mode / Depth preference.
 */
enum class TeachingStyle(val label: String, val description: String) {
    SIMPLE("Simple & Easy", "Short, plain-language explanation with easy real-life examples"),
    BALANCED("Standard Academic", "Textbook depth, definitions, diagrams, and exam key points"),
    ADVANCED("Deep Technical", "In-depth architecture, internal mechanisms, and code logic"),
    DEBUGGING("Code & Debugging", "Step-by-step code error diagnosis, corrections, and best practices")
}

/**
 * AI Provider Selection.
 */
enum class AiProviderType(val displayName: String) {
    OFFLINE_EXPERT("Offline ZAKA AI Expert (Instant)"),
    GEMINI_CLOUD("Google Gemini AI (Cloud)"),
    OLLAMA_LAN("Local Ollama / LAN Model"),
    OPENAI_COMPATIBLE("OpenAI-Compatible Custom API")
}

/**
 * Query Router intent classification.
 */
enum class QueryIntent {
    DEFINITION,
    CONCEPT_EXPLANATION,
    COMPARISON,
    CODING_DEBUGGING,
    MATH_CONVERSION,
    COURSE_EXAM_QUESTION,
    QUIZ_PRACTICE,
    FOLLOW_UP,
    GENERAL_KNOWLEDGE,
    AMBIGUOUS
}

/**
 * Chat message for AI Teacher conversation.
 */
data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val providerUsed: String = "Offline Expert",
    val isFallback: Boolean = false,
    val detectedLanguage: String = "English",
    val intent: QueryIntent = QueryIntent.CONCEPT_EXPLANATION
)

enum class MessageSender {
    USER,
    AI,
    SYSTEM
}

/**
 * App Settings Configuration.
 */
data class AppSettings(
    val providerType: AiProviderType = AiProviderType.OFFLINE_EXPERT,
    val geminiApiKey: String = "",
    val ollamaEndpoint: String = "http://192.168.1.100:11434",
    val ollamaModel: String = "qwen2.5-coder:7b",
    val customApiEndpoint: String = "https://api.openai.com/v1",
    val customApiKey: String = "",
    val customModel: String = "gpt-4o-mini",
    val teachingStyle: TeachingStyle = TeachingStyle.BALANCED,
    val preferredLanguage: String = "Auto" // Auto, English, Roman Urdu, Urdu
)
