package org.dsl.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DSL
{
  public abstract String commName();

  public abstract String commSyntax();

  public abstract String[] commRegex();
}