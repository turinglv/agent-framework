package com.ktd.service.afw.boot.api.log;

import com.ktd.service.afw.boot.api.util.BaseResponseUtil;
import com.ktd.service.afw.boot.api.util.AgentLogUtil;
import com.ktd.service.afw.boot.api.util.ExceptionUtil;
import com.ktd.service.afw.core.anno.source.Nullable;
import com.ktd.service.afw.core.response.BaseResponse;
import com.ktd.service.afw.core.sender.ErrorInfoDTO;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRemoteErrorSender implements RemoteErrorSender {

  private String reportUrl;

  /**
   * 公共构造函数
   */
  public HttpRemoteErrorSender() {
    super();
  }

  @Override
  public BaseResponse<String> send(@Nullable Throwable throwable, String message) {
    if (reportUrl == null) {
      AgentLogUtil.error("发生异常，且不能上报：", throwable);
      return BaseResponseUtil.fail();
    }
    try {
      ErrorInfoDTO errorInfoDTO = ExceptionUtil.getErrorInfo(throwable, true);
      errorInfoDTO.setMessage(message);
      byte[] errorDetailData = ErrorInfoUtil.getErrorDetailRawBytes(errorInfoDTO);
      int statusCode = this.send(errorDetailData);
      return BaseResponseUtil.success(String.valueOf(statusCode));
    } catch (Exception e) {
      AgentLogUtil.error("上报发生异常：", e);
      return BaseResponseUtil.fail();
    }
  }

  protected int send(byte[] data) throws Exception {
    URL postUrl = new URL(reportUrl);
    // 打开连接
    HttpURLConnection conn = (HttpURLConnection) postUrl.openConnection();
    // Output to the connection. Default is
    // false, set to true because post
    // method must write something to the
    // connection
    // 设置是否向connection输出，因为这个是post请求，参数要放在
    // http正文内，因此需要设为true
    conn.setDoOutput(true);
    // Read from the connection. Default is true.
    conn.setDoInput(true);
    // Set the post method. Default is GET
    conn.setRequestMethod("POST");
    // Post cannot use caches
    // Post 请求不能使用缓存
    conn.setUseCaches(false);
    // This method takes effects to
    // every instances of this class.
    // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
    // connection.setFollowRedirects(true);

    // This methods only
    // takes effacts to this
    // instance.
    // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
    conn.setInstanceFollowRedirects(true);
    // Set the content type to urlencoded,
    // because we will write
    // some URL-encoded content to the
    // connection. Settings above must be set before connect!
    // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
    // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
    // 进行编码

    // 设置维持长连接
    conn.setRequestProperty("Connection", "Keep-Alive");
    // 设置文件字符集:
    conn.setRequestProperty("Charset", "UTF-8");
    // 设置文件长度
    conn.setRequestProperty("Content-Length", String.valueOf(data.length));

    // 设置文件类型:
    conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

    // 开始连接请求
    conn.connect();
    OutputStream out = conn.getOutputStream();
    // 写入请求的字符串
    out.write(data);
    out.flush();
    out.close();
    // 请求返回的状态
    return conn.getResponseCode();
  }

  public void setReportUrl(String reportUrl) {
    this.reportUrl = reportUrl;
  }

}
