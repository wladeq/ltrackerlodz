package com.wladeq.ltracker.dto

abstract class contextDTO {
    companion object {
        val dtoStorage = mutableSetOf<DTO>()
        inline fun <reified T : DTO> contextDTO(): T {
            return dtoStorage.firstOrNull { it.javaClass == T::class.java } as T?
                    ?: T::class.java.newInstance().apply {
                        dtoStorage.add(this)
                    }
        }
    }
}