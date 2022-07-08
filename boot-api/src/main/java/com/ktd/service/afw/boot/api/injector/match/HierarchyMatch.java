package com.ktd.service.afw.boot.api.injector.match;

import static ktd.bb.matcher.ElementMatchers.hasSuperType;
import static ktd.bb.matcher.ElementMatchers.named;
import static ktd.bb.matcher.ElementMatchers.not;

import java.util.HashSet;
import java.util.Set;
import ktd.bb.description.type.TypeDescription;
import ktd.bb.matcher.ElementMatcher;
import ktd.bb.matcher.ElementMatchers;

public class HierarchyMatch implements IndirectMatch {

    private String namePrefix;

    private String[] parentTypes;

    private Set<String> excludeTypes;

    private ElementMatcher.Junction superTypeJunction;

    public HierarchyMatch(String namePrefix, String[] parentTypes, String[] excludeTypes) {
        this(parentTypes, excludeTypes);
        this.namePrefix = namePrefix;
    }

    private HierarchyMatch(String[] parentTypes, String[] excludeTypes) {
        if (parentTypes == null || parentTypes.length == 0) {
            throw new IllegalArgumentException("parentTypes is null");
        }
        this.parentTypes = parentTypes;
        if (excludeTypes != null && excludeTypes.length > 0) {
            this.excludeTypes = new HashSet<>();
            for (String excludeType : excludeTypes) {
                this.excludeTypes.add(excludeType);
            }
        }
    }

    public static ClassMatch byHierarchyMatch(String... parentTypes) {
        HierarchyMatch match = new HierarchyMatch(parentTypes, null);
        match.buildSuperTypeJunction();
        return match;
    }

    public static ClassMatch byHierarchyMatch(String[] parentTypes, String[] excludeTypes) {
        HierarchyMatch match = new HierarchyMatch(parentTypes, excludeTypes);
        match.buildSuperTypeJunction();
        return match;
    }

    public static ClassMatch byHierarchyMatch(String namePrefix, String[] parentTypes) {
        HierarchyMatch match = new HierarchyMatch(namePrefix, parentTypes, null);
        match.buildSuperTypeJunction();
        return match;
    }

    @Override
    public ElementMatcher.Junction buildJunction() {
        ElementMatcher.Junction excludeJunction = null;
        if (namePrefix != null && !namePrefix.isEmpty()) {
            excludeJunction = ElementMatchers.nameStartsWith(namePrefix);
        }
        if (excludeTypes != null) {
            for (String excludeType : excludeTypes) {
                if (excludeJunction == null) {
                    excludeJunction = not(named(excludeType));
                } else {
                    excludeJunction = excludeJunction.and(not(named(excludeType)));
                }
            }
        }
        return excludeJunction == null ? superTypeJunction : excludeJunction.and(superTypeJunction);
    }

    private void buildSuperTypeJunction() {
        ElementMatcher.Junction result = null;
        for (String superTypeName : parentTypes) {
            if (result == null) {
                result = hasSuperType(named(superTypeName));
            } else {
                result = result.or(hasSuperType(named(superTypeName)));
            }
        }
        this.superTypeJunction = ElementMatchers.not(ElementMatchers.isInterface()).and(result);
    }

    @Override
    public boolean isMatch(TypeDescription typeDescription) {
        if ((namePrefix != null && !typeDescription.getName().startsWith(namePrefix)) || (excludeTypes != null && excludeTypes.contains(typeDescription.getName()))) {
            return false;
        }
        return this.superTypeJunction.matches(typeDescription);
    }

}
