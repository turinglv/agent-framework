package com.ktd.service.afw.boot.api.log;


import com.ktd.service.afw.core.anno.source.Nullable;
import com.ktd.service.afw.core.response.BaseResponse;

public interface RemoteErrorSender {

  BaseResponse<String> send(@Nullable Throwable throwable, String message);

}
