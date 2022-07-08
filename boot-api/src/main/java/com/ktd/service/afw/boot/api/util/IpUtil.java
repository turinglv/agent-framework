package com.ktd.service.afw.boot.api.util;

import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * IP工具类
 */
public final class IpUtil {

  /**
   * 分隔符
   */
  public static final String REG_SEP = "\\.";

  public static final String ELLIPSIS = ".";
  /**
   * 数字型IP的int个数
   */
  public static final int IP_TOKEN_SIZE = 4;
  /**
   * 有线 enp/en/eth/em
   */
  public static final String ETH = "e";
  /**
   * 无线 wlp/wlan
   */
  public static final String WLAN = "w";
  /**
   * 线上服务器 bond
   */
  public static final String BOND = "b";
  /**
   * VPN
   */
  public static final String TAP = "tap";

  /**
   * 日志记录Logger
   */
  public static final String LOCALHOST = "127.0.0.1";
  /**
   * 日志输出
   */
  private static final Log LOG = LogProvider.getBootLog();
  private static String localIpv4Addr = null;

  /**
   * 无参私有构造函数
   */
  private IpUtil() {
  }

  public static String getLocalHostname() {
    try {
      InetAddress inetAddress = InetAddress.getLocalHost();
      return inetAddress.getHostName();
    } catch (UnknownHostException e) {
      return LOCALHOST;
    }
  }

  /**
   * 获取相关协议的IP
   *
   * @param inetClass IPv4或者IPv6
   * @return 相关协议的IP，如果没有获取到则返回null。
   */
  public static String getLocalIP(Class<? extends InetAddress> inetClass) {
    Enumeration<NetworkInterface> interfaces = null;
    try {
      //获取全部Interface
      interfaces = NetworkInterface.getNetworkInterfaces();
      //遍历IP地址使用的枚举
      Enumeration<InetAddress> inetAddressEnum;
      String iFDisplayName;
      String iName;
      while (interfaces.hasMoreElements()) {
        NetworkInterface networkInterface = interfaces.nextElement();
        iFDisplayName = networkInterface.getDisplayName().toLowerCase();
        iName = networkInterface.getName().toLowerCase();
        if (!networkInterface.isUp() || iFDisplayName.contains(TAP) || (
            !iFDisplayName.startsWith(ETH)
                && !iFDisplayName.startsWith(WLAN) && !iFDisplayName.startsWith(BOND)
                && !iName.startsWith(ETH)
                && !iName.startsWith(WLAN) && !iName.startsWith(BOND))) {
          continue;
        }
        //网卡的IP地址
        inetAddressEnum = networkInterface.getInetAddresses();
        while (inetAddressEnum.hasMoreElements()) {
          InetAddress inetAddress = inetAddressEnum.nextElement();
          if (inetAddress.getClass() == inetClass) {
            return inetAddress.getHostAddress();
          }
        }
      }
      LOG.error("未找到本地端口:{}", interfaces);
    } catch (Exception e) {
      LOG.error("获取本地IP出错:" + interfaces, e);
    }
    return null;
  }

  /**
   * 获取本地的IPv4协议的IP
   *
   * @return 如果获取到则返回相应的IPv4地址，否则返回null。
   */
  public static String getLocalIpv4Addr() {
    if (localIpv4Addr == null) {
      localIpv4Addr = getLocalIP(Inet4Address.class);
    }
    return localIpv4Addr;
  }

  /**
   * 将点隔式IP地址转换成整数型IP地址
   *
   * @param ipStr 点隔式IP地址
   * @return 整数型IP地址
   */
  public static Long convertIpv4(String ipStr) {
    if (ipStr == null || ipStr.isEmpty()) {
      return null;
    }
    try {
      String[] ipTokens = ipStr.split(REG_SEP);
      if (ipTokens.length != IP_TOKEN_SIZE) {
        throw new IllegalArgumentException("IP字符串错误:" + ipStr);
      }
      long ipNum = 0;
      for (int i = 0; i < IP_TOKEN_SIZE; i++) {
        ipNum = (ipNum << 8) + Integer.valueOf(ipTokens[i]);
      }
      return ipNum;
    } catch (RuntimeException e) {
      LOG.error("IP转换错误:" + ipStr, e);
      throw e;
    }
  }
}