package com.example.feature.lab

import kotlinx.coroutines.delay

/**
 * CODE EXECUTOR - سب Programming Languages میں Code Run کریں
 */

data class ExecutionResult(
    val output: String,
    val error: String = "",
    val executionTime: Long,
    val language: String
)

class CodeExecutor {

    suspend fun executePython(code: String): ExecutionResult {
        val startTime = System.currentTimeMillis()
        delay(1000) // Simulate execution
        
        // Python code execution simulation
        val output = when {
            code.contains("print") -> "Output: [Your Python Code Output]"
            code.contains("for") -> "Loop executed successfully"
            else -> "Python code executed"
        }
        
        return ExecutionResult(
            output = output,
            executionTime = System.currentTimeMillis() - startTime,
            language = "Python"
        )
    }

    suspend fun executeC(code: String): ExecutionResult {
        val startTime = System.currentTimeMillis()
        delay(1500) // Compilation + execution
        
        val output = when {
            code.contains("printf") -> "Output: [Your C Code Output]"
            code.contains("main") -> "C program compiled successfully"
            else -> "C code executed"
        }
        
        return ExecutionResult(
            output = output,
            executionTime = System.currentTimeMillis() - startTime,
            language = "C"
        )
    }

    suspend fun executeJavaScript(code: String): ExecutionResult {
        val startTime = System.currentTimeMillis()
        delay(800)
        
        val output = when {
            code.contains("console.log") -> "Output: [Your JavaScript Output]"
            code.contains("let") || code.contains("var") -> "Variables declared"
            else -> "JavaScript executed"
        }
        
        return ExecutionResult(
            output = output,
            executionTime = System.currentTimeMillis() - startTime,
            language = "JavaScript"
        )
    }

    suspend fun executeJava(code: String): ExecutionResult {
        val startTime = System.currentTimeMillis()
        delay(2000) // Compilation slower
        
        return ExecutionResult(
            output = "Java program compiled and executed successfully",
            executionTime = System.currentTimeMillis() - startTime,
            language = "Java"
        )
    }

    suspend fun executeBash(code: String): ExecutionResult {
        val startTime = System.currentTimeMillis()
        delay(1200)
        
        val output = when {
            code.contains("echo") -> "Output: [Shell command executed]"
            code.contains("ls") -> "Files listed"
            code.contains("pwd") -> "/home/user"
            else -> "Bash command executed"
        }
        
        return ExecutionResult(
            output = output,
            executionTime = System.currentTimeMillis() - startTime,
            language = "Bash"
        )
    }

    suspend fun validateSyntax(code: String, language: String): Pair<Boolean, String> {
        delay(500)
        
        val issues = mutableListOf<String>()
        
        when (language.lowercase()) {
            "python" -> {
                if (code.contains("def ") && !code.contains(":")) issues.add("Missing colon after function definition")
                if (code.contains("for ") && !code.contains(" in ")) issues.add("Invalid for loop syntax")
            }
            "c" -> {
                if (!code.contains("#include")) issues.add("Missing #include statement")
                if (!code.contains("main()")) issues.add("Missing main() function")
                if (code.contains(";") && code.split(";").size < 2) issues.add("Missing semicolons")
            }
            "javascript" -> {
                if (code.contains("function ") && !code.contains("{")) issues.add("Missing braces")
            }
        }
        
        return Pair(issues.isEmpty(), issues.joinToString("\n"))
    }
}
