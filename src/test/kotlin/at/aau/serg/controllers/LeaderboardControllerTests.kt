package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.Mockito.`when` as whenever // when is a reserved keyword in Kotlin

class LeaderboardControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: LeaderboardController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = LeaderboardController(mockedService)
    }

    @Test
    fun test_getLeaderboard_correctScoreSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 15, 10.0)
        val third = GameResult(3, "third", 10, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third))

        val res: List<GameResult> = controller.getLeaderboard()

        verify(mockedService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(first, res[0])
        assertEquals(second, res[1])
        assertEquals(third, res[2])
    }

    @Test
    fun test_getLeaderboard_sameScore_CorrectIdSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third))

        val res: List<GameResult> = controller.getLeaderboard()

        verify(mockedService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(second, res[0])
        assertEquals(third, res[1])
        assertEquals(first, res[2])
    }

    @Test
    fun test_getLeaderboard_sameScore_correctTimeSorting() {
        val first = GameResult(1, "ABC", 20, 20.00)
        val second = GameResult(2, "DEF", 20, 10.00)
        val third = GameResult(3, "GHI", 20, 15.00)

        whenever(mockedService.getGameResults()).thenReturn(listOf(first, second, third))

        val res: List <GameResult> = controller.getLeaderboard()

        verify(mockedService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(second, res[0])
        assertEquals(third, res[1])
        assertEquals(first, res[2])
    }

    @Test
    fun test_getLeaderboard_sameTime_correctScoreSorting (){
        val first = GameResult(3, "abba", 15, 17.50)
        val second = GameResult(2, "abba", 30, 17.50)
        val third = GameResult(1, "abba", 35, 17.50)

        whenever(mockedService.getGameResults()).thenReturn(listOf(first, second, third))

        val res: List <GameResult> = controller.getLeaderboard()

        verify(mockedService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(third, res[0])
        assertEquals(second, res[1])
        assertEquals(first, res[2])
    }
}