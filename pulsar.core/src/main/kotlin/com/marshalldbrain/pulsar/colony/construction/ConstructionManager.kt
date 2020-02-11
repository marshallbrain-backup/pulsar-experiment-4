package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.ion.collections.queueOf

class ConstructionManager() {
	
	//TODO except a ResourceRequester to request resources from a ResourceBucket
	//TODO invoke the onComplete function from any tasks that are completed
	
	val constructionQueue = queueOf<ConstructionTask>()
	
	fun addToQueue(task: ConstructionTask) {
		constructionQueue.add(task)
	}
	
	fun tick(amount: Int) {
		var remaining = amount
		while (remaining > 0 && !constructionQueue.isEmpty()) {
			remaining = constructionQueue.peek()!!.passTime(remaining)
			if (remaining >= 0) {
				constructionQueue.poll()
			}
		}
	}
	
}