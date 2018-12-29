package com.wladeq.ltracker.dto

class Tracks(
        var dateOfRide: String?,
        var coordinatesList: MutableList<Coordinates>? = mutableListOf()
)
