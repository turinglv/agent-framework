package com.ktd.service.afw.boot.impl.injector.match;

import ktd.bb.description.method.MethodDescription;
import ktd.bb.description.method.ParameterList;
import ktd.bb.matcher.ElementMatcher;

public class ArgumentTypeNameMatch implements ElementMatcher<MethodDescription> {
    /**
     * the index define arguments list.
     */
    private int index;

    /**
     * the target argument type at {@link ArgumentTypeNameMatch#index} define the arguments list.
     */
    private String argumentTypeName;

    /**
     * declare the match target method with the certain index and type.
     *
     * @param index            the index define arguments list.
     * @param argumentTypeName target argument type
     */
    private ArgumentTypeNameMatch(int index, String argumentTypeName) {
        this.index = index;
        this.argumentTypeName = argumentTypeName;
    }

    /**
     * The static method to create {@link ArgumentTypeNameMatch}
     * This is a delegate method to follow byte-buddy {@link ElementMatcher}'s code style.
     *
     * @param index            the index define arguments list.
     * @param argumentTypeName target argument type
     * @return new {@link ArgumentTypeNameMatch} instance.
     */
    public static ElementMatcher<MethodDescription> takesArgumentWithType(int index, String argumentTypeName) {
        return new ArgumentTypeNameMatch(index, argumentTypeName);
    }

    /**
     * Match the target method.
     *
     * @param target target method description.
     * @return true if matched. or false.
     */
    @Override
    public boolean matches(MethodDescription target) {
        ParameterList<?> parameters = target.getParameters();
        if (parameters.size() > index) {
            return parameters.get(index).getType().asErasure().getName().equals(argumentTypeName);
        }

        return false;
    }
}
