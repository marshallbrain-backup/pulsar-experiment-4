package com.marshalldbrain.pulsar.colony.production

import com.marshalldbrain.pulsar.colony.construction.ConstructionManager
import com.marshalldbrain.pulsar.colony.construction.ConstructionType
import com.marshalldbrain.pulsar.resources.ResourceIncome
import com.marshalldbrain.pulsar.resources.ResourceType
import io.kotlintest.fail
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.maps.shouldContainExactly
import io.kotlintest.matchers.maps.shouldContainKey
import io.kotlintest.matchers.maps.shouldNotContainKey
import io.kotlintest.specs.FunSpec
import io.kotlintest.shouldBe
import io.mockk.mockk

class DistrictManagerTest : FunSpec({
	
	test("Possible district types are correct") {
		
		val districts = setOf(
			DistrictType("1", possible = false),
			DistrictType("2"),
			DistrictType("3"),
			DistrictType("4", possible = false)
		)
		
		val expected = setOf(
			DistrictType("2"),
			DistrictType("3")
		)
		
		val dm = DistrictManager(districts, mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
		
		dm.possibleTypes.shouldContainExactly(expected)
	
	}
	
	test("Starting district types are correct") {
		
		val districts = setOf(
			DistrictType("1", starting = true),
			DistrictType("2"),
			DistrictType("3", starting = true),
			DistrictType("4")
		)
		
		val expected = setOf(
			DistrictType("1", starting = true),
			DistrictType("3", starting = true)
		)
		
		val dm = DistrictManager(districts, mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
		
		dm.districts.keys.shouldContainExactly(expected)
		
	}
	
	context("Create task") {
		
		val type1 = DistrictType(
			"1", starting = true,
			production = mapOf(ResourceType("1") to 4),
			upkeep = mapOf(ResourceType("1") to 1),
			jobs = mapOf(JobType("1") to 1)
		)
		
		val type2 = DistrictType(
			"2", starting = true,
			production = mapOf(ResourceType("2") to 4),
			upkeep = mapOf(ResourceType("2") to 1),
			jobs = mapOf(JobType("2") to 1)
		)
		
		test("Multiple amount") {
			
			val dm = DistrictManager(setOf(type1), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
			val cm = ConstructionManager(mockk(relaxUnitFun = true))
			
			val task = dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 4)
			cm.addToQueue(task)
			cm.tick(20)
			
			dm.districts[type1] shouldBe 4
			cm.active.shouldBeEmpty()
			
		}
		
		context("Build task") {
			
			test("Amount updates") {
				
				val dm = DistrictManager(setOf(type1), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
				
				dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 1).runOnComplete()
				
				dm.districts[type1] shouldBe 1
			
			}
			
			test("Income updates") {
				
				val income = ResourceIncome()
				val dm = DistrictManager(setOf(type1), income, mockk(relaxUnitFun = true))
				val expected = mapOf(ResourceType("1") to 3)
				
				dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 1).runOnComplete()
				
				income.income.shouldContainExactly(expected)
				
			}
			
			test("Jobs updates") {
				
				val jobs = JobManager(mockk(relaxUnitFun = true))
				val dm = DistrictManager(setOf(type1), mockk(relaxUnitFun = true), jobs)
				val expected = mapOf(JobType("1") to 1)
				
				dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 1).runOnComplete()
				
				jobs.jobs.shouldContainExactly(expected)
				
			}
			
		}
		
		context("Destroy task") {
			
			test("Fails if amount is 0") {
				
				val dm = DistrictManager(setOf(type1), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
				
				dm.createConstructionTask(listOf(type1), ConstructionType.DESTROY, 1).runOnComplete()
				
				dm.districts[type1] shouldBe 0
				
			}
			
			test("Amount updates") {
				
				val dm = DistrictManager(setOf(type1), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
				
				dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 1).runOnComplete()
				dm.createConstructionTask(listOf(type1), ConstructionType.DESTROY, 1).runOnComplete()
				
				dm.districts[type1] shouldBe 0
				
			}
			
			test("Income updates") {
				
				val income = ResourceIncome()
				val dm = DistrictManager(setOf(type1), income, mockk(relaxUnitFun = true))
				val expected = mapOf(ResourceType("1") to 0)
				
				dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 1).runOnComplete()
				dm.createConstructionTask(listOf(type1), ConstructionType.DESTROY, 1).runOnComplete()
				
				income.income.shouldContainExactly(expected)
				
			}
			
			test("Jobs updates") {
				
				val jobs = JobManager(mockk(relaxUnitFun = true))
				val dm = DistrictManager(setOf(type1), mockk(relaxUnitFun = true), jobs)
				val expected = mapOf(JobType("1") to 0)
				
				dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 1).runOnComplete()
				dm.createConstructionTask(listOf(type1), ConstructionType.DESTROY, 1).runOnComplete()
				
				jobs.jobs.shouldContainExactly(expected)
				
			}
			
		}
		
		context("Replace task") {
			
			test("Fails if amount is 0") {
				
				val dm = DistrictManager(setOf(type1, type2), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
				
				dm.createConstructionTask(listOf(type2, type1), ConstructionType.REPLACE, 1).runOnComplete()
				
				dm.districts[type1] shouldBe 0
				dm.districts[type2] shouldBe 0
				
			}
			
			test("Amount updates") {
				
				val dm = DistrictManager(setOf(type1, type2), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
				
				dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 1).runOnComplete()
				dm.createConstructionTask(listOf(type2, type1), ConstructionType.REPLACE, 1).runOnComplete()
				
				dm.districts[type1] shouldBe 0
				dm.districts[type2] shouldBe 1
				
			}
			
			test("Income updates") {
				
				val income = ResourceIncome()
				val dm = DistrictManager(setOf(type1, type2), income, mockk(relaxUnitFun = true))
				val expected = mapOf(
					ResourceType("1") to 0,
					ResourceType("2") to 3
				)
				
				dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 1).runOnComplete()
				dm.createConstructionTask(listOf(type2, type1), ConstructionType.REPLACE, 1).runOnComplete()
				
				income.income.shouldContainExactly(expected)
				
			}
			
			test("Jobs updates") {
				
				val jobs = JobManager(mockk(relaxUnitFun = true))
				val dm = DistrictManager(setOf(type1, type2), mockk(relaxUnitFun = true), jobs)
				val expected = mapOf(
					JobType("1") to 0,
					JobType("2") to 1
				)
				
				dm.createConstructionTask(listOf(type1), ConstructionType.BUILD, 1).runOnComplete()
				dm.createConstructionTask(listOf(type2, type1), ConstructionType.REPLACE, 1).runOnComplete()
				
				jobs.jobs.shouldContainExactly(expected)
				
			}
			
		}
		
		context("Tool task") {
			
			test("Untooled") {
				
				val type3 = DistrictType("2")
				
				val dm = DistrictManager(setOf(type3), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
				
				dm.createConstructionTask(listOf(type3), ConstructionType.TOOL, 1).runOnComplete()
				
				dm.districts.shouldContainKey(type3)
				
			}
			
			test("Tooled") {
				
				val type3 = DistrictType("2")
				
				val dm = DistrictManager(setOf(type1, type3), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
				
				dm.createConstructionTask(listOf(type3, type1), ConstructionType.TOOL, 1).runOnComplete()
				
				dm.districts.shouldContainKey(type3)
			
			}
			
		}
		
		test("Detool task") {
			
			val dm = DistrictManager(setOf(type1), mockk(relaxUnitFun = true), mockk(relaxUnitFun = true))
			
			dm.createConstructionTask(listOf(type1), ConstructionType.DETOOL, 1).runOnComplete()
			
			dm.districts.shouldNotContainKey(type1)
			
		}
		
	}
	
})