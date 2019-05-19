package com.apandey.clinical

import java.time.ZonedDateTime

/**
 * RAG status
 * <ul>
 * <li> HEALTHY = green. All is well
 * <li> MIBYOU = amber. Tread with caution, things may go red. see <a href="https://www.yomeishu.co.jp/english/mibyou/">mibyou</a>
 * <li> SICK = red. Things are not well
 * <li> UNDIAGNOSED = grey. I don't know what is going on
 * </ul>
 */
enum class Health {
    HEALTHY,
    MIBYOU,
    SICK,
    UNDIAGNOSED
}

/**
 * Result of a health check uniquely identified by <b>id</b>
 * returns the following:
 * <ul>
 *     <li> health - @see Health
 *     <li> summary - small explanation of current state
 *     <li> asOf - timestamp for when diagnosis was done
 *     <li> data - supplementary data as a Map
 * </ul>
 */
data class Diagnosis(
    val id: String,
    val health: Health,
    val summary: String,
    val asOf: ZonedDateTime,
    val data: Map<String,Any>
)
