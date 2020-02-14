package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.ion.sequences.randomIntRange
import com.marshalldbrain.pulsar.resources.Resource
import com.marshalldbrain.pulsar.resources.ResourceMaster
import com.marshalldbrain.pulsar.resources.ResourceType
import com.marshalldbrain.pulsar.resources.toSet
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import kotlin.random.Random

class ConstructionManagerTest : FunSpec({
	
	context("Process time") {
		
		test("Equal time passed") {
			
			val teller = ResourceMaster()
			val cm = ConstructionManager(teller)
			val task = TaskGen().random().first()
			
			cm.addToQueue(task)
			cm.tick(task.timeRemaining)
			
			cm.constructionQueue.shouldBeEmpty()
			
		}
		
		test("Time rolled over") {
			
			assertAll(RangedInt(min = 1, max = 10)) { a: Int ->
				
				val teller = ResourceMaster()
				val cm = ConstructionManager(teller)
				val tasks = TaskGen().random().take(a).toList()
				
				val totalTime = tasks.sumBy { it.timeRemaining }
				val time = Random.nextInt(0, totalTime+1)
				
				tasks.forEach { cm.addToQueue(it) }
				cm.tick(time)
				
				val actual = cm.constructionQueue.sumBy { it.timeRemaining } + time
				actual.shouldBe(totalTime)
				
			}
			
		}
		
		test("Triggers onComplete function") {
			
			val teller = ResourceMaster()
			val cm = ConstructionManager(teller)
			val task = Task("task", 10, emptySet())
			
			cm.addToQueue(task.build())
			cm.tick(task.time)
			
			task.built.shouldBeTrue()
			
		}
		
		test("Withdraws cost") {
			
			val teller = ResourceMaster()
			teller.deposit(setOf(Resource(ResourceType("test"), 10)))
			
			val cm = ConstructionManager(teller)
			val task = Task("task", 10, setOf(Resource(ResourceType("test"), 10)))
			
			cm.addToQueue(task.build())
			cm.tick(task.time)
			
			val expected = setOf(Resource(ResourceType("test"), 0))
			teller.bank.toSet().shouldContainExactly(expected)
			
		}
		
	}
	
})

private class RangedInt(val min: Int = 0, val max: Int) : Gen<Int> {
	override fun constants(): Iterable<Int> {
		return emptyList()
	}
	
	override fun random(): Sequence<Int> {
		return randomIntRange(min, max)
	}
	
}

private class TaskGen() : Gen<ConstructionTask> {

	override fun constants(): Iterable<ConstructionTask> {
		return emptyList()
	}

	override fun random(): Sequence<ConstructionTask> {
		return generateSequence {
			Task(
				"Task",
				Random.Default.nextInt(1, 13),
				setOf()
			).build(Random.Default.nextInt(1, 6))
		}
	}

}

private class Task (
	override val id: String,
	override val time: Int,
	override val cost: Set<Resource>
) : Constructable {
	
	var built: Boolean = false
	
	fun build(amount: Int = 1): ConstructionTask {
		
		return ConstructionTask(
			id,
			ConstructionType.BUILD,
			time,
			amount,
			cost,
			onComplete = { built = true }
		)
		
	}
	
	override fun createTask(type: ConstructionType, amount: Int): ConstructionTask {
		
		return when (type) {
			ConstructionType.BUILD -> build(amount)
		}
		
	}

}