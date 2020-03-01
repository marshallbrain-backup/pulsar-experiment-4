package com.marshalldbrain.pulsar.resources

import com.marshalldbrain.ion.collections.getOrDefault


class ResourceIncome(private val incomeUpdater: ResourceUpdater? = null) : ResourceUpdater {
	
	private val mutableIncome = mutableMapOf<ResourceType, Int>()
	
	val income: Map<ResourceType, Int>
		get() = mutableIncome
	
	override fun update(change: Map<ResourceType, Int>, modifier: Int) {
		change.forEach { (k, v) ->
			mutableIncome[k] = mutableIncome.getOrDefault(k, 0) + v * modifier
		}
		incomeUpdater?.update(change)
	}
	
}