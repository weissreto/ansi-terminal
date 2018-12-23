package ch.rweiss.terminal.internal;

import java.util.Optional;

import ch.rweiss.terminal.Key;
import ch.rweiss.terminal.Position;

public interface TerminalInput
{
  Optional<Key> readKey();
  Key waitForKey();
  Optional<Key> waitForKey(long timeoutInMillis);

  void resetPositions();
  Position waitForPosition();
  
  static TerminalInput create(boolean ansi)
  {
    if (ansi)
    {
      AnsiTerminalInput ansiInput = new AnsiTerminalInput();
      ansiInput.start();
      return ansiInput;
    }
    return new NoAnsiTerminalInput();
  }
}
