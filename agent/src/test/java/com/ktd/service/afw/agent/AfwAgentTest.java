package com.ktd.service.afw.agent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.ktd.service.afw.boot.api.core.AgentContext;
import com.ktd.service.afw.boot.api.log.AgentErrorContext;
import com.ktd.service.afw.boot.api.util.MxBeanUtil;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Agent测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest({ManagementFactory.class, MxBeanUtil.class, AgentErrorContext.class})
@PowerMockIgnore({"javax.management.*",})
@FixMethodOrder
public class AfwAgentTest {

  @Test
  public void testPremainTrace() {
    AfwAgent.AGENT_START_FLAG.set(false);
    mockStatic(AgentErrorContext.class);
//    String fileName = TestCommonUtil.getTestAgentPath();
    String fileName = "/Users/lvxiaofeng/glp/agent-framework/agent/src/test/resources/base/afw-agent-1.0.0.RELEASE-bin.jar";
    FinalFieldUtil.setValue(AfwAgent.class, "started", false);
    String bootClassPath = "-Xbootclasspath/a:" + fileName;
    RuntimeMXBean runtimeMXBean = mock(RuntimeMXBean.class);
    mockStatic(MxBeanUtil.class);
    when(runtimeMXBean.getInputArguments()).thenReturn(Arrays.asList(bootClassPath));
    when(MxBeanUtil.getRuntimeMXBean()).thenReturn(runtimeMXBean);
    AgentContext.init();
    Assert.assertEquals(fileName, AgentContext.getAgentInfo().getAgentPath());
    Instrumentation instrumentation = mock(Instrumentation.class);
    System.setProperty(AfwAgent.AGENT_OPEN, "true");
    AfwAgent.premain("argument", instrumentation);
    Assert.assertTrue(AfwAgent.isStarted());
  }
}
