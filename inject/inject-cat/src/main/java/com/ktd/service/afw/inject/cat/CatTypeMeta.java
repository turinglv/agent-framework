package com.ktd.service.afw.inject.cat;

import static com.ktd.service.afw.core.enums.FrameworkEnum.CAT;

import com.ktd.service.afw.boot.api.injector.InjectorTypeFactory;
import com.ktd.service.afw.boot.api.injector.InjectorTypeMeta;
import com.ktd.service.afw.core.injector.InjectorType;

public class CatTypeMeta implements InjectorTypeMeta {

  //// Cat  -------------------------------------------------------------------------------------------------------
  public static final InjectorType CAT_INJECTOR = InjectorTypeFactory.of(CAT, 01
      , "CatHomeInjector"
      , "com.ktd.service.afw.inject.cat.CatHomeInjector");

  @Override
  public InjectorType[] getTypes() {
    return new InjectorType[]{
        CAT_INJECTOR
    };
  }
}
