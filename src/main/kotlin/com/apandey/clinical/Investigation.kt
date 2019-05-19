package com.apandey.clinical

/**
 * Contract that defines specific health check implementations
 * Given a spec (@see Appointment), schedules tests and produces @see Diagnosis
 */
interface Investigation {
    fun spec(): Appointment
    fun test(): Diagnosis
}
