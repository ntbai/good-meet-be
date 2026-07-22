package com.practice.goodmeet;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTests {

  private final ApplicationModules modules = ApplicationModules.of(GoodMeetApplication.class);

  @Test
  void verifiesModuleBoundaries() {
    modules.verify();
  }

  @Test
  void detectsAllBusinessModules() {
    modules.getModuleByName("modules.identity").orElseThrow();
    modules.getModuleByName("modules.meeting").orElseThrow();
    modules.getModuleByName("modules.participant").orElseThrow();
    modules.getModuleByName("modules.notification").orElseThrow();
    modules.getModuleByName("modules.calendar").orElseThrow();
    modules.getModuleByName("modules.audit").orElseThrow();
  }
}
