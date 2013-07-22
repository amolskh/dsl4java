package org.dsl.bean;

import java.lang.reflect.Method;

public class DSLObject
{
  private Method m;
  private String commandName;
  private String commandSyntax;
  private String[] commandRegex;

  public Method getM()
  {
    return this.m;
  }

  public void setM(Method m) {
    this.m = m;
  }

  public String getCommandName() {
    return this.commandName;
  }

  public void setCommandName(String commandName) {
    this.commandName = commandName;
  }

  public String getCommandSyntax() {
    return this.commandSyntax;
  }

  public void setCommandSyntax(String commandSyntax) {
    this.commandSyntax = commandSyntax;
  }

  public String[] getCommandRegex() {
    return this.commandRegex;
  }

  public void setCommandRegex(String[] commandRegex) {
    this.commandRegex = commandRegex;
  }
}
