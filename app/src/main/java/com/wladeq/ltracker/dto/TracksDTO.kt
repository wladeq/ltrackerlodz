package com.wladeq.ltracker.dto

class Tracks(
        var instructorID: String? = "",
        var dateOfRide: String? = "",
        var coordinatesList: MutableList<Coordinates>? = mutableListOf()
)
