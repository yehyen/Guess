package com.example.guess.data

// json資料複製後，在此自動生成data class
// command + n > Kotlin data classes from JSON
// 必須先裝plugins > JSON To Kotlin Class
class EventResult : ArrayList<Event>()

data class Event(
    val ID: Int,
    val Name: String,
    val StartDate: String,
    val EndDate: String,
    val Sponsor: String,
    val Season: Int,
    val Type: String,
    val Num: Int,
    val Venue: String,
    val City: String,
    val Country: String,
    val Discipline: String,
    val Main: Int,
    val Sex: String,
    val AgeGroup: String,
    val Url: String,
    val Related: String,
    val Stage: String,
    val ValueType: String,
    val ShortName: String,
    val WorldSnookerId: Int,
    val RankingType: String,
    val EventPredictionID: Int,
    val Team: Boolean,
    val Format: Int,
    val Twitter: String,
    val HashTag: String,
    val ConversionRate: Int,
    val AllRoundsAdded: Boolean,
    val PhotoURLs: String,
    val NumCompetitors: Int,
    val NumUpcoming: Int,
    val NumActive: Int,
    val NumResults: Int,
    val Note: String,
    val CommonNote: String,
    val DefendingChampion: Int,
    val PreviousEdition: Int
)