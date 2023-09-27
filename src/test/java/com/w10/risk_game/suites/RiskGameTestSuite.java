package com.w10.risk_game.suites;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Risk Game Test Suite")
@SelectPackages("com.w10.risk_game")
@IncludeClassNamePatterns(".*Test")
public class RiskGameTestSuite {
}
