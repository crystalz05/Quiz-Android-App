package com.tyro.quizapplication.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tyro.quizapplication.data.entity.Question

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Question>)

    @Query("SELECT * FROM questions WHERE difficulty == :difficulty AND type == :type")
    suspend fun getByDifficultyAndType(difficulty: String, type: String): List<Question>

    @Query("DELETE FROM questions")
    suspend fun clearAll()

}