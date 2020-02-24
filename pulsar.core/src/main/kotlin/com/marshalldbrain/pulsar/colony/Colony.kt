package com.marshalldbrain.pulsar.colony

import com.marshalldbrain.pulsar.colony.construction.ConstructionManager
import com.marshalldbrain.pulsar.colony.production.DistrictManager
import com.marshalldbrain.pulsar.colony.production.DistrictType

class Colony(allDistrictTypes: Set<DistrictType>) {
	
	val constructionManager = ConstructionManager()
	
	val districts = DistrictManager(allDistrictTypes)
	
	private fun min(a: IntRange, b: IntRange): IntRange {
		return if (a.last > b.last) b else a
	}
	
}
