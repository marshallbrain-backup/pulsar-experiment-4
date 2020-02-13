package com.marshalldbrain.pulsar.resources

class ResourceMaster() : ResourceTeller {
	
	val sourceList = mutableListOf<ResourceCollection>()
	private val bucket = mutableMapOf<ResourceType, Resource>()
	
	val bank: Map<ResourceType, Pair<Resource, Resource>>
		get() {
			
			val totalIncome = mutableMapOf<ResourceType, Resource>()
			sourceList.forEach {
				totalIncome += it.income
			}
			
			return bucket.mapValues {
				Pair(it.value, totalIncome[it.key] ?: Resource(it.key, 0))
			}
			
		}
	
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