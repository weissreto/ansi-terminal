package ch.rweiss.terminal.internal;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

import ch.rweiss.terminal.EscCode;
import ch.rweiss.terminal.Key;
import ch.rweiss.terminal.Position;

public class InputReader extends Thread  
{
  private Deque<Key> keys = new LinkedList<>();
  private Deque<Position> positions = new LinkedList<>();

  public InputReader()
  {
    super(InputReader.class.getSimpleName());
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
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
  }
  
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
  
  public Key waitForKey()
  {
    synchronized(keys)
    {
      while (keys.isEmpty())
      {
        try
        {
          keys.wait();
        }
        catch(InterruptedException ex)
        {
          throw new RuntimeException(ex);
        }
      }
      return keys.removeLast();
    }
  }
  
  public void addPosition(EscCode escCode)
  {
    Position position = new Position(escCode.csiArguments()[0], escCode.csiArguments()[1]);
    synchronized(positions)
    {
      positions.addFirst(position);
      positions.notifyAll();
    }
  }

  public void resetPositions()
  {
    synchronized(positions)
    {
      positions.clear();
    }
  }

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
  
  private void readFromTerminal() throws IOException
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

  private static EscCode parseEscCode(EscCodeParser parser) throws IOException
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

  private static int readChar() throws IOException
  {
    return System.in.read();
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

  void shutdown()
  {
    interrupt();
    try
    {
      join(1000);
    }
    catch (InterruptedException ex)
    {
      ex.printStackTrace();
    }
  }
}
