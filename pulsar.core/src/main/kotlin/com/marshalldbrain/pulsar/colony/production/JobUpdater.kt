package com.marshalldbrain.pulsar.colony.production

interface JobUpdater {
	
	fun update(change: Map<JobType, Int>, modifier: Int = 1)
	
}