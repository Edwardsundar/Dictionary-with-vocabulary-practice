package com.demo.englishdectionary.data.local

import androidx.room.TypeConverter
import com.demo.englishdectionary.data.local.words800.SingleWordItem
import com.demo.englishdectionary.data.remoat.dto.License
import com.demo.englishdectionary.data.remoat.dto.Meaning
import com.demo.englishdectionary.data.remoat.dto.Phonetic
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LicenseConverter {
    @TypeConverter
    fun fromLicense(license: License): String {
        val gson = Gson()
        return gson.toJson(license)
    }

    @TypeConverter
    fun toLicense(licenseString: String): License {
        val gson = Gson()
        return gson.fromJson(licenseString, License::class.java)
    }
}

class PhoneticListConverter {
    @TypeConverter
    fun fromPhonetics(phonetics: List<Phonetic>): String {
        val gson = Gson()
        return gson.toJson(phonetics)
    }

    @TypeConverter
    fun toPhonetics(phoneticsString: String): List<Phonetic> {
        val gson = Gson()
        val type = object : TypeToken<List<Phonetic>>() {}.type
        return gson.fromJson(phoneticsString, type)
    }
}

class MeaningListConverter {
    @TypeConverter
    fun fromMeanings(meanings: List<Meaning>): String {
        val gson = Gson()
        return gson.toJson(meanings)
    }

    @TypeConverter
    fun toMeanings(meaningsString: String): List<Meaning> {
        val gson = Gson()
        val type = object : TypeToken<List<Meaning>>() {}.type
        return gson.fromJson(meaningsString, type)
    }
}

class StringListConverter {
    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun toStringList(stringListString: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(stringListString, type)
    }
}
class WordItemListConverter {
    @TypeConverter
    fun fromStringList(stringList: List<SingleWordItem>): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun toStringList(stringListString: String): List<SingleWordItem> {
        val gson = Gson()
        val type = object : TypeToken<List<SingleWordItem>>() {}.type
        return gson.fromJson(stringListString, type)
    }
}


