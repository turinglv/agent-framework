package com.ktd.service.afw.boot.api.injector.classloader;

import com.ktd.service.afw.boot.api.loader.afw.JarURLConnection;
import com.ktd.service.afw.boot.api.log.Log;
import com.ktd.service.afw.boot.api.log.LogProvider;
import com.ktd.service.afw.boot.api.loader.LaunchedURLClassLoader;
import com.ktd.service.afw.core.anno.source.Singleton;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Injector的加载器
 */
@Singleton
public class InjectorClassLoader extends LaunchedURLClassLoader {

    /**
     * 日志输出
     */
    private static final Log LOG = LogProvider.getBootLog();

    private static final String INJECT_CLAZZ_SEPARATOR = ";";

    private static final String INJECTOR_DEFINES_MANIFEST = "Afw-Injector-Defines";

    private Set<String> injectorDefineClazzNames;

    /**
     * Jar的URL
     */
    private List<URL> jarUrls;

    public InjectorClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.jarUrls = Arrays.asList(urls);
        this.injectorDefineClazzNames = new HashSet<>();
        this.init();
    }

    /**
     * 初始化该ClassLoader
     */
    private void init() {
        for (URL url : jarUrls) {
            try {
                this.loadInjectClass(url);
            } catch (Exception e) {
                LOG.error("加载Injector出现错误！", e);
            }
        }
    }

    private void loadInjectClass(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        if (connection instanceof JarURLConnection) {
            JarFile jarFile = ((JarURLConnection) connection).getJarFile();
            Manifest manifest = jarFile.getManifest();
            String injectorDefineClazzes = manifest.getMainAttributes().getValue(INJECTOR_DEFINES_MANIFEST);
            if (injectorDefineClazzes == null) {
                return;
            }
            String[] injectClazzTokens = injectorDefineClazzes.split(INJECT_CLAZZ_SEPARATOR);
            for (String injectClazzToken : injectClazzTokens) {
                LOG.debug("[Injector] Load InjectorDefine:{}", jarFile.getName(), injectClazzToken);
                if (!this.injectorDefineClazzNames.add(injectClazzToken)) {
                    LOG.warn("[Injector] 发现重复的InjectorDefine:{}", jarFile.getName(), injectClazzToken);
                }
            }
        }
    }

    public Set<String> getInjectorDefineClazzNames() {
        return injectorDefineClazzNames;
    }

}
