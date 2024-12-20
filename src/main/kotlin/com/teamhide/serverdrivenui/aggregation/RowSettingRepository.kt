package com.teamhide.serverdrivenui.aggregation

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface RowSettingRepository : CoroutineCrudRepository<RowSettingEntity, Long>
