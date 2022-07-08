package com.ktd.service.afw.inject.cat;

import com.ktd.service.afw.boot.api.injector.define.ClassInjectorDefine;
import com.ktd.service.afw.boot.api.injector.match.ClassMatch;
import com.ktd.service.afw.boot.api.injector.match.NameMatch;
import com.ktd.service.afw.boot.api.injector.point.ConstructorInjectPoint;
import com.ktd.service.afw.boot.api.injector.point.DefaultStaticMethodInjectPoint;
import com.ktd.service.afw.boot.api.injector.point.InstanceMethodInjectPoint;
import com.ktd.service.afw.boot.api.injector.point.StaticMethodInjectPoint;
import ktd.bb.matcher.ElementMatchers;

public class CatHomeInjectorDefine extends ClassInjectorDefine {

  public static final String ENHANCE_CLASS = "com.dianping.cat.Cat";


  @Override
  protected StaticMethodInjectPoint[] getStaticMethodsInjectPoints() {
    return new StaticMethodInjectPoint[]{
        new DefaultStaticMethodInjectPoint(ElementMatchers.named("getCatHome"),
            "com.ktd.service.afw.inject.cat.CatHomeInjector")
    };
  }

  @Override
  protected ConstructorInjectPoint[] getConstructorsInjectPoints() {
    return null;
  }

  @Override
  protected InstanceMethodInjectPoint[] getInstanceMethodsInjectPoints() {
    return null;
  }

  @Override
  public ClassMatch enhanceClass() {
    return NameMatch.byName(ENHANCE_CLASS);
  }
}
