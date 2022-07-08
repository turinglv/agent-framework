package com.ktd.service.afw.boot.impl;

import com.ktd.service.afw.boot.api.config.Configs;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.core.anno.source.Singleton;
import java.io.File;
import ktd.bb.description.type.TypeDescription;
import ktd.bb.dynamic.DynamicType;

@Singleton
public enum EnhancedClassStorage {

    INSTANCE;

    /**
     * 日志输出
     */
    private static final Log LOG = LogProvider.getBootLog();

    private File enhancedClassesStorageDirFile;

    public void storage(TypeDescription typeDescription, DynamicType dynamicType) {
        String outputDir = Configs.Enhance.CLASS_OUTPUT_DIR.getProp();
        boolean outputSwitch = Configs.Enhance.CLASS_OUTPUT_SWITCH.getBoolean();
        if (!outputSwitch || outputDir == null) {
            LOG.debug("由于关闭输出Enhance开关{}或者没有设置输出Enhance的目录{}，所以关闭输出织入后的类。", outputSwitch, outputDir);
            return;
        }

        synchronized (INSTANCE) {
            if (enhancedClassesStorageDirFile == null) {
                try {
                    enhancedClassesStorageDirFile = new File(outputDir);
                    if (!enhancedClassesStorageDirFile.exists()) {
                        enhancedClassesStorageDirFile.mkdirs();
                    }
                    LOG.debug("输出文件夹:{}", outputDir);
                } catch (Exception e) {
                    LOG.error("创建Debug的类输出文件夹发生异常。文件夹:{}.", outputDir, e);
                    return;
                }
            }

            try {
                dynamicType.saveIn(enhancedClassesStorageDirFile);
            } catch (Exception e) {
                LOG.error("保存class {}发生异常." + typeDescription.getActualName(), e);
            }
        }
    }
}
