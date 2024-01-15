package com.example.news_app.db

import androidx.room.TypeConverter
import com.example.news_app.models.Source


// Type converters for Source to string and vice versa
class Converters {

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun toSource(name:String): Source {
        return Source(name, name)
    }

}