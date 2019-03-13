package com.example.pillarexcercise.homescreen

sealed class SearchHistoryListItem {
    data class City(val cityName: String): SearchHistoryListItem()
    data class ZipCode(val zipCode: String): SearchHistoryListItem()
    data class LatLong(val latitude: String, val longitude: String): SearchHistoryListItem()
}