package com.teamhide.serverdrivenui.aggregation

import org.springframework.stereotype.Service

@Service
class AggregationService(
    private val rowSettingRepository: RowSettingRepository,
) {
    suspend fun getBenefitTab() {
    }
}
