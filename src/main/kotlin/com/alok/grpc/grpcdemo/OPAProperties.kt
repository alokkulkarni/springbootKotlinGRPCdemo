package com.alok.grpc.grpcdemo

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "opa")
data class OPAProperties(
    val url: String = "",
    val path: List<String> = emptyList(),
    val method: String = "",
)