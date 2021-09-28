package com.track.trackhabit.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Inspection(
    @PrimaryKey val time: Date,
    val check: Boolean)