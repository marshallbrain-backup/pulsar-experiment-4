package com.marshalldbrain.pulsar.resources

class ResourceMaster(vararg sources: ResourceCollection) : ResourceTeller {
	
	private val sourceList = sources.asList()
	private val bucket = mutableMapOf<ResourceType, Resource>()
	
	val bank: Set<Resource>
		get() = bucket.values.toSet()
	
	fun collectResources() {
		sourceList.forEach {
			bucket += it.income
		}
	}
	
	override fun deposit(resources: Set<Resource>) {
		bucket += resources
	}
	
	override fun withdraw(resources: Set<Resource>) {
		bucket -= resources
	}
	
}