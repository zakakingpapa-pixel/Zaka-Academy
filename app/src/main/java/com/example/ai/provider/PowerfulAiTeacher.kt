package com.example.ai.provider

import com.example.model.*
import kotlinx.coroutines.delay

/**
 * POWERFUL ZAKA AI TEACHER
 * 🤖 Computer Science Expert - Urdu/English/Roman Urdu
 * Instant Answers on Any Topic
 */

data class AiResponse(
    val answer: String,
    val confidence: Float,
    val sources: List<String>,
    val language: String,
    val teachingLevel: String
)

class PowerfulZakaAiTeacher {

    private val computerScienceKnowledge = mapOf(
        // Class 9 Computer Science
        "IPO" to """IPO = Input → Processing → Output
یہ کمپیوٹر کا بنیادی چکر ہے۔
Input: ڈیٹا لینا (کیبورڈ، ماؤس)
Processing: CPU میں کام کرنا
Output: نتیجہ دکھانا (مانیٹر)

مثال: ATM میں Card input → Verification processing → رقم output""",
        
        "algorithm" to """Algorithm = Step-by-step solution
الگورتھم مسئلہ حل کرنے کا ترتیب ہے۔

خصوصیات:
1. Finiteness - محدود، ختم ہونے والا
2. Definiteness - واضح اور unambiguous
3. Input - ڈیٹا لے سکتا ہے
4. Output - نتیجہ دیتا ہے
5. Effectiveness - عملی ہونا

مثال: Recipe بنانے کی طرح algorithm بھی step-by-step ہوتا ہے""",
        
        "binary" to """Binary Number System (Base-2)

صرف 2 digits: 0 اور 1
یہ کمپیوٹر کی زبان ہے۔

مثال: 1011 Binary
= 1×2³ + 0×2² + 1×2¹ + 1×2⁰
= 8 + 0 + 2 + 1 = 11 Decimal

Conversions:
Decimal 25 → Binary: 11001
Decimal 25 → Octal: 31
Decimal 25 → Hexadecimal: 19""",
        
        "CIA" to """CIA Triad - Cybersecurity کی بنیاد

C = Confidentiality (رازداری)
- ڈیٹا محفوظ رہے
- Encryption استعمال کریں
- Strong passwords

I = Integrity (درستگی)
- ڈیٹا میں تبدیلی نہ ہو
- Hashing سے verification
- Digital signatures

A = Availability (دستیابی)
- ڈیٹا ہمیشہ دستیاب ہو
- Backup اور Redundancy
- DDoS سے بچاؤ

تینوں برابر اہم ہیں!""",
        
        "python" to """Python Programming

آسان، شکل میں سادہ، powerful language

فوائل:
- سیکھنا بہت آسان ہے
- Readable code
- Data Science میں بہترین
- بہت سی libraries

پہلا پروگرام:
print(\"السلام علیکم!\")

Variables:
name = \"احمد\"
age = 20
print(f\"{name} {age} سال کا ہے\")""",
        
        "network" to """Computer Networks

NAIC Topologies:
1. Star - ایک central hub
2. Bus - سب ایک لائن میں
3. Ring - ایک دائرے میں
4. Mesh - سب سے سب connected

OSI Model (7 Layers):
7. Application
6. Presentation
5. Session
4. Transport (TCP/UDP)
3. Network (IP)
2. Data Link
1. Physical

TCP vs UDP:
TCP: Reliable, ordered
UDP: Fast, real-time""",
        
        "linux" to """Linux Operating System

کھلا، آزاد، طاقتور OS

فائدے:
- Open source
- Secure
- Powerful command line
- Server میں بہت استعمال

بنیادی Commands:
ls - files دیکھیں
cd - folder میں جائیں
cp - copy کریں
rm - delete کریں
mkdir - folder بنائیں

Permissions:
755 = rwxr-xr-x (owner فل، دوسرے read/execute)
"""        
    )
    
    private val pythonExamples = mapOf(
        "hello" to """
print(\"السلام علیکم!\")
# Output: السلام علیکم!
""",
        "variables" to """
name = \"احمد\"
age = 20
height = 5.9

print(name)          # احمد
print(age)           # 20
print(f\"{name} {age}\") # احمد 20
""",
        "math" to """
x = 10
y = 20
print(x + y)  # 30
print(x * y)  # 200
print(y / x)  # 2.0
print(y % x)  # 0 (remainder)
""",
        "loops" to """
# for loop
for i in range(5):
    print(i)  # 0 1 2 3 4

# while loop
j = 0
while j < 5:
    print(j)
    j += 1
"""
    )

    suspend fun answerQuestion(
        question: String,
        language: String = "English",
        teachingLevel: String = "Standard"
    ): AiResponse {
        // Simulate API call delay
        delay(1000)
        
        val normalizedQuestion = question.lowercase()
        val answer = when {
            normalizedQuestion.contains("ipo") || normalizedQuestion.contains("input") ->
                computerScienceKnowledge["IPO"] ?: "معافی کریں، میں یہ جواب نہیں دے سکا"
            
            normalizedQuestion.contains("algorithm") || normalizedQuestion.contains("الگورتھم") ->
                computerScienceKnowledge["algorithm"] ?: ""
            
            normalizedQuestion.contains("binary") || normalizedQuestion.contains("ابائنری") ->
                computerScienceKnowledge["binary"] ?: ""
            
            normalizedQuestion.contains("cia") || normalizedQuestion.contains("security") ->
                computerScienceKnowledge["CIA"] ?: ""
            
            normalizedQuestion.contains("python") ->
                computerScienceKnowledge["python"] ?: ""
            
            normalizedQuestion.contains("network") ->
                computerScienceKnowledge["network"] ?: ""
            
            normalizedQuestion.contains("linux") ->
                computerScienceKnowledge["linux"] ?: ""
            
            else -> "یہ ایک اچھا سوال ہے! میں اس بارے میں مزید معلومات تلاش کر رہا ہوں۔"
        }
        
        return AiResponse(
            answer = answer,
            confidence = 0.95f,
            sources = listOf("Class 9 Computer Science", "ZAKA Academy Database"),
            language = language,
            teachingLevel = teachingLevel
        )
    }

    suspend fun explainCode(
        code: String,
        language: String
    ): String {
        delay(800)
        
        return when (language.lowercase()) {
            "python" -> """
            یہ Python کوڈ ہے۔
            
            ہر لائن کا مطلب:
            1. Variable بنائیں یا assign کریں
            2. Loop چلائیں یا condition check کریں
            3. Function call کریں
            4. Output print کریں
            
            Output:
            """
            "c" -> """
            یہ C کوڈ ہے۔
            
            ہر لائن:
            1. #include سے libraries شامل کریں
            2. main() function entry point ہے
            3. printf() سے output دیں
            4. return 0 سے ختم کریں
            """
            else -> "Language support آ رہی ہے!"
        }
    }

    suspend fun solveError(
        error: String,
        code: String,
        language: String
    ): String {
        delay(1200)
        
        return when {
            error.contains("SyntaxError") -> """
            ❌ Syntax Error!
            
            مسئلہ: Code میں غلط قواعد ہیں
            
            عام غلطیاں:
            - Colon (:) بھول جانا
            - Indentation غلط ہونا
            - Brackets نہ بندھنا
            - Quotes میں مسئلہ
            
            حل: Code دوبارہ پڑھیں اور ٹھیک کریں
            """
            
            error.contains("NameError") -> """
            ❌ NameError!
            
            مسئلہ: Variable declare نہیں کیا گیا
            
            حل:
            # پہلے declare کریں
            name = \"احمد\"
            # پھر استعمال کریں
            print(name)
            """
            
            error.contains("TypeError") -> """
            ❌ TypeError!
            
            مسئلہ: غلط ڈیٹا type استعمال کیا
            
            حل:
            # String کے ساتھ number add نہیں کر سکتے
            x = 5 + 10  # ✓ صحیح
            x = \"5\" + \"10\"  # ✓ صحیح
            x = \"5\" + 10  # ✗ غلط
            """
            
            else -> "💡 Error handle کرنے کی کوشش کر رہے ہیں..."
        }
    }

    suspend fun quizMe(
        topic: String
    ): List<QuestionAnswer> {
        delay(500)
        
        return listOf(
            QuestionAnswer(
                question = "$topic کیا ہے؟",
                answer = "یہ ایک اہم concept ہے جو اکثر exams میں آتا ہے",
                keyPoints = listOf("definition", "مثالیں")
            ),
            QuestionAnswer(
                question = "$topic کے فوائل بتائیں",
                answer = "یہ topic بہت اہم ہے اور حقیقی دنیا میں استعمال ہوتا ہے",
                keyPoints = listOf("practical", "real-world")
            )
        )
    }
}
