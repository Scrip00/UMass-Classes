package com.scrip0.umassclasses.api.UMassAPI.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "classes_table")
data class Result(
    var _updated_at: String? = "",
    @ColumnInfo(defaultValue = "a")
    var description: String? = "",
    var details: Details? = Details(),
    var enrollment_information: EnrollmentInformation? = EnrollmentInformation(),
    @PrimaryKey
    var id: String,
    var number: String? = "",
    var offerings: List<Offering>? = emptyList(),
    var subject: Subject? = Subject(),
    var title: String? = "",
    var url: String? = ""
)