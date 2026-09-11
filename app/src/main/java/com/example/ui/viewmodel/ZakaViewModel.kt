package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.engine.ZakaAiEngine
import com.example.data.course.CourseRepository
import com.example.data.db.AppDatabase
import com.example.data.course.QuestionBank
import com.example.data.repository.ZakaRepository
import com.example.feature.errorsolver.ErrorAnalysis
import com.example.feature.errorsolver.ErrorSolver
import com.example.feature.exam.ExamEngine
import com.example.feature.revision.LearningSnapshot
import com.example.feature.revision.RevisionEngine
import com.example.feature.tester.AnswerEvaluator
import com.example.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ZakaViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = ZakaRepository(database, application)
    private val aiEngine = ZakaAiEngine(repository.loadSettings())

    // Settings State
    private val _settings = MutableStateFlow(repository.loadSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    // Topic Progress & Bookmarks
    val topicProgress = repository.getTopicProgress().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    // Quiz History
    val quizResults = repository.getQuizResults().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    // Flashcard Mastery
    val flashcardMastery = repository.getFlashcardMastery().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    // Chat History
    val chatHistory = repository.getChatHistory().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    // User Streak
    val userStreak = repository.getUserStreak().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), null
    )

    // Active AI State
    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    // Course Search State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<TopicNote>>(emptyList())
    val searchResults: StateFlow<List<TopicNote>> = _searchResults.asStateFlow()

    // Active Topic
    private val _selectedTopic = MutableStateFlow<TopicNote?>(CourseRepository.getAllTopics().firstOrNull())
    val selectedTopic: StateFlow<TopicNote?> = _selectedTopic.asStateFlow()

    // Active Quiz State
    private val _currentQuizQuestions = MutableStateFlow<List<McqItem>>(emptyList())
    val currentQuizQuestions: StateFlow<List<McqItem>> = _currentQuizQuestions.asStateFlow()

    private val _currentQuizIndex = MutableStateFlow(0)
    val currentQuizIndex: StateFlow<Int> = _currentQuizIndex.asStateFlow()

    private val _selectedOption = MutableStateFlow<Int?>(null)
    val selectedOption: StateFlow<Int?> = _selectedOption.asStateFlow()

    private val _isAnswerSubmitted = MutableStateFlow(false)
    val isAnswerSubmitted: StateFlow<Boolean> = _isAnswerSubmitted.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished: StateFlow<Boolean> = _isQuizFinished.asStateFlow()

    // Active Flashcard State
    private val _currentFlashcards = MutableStateFlow<List<FlashcardItem>>(CourseRepository.getAllFlashcards())
    val currentFlashcards: StateFlow<List<FlashcardItem>> = _currentFlashcards.asStateFlow()

    private val _flashcardIndex = MutableStateFlow(0)
    val flashcardIndex: StateFlow<Int> = _flashcardIndex.asStateFlow()

    private val _isCardFlipped = MutableStateFlow(false)
    val isCardFlipped: StateFlow<Boolean> = _isCardFlipped.asStateFlow()

    // Written practice history / exams / daily challenge
    val writtenAttempts = repository.getWrittenAttempts().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val examResults = repository.getExamResults().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val dailyChallenges = repository.getDailyChallenges().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    /** Real progress picture; chapters without attempts report no score rather than a guess. */
    val learningSnapshot: StateFlow<LearningSnapshot> =
        combine(topicProgress, quizResults, writtenAttempts) { topics, quizzes, written ->
            RevisionEngine.snapshot(topics, quizzes, written)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            RevisionEngine.snapshot(emptyList(), emptyList(), emptyList())
        )

    // Question Tester State
    private val _testerQuestions = MutableStateFlow<List<PracticeQuestion>>(emptyList())
    val testerQuestions: StateFlow<List<PracticeQuestion>> = _testerQuestions.asStateFlow()

    private val _testerIndex = MutableStateFlow(0)
    val testerIndex: StateFlow<Int> = _testerIndex.asStateFlow()

    private val _testerAnswer = MutableStateFlow("")
    val testerAnswer: StateFlow<String> = _testerAnswer.asStateFlow()

    private val _testerEvaluation = MutableStateFlow<AnswerEvaluation?>(null)
    val testerEvaluation: StateFlow<AnswerEvaluation?> = _testerEvaluation.asStateFlow()

    private val _testerHints = MutableStateFlow<List<String>>(emptyList())
    val testerHints: StateFlow<List<String>> = _testerHints.asStateFlow()

    private val _testerShowFullAnswer = MutableStateFlow(false)
    val testerShowFullAnswer: StateFlow<Boolean> = _testerShowFullAnswer.asStateFlow()

    private val _isEvaluating = MutableStateFlow(false)
    val isEvaluating: StateFlow<Boolean> = _isEvaluating.asStateFlow()

    // Exam State
    private val _examConfig = MutableStateFlow<ExamConfig?>(null)
    val examConfig: StateFlow<ExamConfig?> = _examConfig.asStateFlow()

    private val _examQuestions = MutableStateFlow<List<PracticeQuestion>>(emptyList())
    val examQuestions: StateFlow<List<PracticeQuestion>> = _examQuestions.asStateFlow()

    private val _examIndex = MutableStateFlow(0)
    val examIndex: StateFlow<Int> = _examIndex.asStateFlow()

    private val _examAnswers = MutableStateFlow<List<ExamAnswer>>(emptyList())
    val examAnswers: StateFlow<List<ExamAnswer>> = _examAnswers.asStateFlow()

    private val _examSummary = MutableStateFlow<ExamSummary?>(null)
    val examSummary: StateFlow<ExamSummary?> = _examSummary.asStateFlow()

    private val _examDraftAnswer = MutableStateFlow("")
    val examDraftAnswer: StateFlow<String> = _examDraftAnswer.asStateFlow()

    private val _examDraftOption = MutableStateFlow<Int?>(null)
    val examDraftOption: StateFlow<Int?> = _examDraftOption.asStateFlow()

    // Error Solver State
    private val _errorAnalysis = MutableStateFlow<ErrorAnalysis?>(null)
    val errorAnalysis: StateFlow<ErrorAnalysis?> = _errorAnalysis.asStateFlow()

    init {
        viewModelScope.launch {
            repository.recordActivityForStreak(repository.getUserStreak().first())
        }
    }

    // ---------------- Question Tester ----------------

    fun startQuestionTester(
        chapterId: Int?,
        types: List<QuestionType>,
        difficulties: List<QuestionDifficulty>,
        count: Int
    ) {
        val pool = QuestionBank.query(
            chapterIds = listOfNotNull(chapterId),
            types = types,
            difficulties = difficulties
        ).ifEmpty { QuestionBank.query(chapterIds = listOfNotNull(chapterId), types = types) }

        _testerQuestions.value = pool.shuffled().take(count)
        _testerIndex.value = 0
        resetTesterQuestionState()
    }

    fun startSingleQuestionTester(question: PracticeQuestion) {
        _testerQuestions.value = listOf(question)
        _testerIndex.value = 0
        resetTesterQuestionState()
    }

    fun startRevisionTester(count: Int = 10) {
        _testerQuestions.value = RevisionEngine
            .revisionQuestions(learningSnapshot.value, writtenAttempts.value, limit = count * 3)
            .filter { it.isWritten }
            .take(count)
        _testerIndex.value = 0
        resetTesterQuestionState()
    }

    fun updateTesterAnswer(text: String) {
        _testerAnswer.value = text
    }

    fun revealNextHint() {
        val question = currentTesterQuestion() ?: return
        val all = AnswerEvaluator.hints(question)
        val shown = _testerHints.value.size
        if (shown < all.size) {
            _testerHints.value = all.take(shown + 1)
        }
    }

    fun revealFullAnswer() {
        _testerShowFullAnswer.value = true
    }

    fun checkTesterAnswer() {
        val question = currentTesterQuestion() ?: return
        if (_isEvaluating.value || _testerEvaluation.value != null) return
        val answer = _testerAnswer.value

        viewModelScope.launch {
            _isEvaluating.value = true
            val evaluation = try {
                aiEngine.evaluateWrittenAnswer(question, answer)
            } catch (e: Exception) {
                AnswerEvaluator.evaluate(question, answer)
            }
            _testerEvaluation.value = evaluation
            _isEvaluating.value = false

            if (answer.isNotBlank()) {
                repository.saveWrittenAttempt(question, answer, evaluation, _testerHints.value.size)
            }
        }
    }

    fun nextTesterQuestion() {
        if (_testerIndex.value < _testerQuestions.value.lastIndex) {
            _testerIndex.value += 1
            resetTesterQuestionState()
        }
    }

    fun currentTesterQuestion(): PracticeQuestion? =
        _testerQuestions.value.getOrNull(_testerIndex.value)

    private fun resetTesterQuestionState() {
        _testerAnswer.value = ""
        _testerEvaluation.value = null
        _testerHints.value = emptyList()
        _testerShowFullAnswer.value = false
    }

    // ---------------- Chapter tests & mock exams ----------------

    fun startExam(config: ExamConfig) {
        _examConfig.value = config
        _examQuestions.value = ExamEngine.buildPaper(config, learningSnapshot.value.weakChapterIds)
        _examAnswers.value = emptyList()
        _examIndex.value = 0
        _examSummary.value = null
        _examDraftAnswer.value = ""
        _examDraftOption.value = null
    }

    fun startChapterTest(chapterId: Int) = startExam(ExamEngine.chapterTest(chapterId))

    fun startMockExam() = startExam(ExamEngine.fullMockExam())

    fun updateExamDraftAnswer(text: String) {
        _examDraftAnswer.value = text
    }

    fun selectExamOption(index: Int) {
        _examDraftOption.value = index
    }

    /** Records the current answer and advances; finishes and scores the paper on the last item. */
    fun submitExamAnswer() {
        val question = _examQuestions.value.getOrNull(_examIndex.value) ?: return
        val config = _examConfig.value ?: return
        if (_isEvaluating.value) return

        viewModelScope.launch {
            _isEvaluating.value = true
            val written = _examDraftAnswer.value
            val evaluation = if (!question.isWritten) {
                null
            } else if (written.isNotBlank()) {
                try {
                    aiEngine.evaluateWrittenAnswer(question, written)
                } catch (e: Exception) {
                    AnswerEvaluator.evaluate(question, written)
                }
            } else {
                AnswerEvaluator.evaluate(question, written)
            }

            _examAnswers.value = _examAnswers.value + ExamAnswer(
                question = question,
                selectedOptionIndex = _examDraftOption.value,
                writtenAnswer = written,
                evaluation = evaluation
            )
            _isEvaluating.value = false

            if (evaluation != null && written.isNotBlank()) {
                repository.saveWrittenAttempt(question, written, evaluation, 0)
            }

            if (_examIndex.value < _examQuestions.value.lastIndex) {
                _examIndex.value += 1
                _examDraftAnswer.value = ""
                _examDraftOption.value = null
            } else {
                val summary = ExamEngine.summarize(config, _examAnswers.value)
                _examSummary.value = summary
                repository.saveExamSummary(summary, config.chapterIds)
            }
        }
    }

    // ---------------- Daily challenge ----------------

    fun todayKey(): String =
        java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date())

    fun dailyChallengeQuestion(): PracticeQuestion? = RevisionEngine.dailyChallenge(todayKey())

    fun isDailyChallengeDone(): Boolean =
        dailyChallenges.value.any { it.date == todayKey() && it.isCompleted }

    fun recordDailyChallenge(question: PracticeQuestion, scorePercent: Int) {
        viewModelScope.launch {
            repository.saveDailyChallenge(todayKey(), question.id, scorePercent)
        }
    }

    // ---------------- Error Solver ----------------

    fun analyzeError(input: String) {
        _errorAnalysis.value = ErrorSolver.analyze(input)
    }

    fun clearErrorAnalysis() {
        _errorAnalysis.value = null
    }

    // Navigation & Topic Selection
    fun selectTopic(topicId: String) {
        val topic = repository.getTopic(topicId)
        if (topic != null) {
            _selectedTopic.value = topic
            viewModelScope.launch {
                repository.markTopicCompleted(topicId, topic.chapterId)
            }
        }
    }

    fun toggleBookmark(topicId: String, chapterId: Int, currentStatus: Boolean) {
        viewModelScope.launch {
            repository.toggleBookmark(topicId, chapterId, !currentStatus)
        }
    }

    // Search
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _searchResults.value = CourseRepository.searchCourse(query)
    }

    // Chat / AI Teacher
    fun sendChatMessage(userText: String) {
        if (userText.isBlank() || _isAiThinking.value) return

        val userMessage = ChatMessage(
            sender = MessageSender.USER,
            text = userText.trim(),
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            repository.saveChatMessage(userMessage)
            _isAiThinking.value = true

            try {
                val currentHistory = chatHistory.value
                val aiResponse = aiEngine.processQuery(userText, currentHistory)
                repository.saveChatMessage(aiResponse)
            } catch (e: Exception) {
                val errNotice = ChatMessage(
                    sender = MessageSender.AI,
                    text = "System notice: Switched to offline expert mode.",
                    providerUsed = "Offline Expert",
                    isFallback = true
                )
                repository.saveChatMessage(errNotice)
            } finally {
                _isAiThinking.value = false
            }
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            repository.clearChatHistory()
        }
    }

    // Quiz Controls
    fun startChapterQuiz(chapterId: Int) {
        val questions = repository.getMcqsForChapter(chapterId)
        _currentQuizQuestions.value = questions.ifEmpty { CourseRepository.getAllMcqs().take(5) }
        _currentQuizIndex.value = 0
        _selectedOption.value = null
        _isAnswerSubmitted.value = false
        _quizScore.value = 0
        _isQuizFinished.value = false
    }

    fun startCumulativeQuiz() {
        _currentQuizQuestions.value = repository.getAllMcqs().shuffled().take(10)
        _currentQuizIndex.value = 0
        _selectedOption.value = null
        _isAnswerSubmitted.value = false
        _quizScore.value = 0
        _isQuizFinished.value = false
    }

    fun selectQuizOption(optionIndex: Int) {
        if (!_isAnswerSubmitted.value) {
            _selectedOption.value = optionIndex
        }
    }

    fun submitQuizAnswer() {
        val selected = _selectedOption.value ?: return
        val currentQ = _currentQuizQuestions.value.getOrNull(_currentQuizIndex.value) ?: return

        _isAnswerSubmitted.value = true
        if (selected == currentQ.correctOptionIndex) {
            _quizScore.value += 1
        }
    }

    fun nextQuizQuestion() {
        val nextIdx = _currentQuizIndex.value + 1
        if (nextIdx < _currentQuizQuestions.value.size) {
            _currentQuizIndex.value = nextIdx
            _selectedOption.value = null
            _isAnswerSubmitted.value = false
        } else {
            _isQuizFinished.value = true
            // Persist quiz result
            viewModelScope.launch {
                val firstQ = _currentQuizQuestions.value.firstOrNull()
                val chapterId = firstQ?.let { CourseRepository.getAllTopics().firstOrNull { t -> t.mcqs.contains(it) }?.chapterId } ?: 1
                repository.saveQuizResult(
                    quizTitle = "Chapter $chapterId Practice Quiz",
                    chapterId = chapterId,
                    score = _quizScore.value,
                    total = _currentQuizQuestions.value.size
                )
            }
        }
    }

    // Flashcard Controls
    fun loadChapterFlashcards(chapterId: Int) {
        val cards = repository.getFlashcardsForChapter(chapterId)
        _currentFlashcards.value = cards.ifEmpty { CourseRepository.getAllFlashcards() }
        _flashcardIndex.value = 0
        _isCardFlipped.value = false
    }

    fun toggleCardFlip() {
        _isCardFlipped.value = !_isCardFlipped.value
    }

    fun nextFlashcard() {
        if (_currentFlashcards.value.isNotEmpty()) {
            _flashcardIndex.value = (_flashcardIndex.value + 1) % _currentFlashcards.value.size
            _isCardFlipped.value = false
        }
    }

    fun previousFlashcard() {
        if (_currentFlashcards.value.isNotEmpty()) {
            val prev = if (_flashcardIndex.value > 0) _flashcardIndex.value - 1 else _currentFlashcards.value.size - 1
            _flashcardIndex.value = prev
            _isCardFlipped.value = false
        }
    }

    fun markFlashcardMastery(isMastered: Boolean) {
        val currentCard = _currentFlashcards.value.getOrNull(_flashcardIndex.value) ?: return
        viewModelScope.launch {
            repository.setFlashcardMastery(currentCard.id, currentCard.chapterId, isMastered)
            nextFlashcard()
        }
    }

    // Settings Updates
    fun updateSettings(newSettings: AppSettings) {
        _settings.value = newSettings
        aiEngine.updateSettings(newSettings)
        repository.saveSettings(newSettings)
    }
}
