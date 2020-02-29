package com.marshalldbrain.pulsar.resources

class ResourceBucket() : ResourceTeller {

	private val total = mutableMapOf<ResourceType, Int>()
	private val income = ResourceIncome()
	
	val bucket: Map<ResourceType, Pair<Int, Int>>
		get() = total.mapValues {
			Pair(it.value, income.income[it.key]?: 0)
		}
	
	override fun deposit(amount: Pair<ResourceType, Int>) {
		total[amount.first] = (total[amount.first]?: 0) + amount.second
	}
	
	override fun depositAll(amount: Map<ResourceType, Int>) {
		amount.forEach {
			total[it.key] = (total[it.key]?: 0) + it.value
		}
	}
	
	override fun withdraw(amount: Pair<ResourceType, Int>) {
		total[amount.first] = (total[amount.first]?: 0) - amount.second
	}
	
	override fun withdrawAll(amount: Map<ResourceType, Int>) {
		amount.forEach {
			total[it.key] = (total[it.key]?: 0) - it.value
		}
	}

}