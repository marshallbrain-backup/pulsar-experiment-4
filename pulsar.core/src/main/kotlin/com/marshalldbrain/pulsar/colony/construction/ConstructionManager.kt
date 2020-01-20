package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.ion.collections.queueOf

class ConstructionManager {
	
	private val constructionQueue = queueOf<ConstructionTask>()
	
	fun addToQueue(task: ConstructionTask) {
		constructionQueue.add(task)
		if (constructionQueue.size == 1) {
			constructionQueue.peek()!!.onStart()
		}
	}
	
	fun getQueue(): Collection<ConstructionTask> {
		return constructionQueue
	}
	
	fun tick(amount: Int) {
		var remaining = amount
		while (remaining > 0 && constructionQueue.size > 0) {
			remaining -= constructionQueue.peek()!!.passTime(amount)
			constructionQueue.peek()!!.whileConstructing()
			if (remaining > 0) {
				constructionQueue.poll()!!.onFinnish()
				constructionQueue.peek()!!.onStart()
			}
		}
	}
	
}