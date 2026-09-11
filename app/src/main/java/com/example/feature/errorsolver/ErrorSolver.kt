package com.example.feature.errorsolver

data class ErrorAnalysis(
    val observedFacts: List<String>,
    val likelyCauses: List<String>,
    val suggestedFixes: List<String>,
    val needsExecution: List<String>,
    val analyzedBy: String
) {
    /** The app never runs the student's code, so every analysis says so plainly. */
    val executionNotice: String =
        "Static analysis only — ZAKA does not execute your code on the device, so runtime behaviour is not verified."
}

/**
 * Offline analyser for Python/Java-style error messages and snippets.
 *
 * It separates what is literally visible in the pasted text from what is only a likely cause, and
 * lists the checks that would need an actual run to confirm.
 */
object ErrorSolver {

    private data class Pattern(
        val marker: String,
        val cause: String,
        val fix: String
    )

    private val patterns = listOf(
        Pattern("indentationerror", "Block indentation is inconsistent with the surrounding code.", "Use the same indentation (4 spaces is standard) for every line inside a block, and never mix tabs with spaces."),
        Pattern("taberror", "Tabs and spaces are mixed inside one block.", "Convert all indentation in the file to spaces."),
        Pattern("syntaxerror", "The parser reached a token it cannot accept — often a missing ':', bracket or quote.", "Check the reported line and the line directly above it for an unclosed bracket, quote or missing colon."),
        Pattern("nameerror", "A name is used before it is defined or is misspelled.", "Define the variable/function before use and check the spelling and capitalisation."),
        Pattern("typeerror", "An operation received a value of the wrong type.", "Print the types involved and convert explicitly, e.g. int(input()) before arithmetic."),
        Pattern("valueerror", "A conversion received a value of the right type but an unusable content.", "Validate the input before converting, e.g. check str.isdigit() before int()."),
        Pattern("indexerror", "A list/string index is outside the valid range.", "Remember indexes run from 0 to len(x) - 1; guard loops with range(len(x))."),
        Pattern("keyerror", "A dictionary key that does not exist was requested.", "Use dict.get(key, default) or check `key in dict` first."),
        Pattern("zerodivisionerror", "A division used zero as the divisor.", "Check the divisor is non-zero before dividing."),
        Pattern("attributeerror", "A method/attribute was called on an object that does not have it.", "Check the object's actual type; a None value is the most common cause."),
        Pattern("modulenotfounderror", "The imported module is not installed or not on the import path.", "Install the package or correct the module name in the import statement."),
        Pattern("importerror", "The import target exists but the requested name inside it does not.", "Check the exact name being imported from the module."),
        Pattern("nullpointerexception", "A reference was used while it was null.", "Initialise the object, or guard the call with a null check."),
        Pattern("arrayindexoutofbounds", "An array index is outside the array length.", "Loop with i < array.length and check the index before access."),
        Pattern("recursionerror", "Recursion never reached its base case.", "Add or correct the base case so the recursion terminates.")
    )

    fun analyze(input: String): ErrorAnalysis {
        val text = input.trim()
        if (text.isEmpty()) {
            return ErrorAnalysis(
                observedFacts = emptyList(),
                likelyCauses = emptyList(),
                suggestedFixes = listOf("Paste the error message or the code that fails."),
                needsExecution = emptyList(),
                analyzedBy = "ZAKA Offline Error Solver"
            )
        }

        val lower = text.lowercase()
        val facts = mutableListOf<String>()
        val causes = mutableListOf<String>()
        val fixes = mutableListOf<String>()

        Regex("line\\s+(\\d+)", RegexOption.IGNORE_CASE).find(text)?.let {
            facts += "The message points at line ${it.groupValues[1]}."
        }
        Regex("([A-Za-z]+(?:Error|Exception))").find(text)?.let {
            facts += "Reported error type: ${it.groupValues[1]}."
        }
        Regex("File \"([^\"]+)\"").find(text)?.let {
            facts += "Reported file: ${it.groupValues[1]}."
        }
        facts += "Input length: ${text.length} characters, ${text.lines().size} line(s)."

        patterns.filter { lower.contains(it.marker) }.forEach {
            causes += it.cause
            fixes += it.fix
        }

        if (causes.isEmpty()) {
            val openParens = text.count { it == '(' } - text.count { it == ')' }
            val openBrackets = text.count { it == '[' } - text.count { it == ']' }
            val openBraces = text.count { it == '{' } - text.count { it == '}' }
            if (openParens != 0) {
                facts += "Unbalanced parentheses: ${if (openParens > 0) "$openParens unclosed '('" else "${-openParens} extra ')'"}."
                causes += "A bracket mismatch usually produces a syntax error on or after this line."
                fixes += "Balance the parentheses in the affected statement."
            }
            if (openBrackets != 0 || openBraces != 0) {
                facts += "Unbalanced square brackets or braces detected."
                causes += "Unclosed [ or { commonly causes the parser to fail at the next statement."
                fixes += "Close every [ and { that was opened."
            }
        }

        if (causes.isEmpty()) {
            causes += "No known error signature was found in the pasted text."
            fixes += "Paste the full traceback (including the last line) so the error type can be identified."
        }

        return ErrorAnalysis(
            observedFacts = facts,
            likelyCauses = causes.distinct(),
            suggestedFixes = fixes.distinct(),
            needsExecution = listOf(
                "Whether the fix removes the error can only be confirmed by running the program.",
                "Values held by variables at the moment of failure are not visible from this text."
            ),
            analyzedBy = "ZAKA Offline Error Solver"
        )
    }
}
