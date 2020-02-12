package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.ion.collections.queueOf
import com.marshalldbrain.pulsar.resources.ResourceTeller

class ConstructionManager(private val resourceTeller: ResourceTeller) {
	
	val constructionQueue = queueOf<ConstructionTask>()
	
	fun addToQueue(task: ConstructionTask) {
		constructionQueue.add(task)
		resourceTeller.withdraw(task.totalCost)
	}
	
	fun tick(amount: Int) {
		var remaining = amount
		while (remaining > 0 && !constructionQueue.isEmpty()) {
			remaining = constructionQueue.peek()!!.passTime(remaining)
			if (remaining >= 0) {
				constructionQueue.poll()!!.onComplete.invoke()
			}
		}
	}
	
}