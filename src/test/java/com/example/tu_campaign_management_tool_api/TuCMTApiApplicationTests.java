package com.example.tu_campaign_management_tool_api;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "com.example.tu_campaign_management_tool_api.authServices",
        "com.example.tu_campaign_management_tool_api.controllers",
        "com.example.tu_campaign_management_tool_api.jwtUtils"
})
class TuCMTApiApplicationTests {

}
