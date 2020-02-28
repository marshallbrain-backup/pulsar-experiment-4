package com.marshalldbrain.pulsar.resources

class ResourceIncome {
	
	private val mutableIncome = mutableMapOf<ResourceType, Int>()
	
	val income: Map<ResourceType, Int>
		get() = mutableIncome
	
	fun pingChange(change: Map<ResourceType, Int>) {
		
		for (k in change.keys) {
			val a = mutableIncome[k]?: 0
			val b = change[k]?: 0
			if (a != 0 && b != 0) {
				mutableIncome[k] = a + b
			} else {
				mutableIncome[k] = b
			}
		}
		
	}
	
}