package com.ktd.service.afw.boot.api.injector.define;

import static ktd.bb.jar.asm.Opcodes.ACC_PRIVATE;
import static ktd.bb.jar.asm.Opcodes.ACC_SYNTHETIC;
import static ktd.bb.matcher.ElementMatchers.isStatic;
import static ktd.bb.matcher.ElementMatchers.not;

import com.ktd.service.afw.boot.api.exception.EnhanceException;
import com.ktd.service.afw.boot.api.exception.InjectorException;
import com.ktd.service.afw.boot.api.injector.InjectStatus;
import com.ktd.service.afw.boot.api.injector.point.StaticMethodInjectPoint;
import com.ktd.service.afw.boot.api.injector.enhance.ConstructorEnhancer;
import com.ktd.service.afw.boot.api.injector.enhance.EnhancedInstance;
import com.ktd.service.afw.boot.api.injector.enhance.InstanceMethodEnhancer;
import com.ktd.service.afw.boot.api.injector.enhance.InstanceMethodWithOverrideArgEnhancer;
import com.ktd.service.afw.boot.api.injector.enhance.OverrideCallable;
import com.ktd.service.afw.boot.api.injector.enhance.StaticMethodEnhancer;
import com.ktd.service.afw.boot.api.injector.enhance.StaticMethodWithOverrideArgEnhancer;
import com.ktd.service.afw.boot.api.injector.point.ConstructorInjectPoint;
import com.ktd.service.afw.boot.api.injector.point.InstanceMethodInjectPoint;
import com.ktd.service.afw.boot.api.util.StringUtil;
import ktd.bb.dynamic.DynamicType;
import ktd.bb.implementation.FieldAccessor;
import ktd.bb.implementation.MethodDelegation;
import ktd.bb.implementation.SuperMethodCall;
import ktd.bb.implementation.bind.annotation.Morph;

public abstract class ClassInjectorDefine extends AbstractClassInjectorDefine {

  /**
   * New field name.
   */
  public static final String ENHANCED_INSTALL_FIELD_NAME = "_$afwEnhancedInstanceField_";

  /**
   * Begin to define how to enhance class. After invoke this method, only means definition is
   * finished.
   *
   * @param enhanceOriginClassName target class name
   * @param newClassBuilder        byte-buddy's builder to manipulate class bytecode.
   * @return new byte-buddy's builder for further manipulation.
   */
  @Override
  protected DynamicType.Builder<?> enhance(String enhanceOriginClassName,
      DynamicType.Builder<?> newClassBuilder,
      ClassLoader targetClassLoader,
      InjectStatus injectStatus) throws InjectorException {
    newClassBuilder = this.enhanceClass(enhanceOriginClassName, newClassBuilder);
    newClassBuilder = this.enhanceInstance(enhanceOriginClassName, newClassBuilder,
        targetClassLoader, injectStatus);
    return newClassBuilder;
  }

  private DynamicType.Builder<?> enhanceClass(String enhanceOriginClassName,
      DynamicType.Builder<?> newClassBuilder) throws InjectorException {

    StaticMethodInjectPoint[] staticMethodInjectPoints = getStaticMethodsInjectPoints();

    if (staticMethodInjectPoints == null || staticMethodInjectPoints.length == 0) {
      return newClassBuilder;
    }

    for (StaticMethodInjectPoint staticMethodInjectPoint : staticMethodInjectPoints) {
      String injectorClassName = staticMethodInjectPoint.getMethodsInjectorClass();
      if (StringUtil.isEmpty(injectorClassName)) {
        throw new EnhanceException(
            "no StaticMethodAroundInjector define to enhance class " + enhanceOriginClassName);
      }

      if (staticMethodInjectPoint.isOverrideArgs()) {
        newClassBuilder = newClassBuilder.method(
                isStatic().and(staticMethodInjectPoint.getMethodsMatcher()))
            .intercept(
                MethodDelegation.withDefaultConfiguration()
                    .withBinders(Morph.Binder.install(OverrideCallable.class))
                    .to(new StaticMethodWithOverrideArgEnhancer(injectorClassName))
            );
      } else {
        newClassBuilder = newClassBuilder.method(
                isStatic().and(staticMethodInjectPoint.getMethodsMatcher()))
            .intercept(
                MethodDelegation.withDefaultConfiguration()
                    .to(new StaticMethodEnhancer(injectorClassName))
            );
      }

    }

    return newClassBuilder;
  }

  /**
   * Enhance a class to inject constructors and class instance methods.
   *
   * @param enhanceOriginClassName target class name
   * @param newClassBuilder        byte-buddy's builder to manipulate class bytecode.
   * @return new byte-buddy's builder for further manipulation.
   */
  private DynamicType.Builder<?> enhanceInstance(String enhanceOriginClassName,
      DynamicType.Builder<?> newClassBuilder, ClassLoader targetClassLoader,
      InjectStatus context) throws InjectorException {
    ConstructorInjectPoint[] constructorInjectPoints = getConstructorsInjectPoints();
    InstanceMethodInjectPoint[] instanceMethodInjectPoints = getInstanceMethodsInjectPoints();

    boolean existedConstructorInterceptPoint =
        constructorInjectPoints != null && constructorInjectPoints.length > 0;
    boolean existedMethodsInjectPoints =
        instanceMethodInjectPoints != null && instanceMethodInjectPoints.length > 0;

    /**
     * nothing need to be enhanced in class instance, maybe need enhance static methods.
     */
    if (!existedConstructorInterceptPoint && !existedMethodsInjectPoints) {
      return newClassBuilder;
    }

    /**
     * Manipulate class source code.<br/>
     *
     * new class need:<br/>
     * 1.Add field, name {@link #ENHANCED_INSTALL_FIELD_NAME}.
     * 2.Add a field accessor for this field.
     *
     * And make sure the source codes manipulation only occurs once.
     *
     */
    if (!context.isFieldInjected()) {
      newClassBuilder = newClassBuilder.defineField(ENHANCED_INSTALL_FIELD_NAME, Object.class,
              ACC_PRIVATE | ACC_SYNTHETIC)
          .implement(EnhancedInstance.class)
          .intercept(FieldAccessor.ofField(ENHANCED_INSTALL_FIELD_NAME));
      context.injectFieldCompleted();
    }

    /**
     * 2. enhance constructors
     */
    if (existedConstructorInterceptPoint) {
      for (ConstructorInjectPoint constructorInjectPoint : constructorInjectPoints) {
        newClassBuilder = newClassBuilder.constructor(
            constructorInjectPoint.getConstructorMatcher()).intercept(SuperMethodCall.INSTANCE
            .andThen(MethodDelegation.withDefaultConfiguration()
                .to(new ConstructorEnhancer(constructorInjectPoint.getConstructorInjectorClass(),
                    targetClassLoader))
            )
        );
      }
    }

    /**
     * 3. enhance instance methods
     */
    if (existedMethodsInjectPoints) {
      for (InstanceMethodInjectPoint instanceMethodInjectPoint : instanceMethodInjectPoints) {
        String injectorClassName = instanceMethodInjectPoint.getMethodsInjectorClass();
        if (StringUtil.isEmpty(injectorClassName)) {
          throw new EnhanceException(
              "no InstanceMethodAroundInjector define to enhance class " + enhanceOriginClassName);
        }

        if (instanceMethodInjectPoint.isOverrideArgs()) {
          newClassBuilder =
              newClassBuilder.method(
                      not(isStatic()).and(instanceMethodInjectPoint.getMethodsMatcher()))
                  .intercept(
                      MethodDelegation.withDefaultConfiguration()
                          .withBinders(Morph.Binder.install(OverrideCallable.class))
                          .to(new InstanceMethodWithOverrideArgEnhancer(injectorClassName,
                              targetClassLoader))
                  );
        } else {
          newClassBuilder =
              newClassBuilder.method(
                      not(isStatic()).and(instanceMethodInjectPoint.getMethodsMatcher()))
                  .intercept(
                      MethodDelegation.withDefaultConfiguration()
                          .to(new InstanceMethodEnhancer(injectorClassName, targetClassLoader))
                  );
        }
      }
    }

    return newClassBuilder;
  }

  protected abstract StaticMethodInjectPoint[] getStaticMethodsInjectPoints();

  protected abstract ConstructorInjectPoint[] getConstructorsInjectPoints();

  protected abstract InstanceMethodInjectPoint[] getInstanceMethodsInjectPoints();

}
