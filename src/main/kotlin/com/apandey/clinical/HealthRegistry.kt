package com.apandey.clinical


/**
 * Central location to hold all health check results
 */
class HealthRegistry {
    private val state = mutableMapOf<String,Diagnosis>()
}
