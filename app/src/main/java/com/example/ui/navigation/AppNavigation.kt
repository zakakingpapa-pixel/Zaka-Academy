package com.example.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.ui.screens.*
import com.example.ui.viewmodel.ZakaViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Home", Icons.Default.Home)
    object Course : Screen("course", "Course", Icons.Default.MenuBook)
    object AiTeacher : Screen("ai_teacher?prompt={prompt}", "AI Teacher", Icons.Default.SmartToy)
    object Quiz : Screen("quiz/{chapterId}", "Quiz", Icons.Default.Quiz)
    object Flashcards : Screen("flashcards/{chapterId}", "Flashcards", Icons.Default.Style)
    object TopicDetail : Screen("topic/{topicId}", "Topic", Icons.Default.Book)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
    object QuestionTester : Screen("question_tester", "Tester", Icons.Default.EditNote)
    object Exam : Screen("exam", "Exams", Icons.Default.Assignment)
    object Progress : Screen("progress", "Progress", Icons.Default.Insights)
    object Notes : Screen("notes", "Notes", Icons.Default.Description)
    object ErrorSolver : Screen("error_solver", "Error Solver", Icons.Default.BugReport)
}

val bottomNavItems = listOf(
    Screen.Dashboard,
    Screen.Course,
    Screen.AiTeacher,
    Screen.Quiz,
    Screen.Flashcards
)

@Composable
fun ZakaAppNavigation(
    viewModel: ZakaViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Show bottom bar only on top-level screens
    val isTopLevelScreen = bottomNavItems.any { screen ->
        val baseRoute = screen.route.substringBefore("?").substringBefore("/{")
        currentDestination?.route?.startsWith(baseRoute) == true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isTopLevelScreen) {
                NavigationBar(
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    bottomNavItems.forEach { screen ->
                        val baseRoute = screen.route.substringBefore("?").substringBefore("/{")
                        val isSelected = currentDestination?.route?.startsWith(baseRoute) == true

                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = isSelected,
                            onClick = {
                                val targetRoute = when (screen) {
                                    is Screen.Quiz -> "quiz/1"
                                    is Screen.Flashcards -> "flashcards/1"
                                    is Screen.AiTeacher -> "ai_teacher"
                                    else -> screen.route
                                }
                                navController.navigate(targetRoute) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            modifier = Modifier.testTag("nav_item_${screen.title.lowercase()}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Dashboard
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToCourse = { navController.navigate(Screen.Course.route) },
                    onNavigateToTopic = { topicId ->
                        viewModel.selectTopic(topicId)
                        navController.navigate("topic/$topicId")
                    },
                    onNavigateToQuiz = { chId ->
                        viewModel.startChapterQuiz(chId)
                        navController.navigate("quiz/$chId")
                    },
                    onNavigateToFlashcards = { chId ->
                        viewModel.loadChapterFlashcards(chId)
                        navController.navigate("flashcards/$chId")
                    },
                    onNavigateToAiTeacher = { prompt ->
                        val route = if (!prompt.isNullOrBlank()) "ai_teacher?prompt=$prompt" else "ai_teacher"
                        navController.navigate(route)
                    },
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                    onNavigateToTester = { navController.navigate(Screen.QuestionTester.route) },
                    onNavigateToExam = { navController.navigate(Screen.Exam.route) },
                    onNavigateToProgress = { navController.navigate(Screen.Progress.route) },
                    onNavigateToNotes = { navController.navigate(Screen.Notes.route) },
                    onNavigateToErrorSolver = { navController.navigate(Screen.ErrorSolver.route) },
                    onStartDailyChallenge = { question ->
                        viewModel.startSingleQuestionTester(question)
                        navController.navigate(Screen.QuestionTester.route)
                    }
                )
            }

            // ZAKA Question Tester
            composable(Screen.QuestionTester.route) {
                QuestionTesterScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Chapter tests & mock exams
            composable(Screen.Exam.route) {
                ExamScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // My Progress
            composable(Screen.Progress.route) {
                ProgressScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onStartRevision = {
                        viewModel.startRevisionTester()
                        navController.navigate(Screen.QuestionTester.route)
                    },
                    onStartChapterTest = { chapterId ->
                        viewModel.startChapterTest(chapterId)
                        navController.navigate(Screen.Exam.route)
                    }
                )
            }

            // Notes hub & glossary
            composable(Screen.Notes.route) {
                NotesHubScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onOpenTopic = { topicId ->
                        viewModel.selectTopic(topicId)
                        navController.navigate("topic/$topicId")
                    }
                )
            }

            // Error Solver
            composable(Screen.ErrorSolver.route) {
                ErrorSolverScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onAskAi = { prompt -> navController.navigate("ai_teacher?prompt=$prompt") }
                )
            }

            // Course Catalog
            composable(Screen.Course.route) {
                CourseCatalogScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToTopic = { topicId ->
                        viewModel.selectTopic(topicId)
                        navController.navigate("topic/$topicId")
                    }
                )
            }

            // Topic Detail (22-part note)
            composable(
                route = Screen.TopicDetail.route,
                arguments = listOf(navArgument("topicId") { type = NavType.StringType })
            ) { backStackEntry ->
                val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
                val selectedTopic by viewModel.selectedTopic.collectAsState()
                TopicDetailScreen(
                    topic = selectedTopic,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onAskAiAboutTopic = { prompt ->
                        navController.navigate("ai_teacher?prompt=$prompt")
                    },
                    onTakeQuiz = { chId ->
                        viewModel.startChapterQuiz(chId)
                        navController.navigate("quiz/$chId")
                    }
                )
            }

            // Quiz Screen
            composable(
                route = Screen.Quiz.route,
                arguments = listOf(navArgument("chapterId") { type = NavType.IntType; defaultValue = 1 })
            ) { backStackEntry ->
                QuizScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Flashcards Screen
            composable(
                route = Screen.Flashcards.route,
                arguments = listOf(navArgument("chapterId") { type = NavType.IntType; defaultValue = 1 })
            ) { backStackEntry ->
                FlashcardScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // AI Teacher Chat Screen
            composable(
                route = Screen.AiTeacher.route,
                arguments = listOf(navArgument("prompt") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
            ) { backStackEntry ->
                val initialPrompt = backStackEntry.arguments?.getString("prompt")
                AiTeacherScreen(
                    initialPrompt = initialPrompt,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Settings Screen
            composable(Screen.Settings.route) {
                SettingsScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
