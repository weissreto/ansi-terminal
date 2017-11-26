package ch.weiss.check;

import java.text.MessageFormat;

class AbstractCheck
{

  private Thrower thrower;

  AbstractCheck(Thrower thrower)
  {
    this.thrower = thrower;
  }
  
  void fail(String message, Object... arguments)
  {
    message = MessageFormat.format(message, arguments);
    thrower.throwNow(message);
  }

}
