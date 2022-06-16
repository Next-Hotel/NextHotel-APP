package com.nexthotel.core.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "survey")
@Parcelize
data class SurveyEntity(
    @PrimaryKey val id: Int,
    var hasSurvey: Boolean,
    var listSurvey: List<String>,
) : Parcelable