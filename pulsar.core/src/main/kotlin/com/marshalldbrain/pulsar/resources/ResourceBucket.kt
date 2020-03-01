package com.marshalldbrain.pulsar.resources

import com.marshalldbrain.ion.collections.getOrDefault

class ResourceBucket() : ResourceTeller {

	private val total = mutableMapOf<ResourceType, Int>()
	private val income = ResourceIncome()
	
	override val incomeUpdater: ResourceUpdater
		get() = income
	val bucket: Map<ResourceType, Pair<Int, Int>>
		get() = total.mapValues { (k, v) ->
			Pair(v, income.income[k]?: 0)
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
	
	fun processIncome() {
		income.income.forEach { (k, v) ->
			total[k] = total.getOrDefault(k, 0) + v
		}
	}
	
}