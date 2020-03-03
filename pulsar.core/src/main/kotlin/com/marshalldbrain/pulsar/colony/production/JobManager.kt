package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.ion.collections.getOrDefault
import com.marshalldbrain.pulsar.resources.ResourceType
import com.marshalldbrain.pulsar.resources.ResourceUpdater

class JobManager(private val resourceUpdater: ResourceUpdater) : JobUpdater {
	
	private val mutableJobs = mutableMapOf<JobType, Int>()
	
	val jobs: Map<JobType, Int>
		get() = mutableJobs
	
	override fun update(change: Map<JobType, Int>, modifier: Int) {
		
		change.forEach { (k, v) ->
			mutableJobs[k] = mutableJobs.getOrDefault(k, 0) + v * modifier
			resourceUpdater.update(k.production, v * modifier)
			resourceUpdater.update(k.upkeep, -v * modifier)
		}
		
	}
	
}