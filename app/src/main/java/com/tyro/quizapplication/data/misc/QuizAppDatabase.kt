package com.tyro.quizapplication.data.misc

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tyro.quizapplication.data.dao.QuestionDao
import com.tyro.quizapplication.data.entity.Question

@Database(entities = [Question::class], version = 1)
@TypeConverters(Converters::class)
abstract class QuizAppDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

}

