package com.marshalldbrain.pulsar.colony.production.jobs

interface Workable {
	
	val id: String
	val jobGroups: Map<JobType, JobGroup>
	
}