package com.teamhide.serverdrivenui.common.config.database

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

abstract class BaseTimestampEntity(
    @CreatedDate
    @Column("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column("updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
