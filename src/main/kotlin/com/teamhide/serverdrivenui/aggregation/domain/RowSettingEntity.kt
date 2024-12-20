package com.teamhide.serverdrivenui.aggregation.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "row_setting")
data class RowSettingEntity(
    @Column("name")
    val name: String,

    @Column("row_type")
    val rowType: String,

    @Column("row_id")
    val rowId: String,

    @Column("seq")
    val seq: Int,

    @Column("enabled")
    val enabled: Boolean,

    @Column("created_at")
    val createdAt: LocalDateTime,

    @Column("updated_at")
    val updatedAt: LocalDateTime,

    @Id
    val id: Long = 0L,
)
