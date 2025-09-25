package com.calyrsoft.ucbp1.features.dollar.domain.model

data class DollarModel(
    var dollarOfficial: String? = null,
    var dollarParallel: String? = null,
    var usdc: String? = null,
    var usdt: String? = null,
    var timestamp: Long = 0L
)