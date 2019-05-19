package com.apandey.clinical

data class ClinicalSpec(
    /** Default check settings */
    val defaultAppointments: Appointment = Appointment(),
    /** Number of parallel checker threads. default 1 */
    val checkerThreads: Int = 1,
    val healthOnMissedAppointments: Health = Health.MIBYOU
)


/**
 * Central location to hold all health checks and results
 */
class HealthRegistry {
    private val state = mutableMapOf<String,Diagnosis>()

    private val investigations = mutableListOf<Investigation>()

    fun addInvestigation( investigation: Investigation) {
        investigations.add(investigation)
    }
}
