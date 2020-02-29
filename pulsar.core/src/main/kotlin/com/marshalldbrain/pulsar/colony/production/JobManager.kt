package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.resources.ResourceType
import com.marshalldbrain.pulsar.resources.ResourceUpdater

class JobManager(private val resourceUpdater: ResourceUpdater) : JobUpdater {
	
	private val mutableJobs = mutableMapOf<JobType, Int>()
	
	val jobs: Map<JobType, Int>
		get() = mutableJobs
	
	override fun update(change: Map<JobType, Int>, modifier: Int) {
		
		val resources = mutableMapOf<ResourceType, Int>()
		
		change.forEach { (k, v) ->
			mutableJobs[k] = mutableJobs[k]?:0 + v * 1
			resourceUpdater.update(k.production, v)
			resourceUpdater.update(k.upkeep, -v)
		}
		
	}
	
}