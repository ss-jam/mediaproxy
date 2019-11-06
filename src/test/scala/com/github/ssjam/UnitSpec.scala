package com.github.ssjam

import org.scalatest.{AsyncWordSpec, Inside, Inspectors, MustMatchers, OptionValues, WordSpec}

abstract class UnitSpec extends AsyncWordSpec with MustMatchers with
  OptionValues with Inside with Inspectors
