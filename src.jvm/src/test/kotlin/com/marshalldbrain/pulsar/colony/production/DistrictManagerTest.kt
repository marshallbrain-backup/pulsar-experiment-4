package com.marshalldbrain.pulsar.colony.production

import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.maps.shouldContainKeys
import io.kotlintest.specs.FunSpec
import io.kotlintest.shouldBe

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
		
		val dm = DistrictManager(districts)
		
		dm.districtTypes.shouldContainExactly(expected)
	
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
		
		val dm = DistrictManager(districts)
		
		dm.districts.shouldContainKeys(*expected.toTypedArray())
		
	}
})