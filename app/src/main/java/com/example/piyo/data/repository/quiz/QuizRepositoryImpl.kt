package com.example.piyo.data.repository.quiz

import com.example.piyo.data.mapper.quiz.QuizMapper
import com.example.piyo.data.remote.QuizContentService
import com.example.piyo.data.remote.QuizService
import com.example.piyo.domain.model.Quiz
import com.example.piyo.domain.model.QuizItem
import com.example.piyo.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizRepositoryImpl(
    private val contentService: QuizContentService,
    private val quizService: QuizService,
    private val mapper: QuizMapper
) : QuizRepository {

    override suspend fun getQuizByAgeSegment(ageSegment: String): Result<Quiz> {
        return try {
            val dto = contentService.getQuizByAgeSegment(ageSegment)
            if (dto != null) {
                Result.success(mapper.toDomain(dto))
            } else {
                Result.failure(Exception("Quiz untuk segmen $ageSegment tidak ditemukan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllQuizzes(): Flow<List<Quiz>> {
        return contentService.observeAllQuizzes().map { dtos ->
            dtos.map { mapper.toDomain(it) }
        }
    }

    override suspend fun observeQuizzes(): Flow<Result<List<QuizItem>>> {
        return quizService.observeQuizzes().map { dtos ->
            try {
                val quizItems = dtos.map { dto ->
                    QuizItem(
                        id = dto.id.orEmpty(),
                        title = dto.title.orEmpty(),
                        thumbnail = dto.thumbnail.orEmpty(),
                        description = dto.description.orEmpty(),
                        totalQuestions = dto.totalQuestions ?: 0,
                        duration = dto.duration ?: 0,
                        difficulty = dto.difficulty.orEmpty(),
                        tags = dto.tags ?: emptyList()
                    )
                }
                Result.success(quizItems)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun searchQuizzes(query: String): Result<List<QuizItem>> {
        return try {
            val dtos = quizService.searchQuizzes(query)
            val quizItems = dtos.map { dto ->
                QuizItem(
                    id = dto.id.orEmpty(),
                    title = dto.title.orEmpty(),
                    thumbnail = dto.thumbnail.orEmpty(),
                    description = dto.description.orEmpty(),
                    totalQuestions = dto.totalQuestions ?: 0,
                    duration = dto.duration ?: 0,
                    difficulty = dto.difficulty.orEmpty(),
                    tags = dto.tags ?: emptyList()
                )
            }
            Result.success(quizItems)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
