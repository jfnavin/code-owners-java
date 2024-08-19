package com.jfnavin.codeowners.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to declare the owner of a class or package.
 * <p>
 * If added to a class, denotes the owner for that class.
 * <p>
 * If added to a package, denotes the owner for that package and its contents, including child packages
 * unless there are other annotations to override the owner lower in the package tree.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.TYPE})
public @interface Owner {
    String[] value();
}
