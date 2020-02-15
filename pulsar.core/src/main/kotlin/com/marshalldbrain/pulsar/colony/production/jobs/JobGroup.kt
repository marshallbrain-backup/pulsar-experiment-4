package com.marshalldbrain.pulsar.colony.production.jobs

import com.marshalldbrain.pulsar.colony.PopType

class JobGroup(
	val jobType: JobType,
	val max: Int
) {
	val workers = mutableMapOf<PopType, Int>()
	
}