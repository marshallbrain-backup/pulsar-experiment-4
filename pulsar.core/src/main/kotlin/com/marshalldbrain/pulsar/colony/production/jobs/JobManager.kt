package com.marshalldbrain.pulsar.colony.production.jobs

class JobManager {
	
	val jobGroups = mutableMapOf<String, Collection<JobGroup>>()
	
	fun addAll(jobs: List<Workable>) {
		
		jobs.forEach {
			jobGroups[it.id] = it.jobGroups.values
		}
		
	}
	
}
