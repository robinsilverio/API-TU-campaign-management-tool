package com.example.tu_campaign_management_tool_api;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SelectPackages({
        "com.example.tu_campaign_management_tool_api.authServices",
        "com.example.tu_campaign_management_tool_api.controllers",
        "com.example.tu_campaign_management_tool_api.jwtUtils"
})
@Suite
class TuCMTApiApplicationTests {

}
