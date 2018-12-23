package ch.rweiss.terminal.internal;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

import ch.rweiss.check.Check;
import ch.rweiss.terminal.EscCode;
import ch.rweiss.terminal.Key;
import ch.rweiss.terminal.Position;

public class AnsiTerminalInput extends Thread implements TerminalInput  
{
  private Deque<Key> keys = new LinkedList<>();
  private Deque<Position> positions = new LinkedList<>();

  public AnsiTerminalInput()
  {
    super(AnsiTerminalInput.class.getSimpleName());
    setDaemon(true);
  }

  @Override
  public void run()
  {
    try
    {
      do
      {
        readFromTerminal();      
      } while (!isInterrupted());
    }
    catch(@SuppressWarnings("unused") InterruptedException ex)
    {      
      // happens to signal shutdown -> ignore and exit
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
  }
  
  @Override
  public Optional<Key> readKey()
  {
    synchronized(keys)
    {
      if (keys.isEmpty())
      {
        return Optional.empty();
      }
      return Optional.of(keys.removeLast());
    }
  }
  
  @Override
  public Key waitForKey()
  {
    return waitForKey(0).get();
  }
  
  @Override
  public Optional<Key> waitForKey(long timeoutInMillis)
  {
    Check.parameter("timeoutInMillis").withValue(timeoutInMillis).isPositive();
    long entryTime = System.currentTimeMillis();
    synchronized(keys)
    {
      while (keys.isEmpty())
      {
        long wait = entryTime+timeoutInMillis - System.currentTimeMillis();
        if (wait <= 0L)
        {
        	return Optional.empty();
        }

        try
        {
          keys.wait(wait);
        }
        catch(InterruptedException ex)
        {
          throw new RuntimeException(ex);
        }
      }
      return Optional.of(keys.removeLast());
    }
  }
  
  private void addPosition(EscCode escCode)
  {
    Position position = new Position(escCode.csiArguments()[0], escCode.csiArguments()[1]);
    synchronized(positions)
    {
      positions.addFirst(position);
      positions.notifyAll();
    }
  }

  @Override
  public void resetPositions()
  {
    synchronized(positions)
    {
      positions.clear();
    }
  }

  @Override
  public Position waitForPosition() 
  {
    synchronized(positions)
    {
      while (positions.isEmpty())
      {
        try
        {
          positions.wait();
        }
        catch(InterruptedException ex)
        {
          throw new RuntimeException(ex);
        }
      }
      return positions.removeLast();
    }
  }
  
  private void readFromTerminal() throws IOException, InterruptedException
  {
    int ch = readChar();
    EscCodeParser parser = EscCodeParser.start((char)ch);
    if (parser == null)
    {
      addKey(ch);
      return;
    }
    EscCode escCode = parseEscCode(parser);
    if (isPosition(escCode))
    {
      addPosition(escCode);
      return;
    }
    addKey(escCode);
  }

  private EscCode parseEscCode(EscCodeParser parser) throws IOException, InterruptedException
  {
    int ch;
    while(parser.isNotComplete())
    {
      ch = readChar();
      parser.putNext((char)ch);
    }
    EscCode escCode = parser.toEscCode();
    return escCode;
  }

  private int readChar() throws IOException, InterruptedException
  {
    if (isInterrupted())
    {
      throw new InterruptedException();
    }
    int ch = System.in.read();
    if (isInterrupted())
    {
      throw new InterruptedException();
    }
    return ch;
  }

  private static boolean isPosition(EscCode escCode)
  {
    return escCode.isCsi() && escCode.csiCommand() == 'R';
  }

  private void addKey(int ch)
  {
    Key key = new Key.Printable((char)ch);
    addKey(key);
  }

  private void addKey(EscCode escCode)
  {
    Key key = Key.Control.forEscCode(escCode);
    addKey(key);
  }

  private void addKey(Key key)
  {
    synchronized(keys)
    {
      keys.addFirst(key);
      keys.notifyAll();
    }
  }

  public void shutdown()
  {
    interrupt();
    try
    {
      join(1000);
    }
    catch (InterruptedException ex)
    {
      throw new RuntimeException(ex);
    }
  }
}
