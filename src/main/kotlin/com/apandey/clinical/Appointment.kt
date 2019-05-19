package com.apandey.clinical

import java.time.Duration

data class Appointment(
    /** how often should we check for health. default 60 sec */
    val frequency: Duration = Duration.ofSeconds(60),
    /** how many missed checks constitute a failing health indicator. default 2 */
    val staleFactor: Int = 2
)