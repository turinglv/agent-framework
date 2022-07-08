package com.ktd.service.afw.boot.impl.injector;

import static ktd.bb.matcher.ElementMatchers.isInterface;
import static ktd.bb.matcher.ElementMatchers.not;

import com.ktd.service.afw.boot.api.injector.define.AbstractClassInjectorDefine;
import com.ktd.service.afw.boot.api.injector.match.ClassMatch;
import com.ktd.service.afw.boot.api.injector.match.IndirectMatch;
import com.ktd.service.afw.boot.api.injector.match.NameMatch;
import com.ktd.service.afw.boot.api.injector.match.NameMultiMatch;
import com.ktd.service.afw.boot.api.injector.match.ProtectiveShieldMatcher;
import com.ktd.service.afw.boot.impl.injector.match.AbstractJunction;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import ktd.bb.description.NamedElement;
import ktd.bb.description.type.TypeDescription;
import ktd.bb.matcher.ElementMatcher;

public class InjectorFinder {

  private final Map<String, LinkedList<AbstractClassInjectorDefine>> nameMatchDefine = new HashMap<String, LinkedList<AbstractClassInjectorDefine>>();

  private final List<AbstractClassInjectorDefine> signatureMatchDefine = new LinkedList<AbstractClassInjectorDefine>();

  public InjectorFinder(List<AbstractClassInjectorDefine> plugins) {
    for (AbstractClassInjectorDefine plugin : plugins) {
      ClassMatch match = plugin.enhanceClass();
      if (match == null) {
        continue;
      }
      if (match instanceof NameMatch) {
        NameMatch nameMatch = (NameMatch) match;
        LinkedList<AbstractClassInjectorDefine> injectorDefines = nameMatchDefine.get(
            nameMatch.getClassName());
        if (injectorDefines == null) {
          injectorDefines = new LinkedList<>();
          nameMatchDefine.put(nameMatch.getClassName(), injectorDefines);
        }
        injectorDefines.add(plugin);
      } else if (match instanceof NameMultiMatch) {
        NameMultiMatch multiMatch = (NameMultiMatch) match;
        for (String className : multiMatch.getClassNames()) {
          LinkedList<AbstractClassInjectorDefine> injectorDefines = nameMatchDefine.get(className);
          if (injectorDefines == null) {
            injectorDefines = new LinkedList<>();
            nameMatchDefine.put(className, injectorDefines);
          }
          injectorDefines.add(plugin);
        }
      } else {
        signatureMatchDefine.add(plugin);
      }
    }
  }


  public List<AbstractClassInjectorDefine> find(TypeDescription typeDescription) {
    List<AbstractClassInjectorDefine> matchedPlugins = new LinkedList<>();
    String typeName = typeDescription.getTypeName();
    if (nameMatchDefine.containsKey(typeName)) {
      matchedPlugins.addAll(nameMatchDefine.get(typeName));
    }

    for (AbstractClassInjectorDefine pluginDefine : signatureMatchDefine) {
      IndirectMatch match = (IndirectMatch) pluginDefine.enhanceClass();
      if (match.isMatch(typeDescription)) {
        matchedPlugins.add(pluginDefine);
      }
    }

    return matchedPlugins;
  }

  public ElementMatcher<? super TypeDescription> buildMatch() {
    ElementMatcher.Junction judge = new AbstractJunction<NamedElement>() {
      @Override
      public boolean matches(NamedElement target) {
        return nameMatchDefine.containsKey(target.getActualName());
      }
    };
    judge = judge.and(not(isInterface()));
    for (AbstractClassInjectorDefine define : signatureMatchDefine) {
      ClassMatch match = define.enhanceClass();
      if (match instanceof IndirectMatch) {
        judge = judge.or(((IndirectMatch) match).buildJunction());
      }
    }
    return new ProtectiveShieldMatcher(judge);
  }
}
