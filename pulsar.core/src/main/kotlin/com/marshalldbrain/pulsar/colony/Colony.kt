package com.marshalldbrain.pulsar.colony

import com.marshalldbrain.pulsar.colony.construction.ConstructionManager
import com.marshalldbrain.pulsar.colony.construction.ConstructionTask
import com.marshalldbrain.pulsar.colony.production.DistrictManager
import com.marshalldbrain.pulsar.colony.production.DistrictType
import com.marshalldbrain.pulsar.resources.ResourceIncome
import com.marshalldbrain.pulsar.resources.ResourceType

class Colony(allDistrictTypes: Set<DistrictType>) {
	
	private val resourceIncome = ResourceIncome()
	private val constructionManager = ConstructionManager()
	private val districtManager = DistrictManager(allDistrictTypes, resourceIncome)
	
	val income: Map<ResourceType, Int>
		get() = resourceIncome.income
	val districts: Map<DistrictType, Int>
		get() = districtManager.districts
	
	fun getConstructionQueue(): Collection<ConstructionTask> {
		return constructionManager.constructionQueue
	}
	
}
