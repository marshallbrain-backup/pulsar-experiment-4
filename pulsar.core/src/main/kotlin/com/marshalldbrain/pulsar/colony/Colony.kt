package com.marshalldbrain.pulsar.colony

import com.marshalldbrain.pulsar.colony.construction.ConstructionInfo
import com.marshalldbrain.pulsar.colony.construction.ConstructionManager
import com.marshalldbrain.pulsar.colony.construction.ConstructionTask
import com.marshalldbrain.pulsar.colony.production.DistrictInfo
import com.marshalldbrain.pulsar.colony.production.DistrictManager
import com.marshalldbrain.pulsar.colony.production.DistrictType
import com.marshalldbrain.pulsar.colony.production.JobManager
import com.marshalldbrain.pulsar.resources.ResourceIncome
import com.marshalldbrain.pulsar.resources.ResourceTeller
import com.marshalldbrain.pulsar.resources.ResourceType

class Colony(allDistrictTypes: Set<DistrictType>, teller: ResourceTeller) {
	
	private val resourceIncome = ResourceIncome(teller.incomeUpdater)
	private val constructionManager = ConstructionManager(teller)
	private val jobManager = JobManager(resourceIncome)
	private val districtManager = DistrictManager(allDistrictTypes, resourceIncome, jobManager)
	
	val income: Map<ResourceType, Int>
		get() = resourceIncome.income
	val districtInfo: DistrictInfo
		get() = districtManager
	val constructionInfo: ConstructionInfo
		get() = constructionManager
	
	fun tick(amount: Int) {
		constructionManager.tick(amount)
	}
	
	fun addToConstructionQueue(task: ConstructionTask) {
		constructionManager.addToQueue(task)
	}
	
}
