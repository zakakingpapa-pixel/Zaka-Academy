package com.example

import com.example.ai.router.QueryRouter
import com.example.data.course.CourseRepository
import com.example.data.fastconcept.FastConceptData
import com.example.model.QueryIntent
import org.junit.Assert.*
import org.junit.Test

class ZakaEngineUnitTest {

    @Test
    fun testCourseRepositoryHasAll8Chapters() {
        val chapters = CourseRepository.allChapters
        assertEquals(8, chapters.size)

        val allTopics = CourseRepository.getAllTopics()
        assertTrue(allTopics.size >= 8)

        // Verify chapter 1 content
        val ch1 = CourseRepository.getChapter(1)
        assertNotNull(ch1)
        assertEquals("Fundamentals of Computer & Systems", ch1?.title)

        // Verify chapter 8 content
        val ch8 = CourseRepository.getChapter(8)
        assertNotNull(ch8)
        assertEquals("Programming & Logic in Python", ch8?.title)
    }

    @Test
    fun testFastConceptOfflineDictionary() {
        val ramConcept = FastConceptData.findConcept("What is RAM?")
        assertNotNull(ramConcept)
        assertEquals("RAM (Random Access Memory)", ramConcept?.name)

        val cpuConcept = FastConceptData.findConcept("cpu kya hota ha")
        assertNotNull(cpuConcept)
        assertEquals("CPU (Central Processing Unit)", cpuConcept?.name)

        val binaryConcept = FastConceptData.findConcept("binary number")
        assertNotNull(binaryConcept)
        assertEquals("Binary Number System", binaryConcept?.name)
    }

    @Test
    fun testQueryRouterLanguageAndTypoCorrection() {
        // Typo correction
        val routed1 = QueryRouter.route("pyhton loop")
        assertEquals("python loop", routed1.correctedQuery)

        // Roman Urdu language detection
        val routed2 = QueryRouter.route("ram kya hota ha samjhao")
        assertEquals("Roman Urdu", routed2.detectedLanguage)
        assertNotNull(routed2.matchedFastConcept)

        // English comparison intent
        val routed3 = QueryRouter.route("RAM vs ROM")
        assertEquals("English", routed3.detectedLanguage)
        assertEquals(QueryIntent.COMPARISON, routed3.intent)

        // Follow up detection
        val routed4 = QueryRouter.route("ROM ka?", previousQuerySubject = "RAM")
        assertTrue(routed4.isFollowUp)
    }

    @Test
    fun testCourseSearch() {
        val results = CourseRepository.searchCourse("Algorithm")
        assertTrue(results.isNotEmpty())
        assertTrue(results.any { it.title.contains("Algorithm", ignoreCase = true) })
    }

    @Test
    fun testMcqsAndFlashcardsAvailability() {
        val allMcqs = CourseRepository.getAllMcqs()
        assertTrue(allMcqs.size >= 8)

        val allFlashcards = CourseRepository.getAllFlashcards()
        assertTrue(allFlashcards.size >= 8)
    }
}
