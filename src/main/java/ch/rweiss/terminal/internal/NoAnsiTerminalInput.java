package ch.rweiss.terminal.internal;

import java.util.Optional;

import ch.rweiss.terminal.Key;
import ch.rweiss.terminal.Position;

public class NoAnsiTerminalInput implements TerminalInput
{
  private final static Position DEFAULT_TERMINAL_SIZE = new Position(Integer.MAX_VALUE, 120);
  
  @Override
  public Optional<Key> readKey()
  {
    throw new RuntimeException("There is no terminal to read a key from");
  }

  @Override
  public Key waitForKey()
  {
    throw new RuntimeException("There is no terminal to read a key from");
  }

  @Override
  public Optional<Key> waitForKey(long timeoutInMillis)
  {
    throw new RuntimeException("There is no terminal to read a key from");
  }

  @Override
  public void resetPositions()
  {
    // nothing to do
  }
  
  @Override
  public Position waitForPosition()
  {
    return DEFAULT_TERMINAL_SIZE;
  }

}
