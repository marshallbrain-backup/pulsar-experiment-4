package com.marshalldbrain.pulsar.colony

import com.marshalldbrain.pulsar.resources.ResourceIncome
import com.marshalldbrain.pulsar.resources.ResourceType
import io.kotlintest.matchers.maps.shouldContainExactly
import io.kotlintest.specs.FunSpec

class ResourceIncomeTest : FunSpec({
	
	test("pingChange") {
		
		val type1 = ResourceType("+")
		val type2 = ResourceType("-")
		
		val income = ResourceIncome()
		val ping1 = mapOf(type1 to 5, type2 to 5)
		val ping2 = mapOf(type1 to 5, type2 to -5)
		val expected = mapOf(type1 to 10, type2 to 0)
		
		income.pingChange(ping1)
		income.pingChange(ping2)
		
		income.income.shouldContainExactly(expected)
		
	}
	
})