package com.ktd.service.afw.boot.api.log;

import com.ktd.service.afw.boot.api.serialize.Serializer;
import com.ktd.service.afw.boot.api.util.IpUtil;
import com.ktd.service.afw.boot.api.util.RuntimeMxUtil;
import com.ktd.service.afw.core.model.InstanceInfo;
import com.ktd.service.afw.core.sender.ErrorDetailDTO;
import com.ktd.service.afw.core.sender.ErrorInfoDTO;

public final class ErrorInfoUtil {

  /**
   * 私有构造函数
   */
  private ErrorInfoUtil() {
    super();
  }

  public static byte[] getErrorDetailRawBytes(ErrorInfoDTO errorInfoDTO) {
    ErrorDetailDTO errorDetail = new ErrorDetailDTO();
    errorDetail.setErrorInfo(errorInfoDTO);
    InstanceInfo instanceInfo = InstanceInfo.INSTANCE;
    errorDetail.setTimestamp(System.currentTimeMillis());
    if (instanceInfo != null) {
      errorDetail.setIpv4(instanceInfo.getIpv4());
      errorDetail.setHostname(instanceInfo.getHostname());
      errorDetail.setApplicationName(instanceInfo.getApplicationName());
      errorDetail.setEnv(instanceInfo.getEnv());
      errorDetail.setSet(instanceInfo.getSet());
      errorDetail.setDeployEnv(instanceInfo.getDeployEnv());
      errorDetail.setPid(instanceInfo.getPid());
      errorDetail.setAgentVersion(instanceInfo.getAgentVersion());

    } else {
      errorDetail.setPid(RuntimeMxUtil.getPid());
      errorDetail.setIpv4(IpUtil.getLocalIpv4Addr());
      errorDetail.setHostname(IpUtil.getLocalHostname());
    }

    return Serializer.INSTANCE.toBytes(errorDetail);
  }

}
