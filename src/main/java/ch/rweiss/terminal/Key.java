package ch.rweiss.terminal;

import ch.rweiss.terminal.internal.EscCodeParser;

public abstract class Key
{
  public static final Key UP = new Control(EscCode.csi('A'), "UP");
  public static final Key DOWN = new Control(EscCode.csi('B'), "DOWN");
  public static final Key RIGHT = new Control(EscCode.csi('C'), "RIGHT");
  public static final Key LEFT = new Control(EscCode.csi('D'), "LEFT");
  
  public static final Key HOME = new Control(EscCode.csi('H'), "HOME");
  public static final Key END = new Control(EscCode.csi('F'), "END");
  
  public static final Key INSERT = new Control(EscCode.csi('~', 2), "INSERT");
  public static final Key DELETE = new Control(EscCode.csi('~', 3), "DELETE");
  
  public static final Key PAGE_UP = new Control(EscCode.csi('~', 5), "PAGE UP");
  public static final Key PAGE_DOWN = new Control(EscCode.csi('~', 6), "PAGE DOWN");
  
  public static final Key F1 = new Control(EscCode.esc("OP"), "F1");
  public static final Key F2 = new Control(EscCode.esc("OQ"), "F2");
  public static final Key F3 = new Control(EscCode.esc("OR"), "F3");
  public static final Key F4 = new Control(EscCode.esc("OS"), "F4");
  
  public static final Key F5 = new Control(EscCode.csi('~', 15), "F5");
  public static final Key F6 = new Control(EscCode.csi('~', 17), "F6");
  public static final Key F7 = new Control(EscCode.csi('~', 18), "F7");
  public static final Key F8 = new Control(EscCode.csi('~', 19), "F8");
  
  public static final Key F9 = new Control(EscCode.csi('~', 20), "F9");
  public static final Key F10 = new Control(EscCode.csi('~', 21), "F10");
  public static final Key F11 = new Control(EscCode.csi('~', 23), "F11");
  public static final Key F12 = new Control(EscCode.csi('~', 24), "F12");

  public static final Key CTRL_UP = new Control(EscCode.csi('A', 1, 5), "CTRL & UP");
  public static final Key CTRL_DOWN = new Control(EscCode.csi('B', 1, 5), "CTRL & DOWN");
  public static final Key CTRL_RIGHT = new Control(EscCode.csi('C', 1, 5), "CTRL & RIGHT");
  public static final Key CTRL_LEFT = new Control(EscCode.csi('D', 1, 5), "CTRL & LEFT");
  
  public static final Key CTRL_HOME = new Control(EscCode.csi('H', 1, 5), "CTRL & HOME");
  public static final Key CTRL_END = new Control(EscCode.csi('F', 1, 5), "CTRL & END");
  
  public static final Key CTRL_INSERT = new Control(EscCode.csi('~', 2, 5), "CTRL & INSERT");
  public static final Key CTRL_DELETE = new Control(EscCode.csi('~', 3, 5), "CTRL & DELETE");
  
  public static final Key CTRL_PAGE_UP = new Control(EscCode.csi('~', 5, 5), "CTRL & PAGE UP");
  public static final Key CTRL_PAGE_DOWN = new Control(EscCode.csi('~', 6, 5), "CTRL & PAGE DOWN");
  
  public static final Key CTRL_F1 = new Control(EscCode.csi('P', 1, 5), "CTRL & F1");
  public static final Key CTRL_F2 = new Control(EscCode.csi('Q', 1, 5), "CTRL & F2");
  public static final Key CTRL_F3 = new Control(EscCode.csi('R', 1, 5), "CTRL & F3");
  public static final Key CTRL_F4 = new Control(EscCode.csi('S', 1, 5), "CTRL & F4");
  
  public static final Key CTRL_F5 = new Control(EscCode.csi('~', 15, 5), "CTRL & F5");
  public static final Key CTRL_F6 = new Control(EscCode.csi('~', 17, 5), "CTRL & F6");
  public static final Key CTRL_F7 = new Control(EscCode.csi('~', 18, 5), "CTRL & F7");
  public static final Key CTRL_F8 = new Control(EscCode.csi('~', 19, 5), "CTRL & F8");
  
  public static final Key CTRL_F9 = new Control(EscCode.csi('~', 20, 5), "CTRL & F9");
  public static final Key CTRL_F10 = new Control(EscCode.csi('~', 21, 5), "CTRL & F10");
  public static final Key CTRL_F11 = new Control(EscCode.csi('~', 23, 5), "CTRL & F11");
  public static final Key CTRL_F12 = new Control(EscCode.csi('~', 24, 5), "CTRL & F12");

  public static final Key ALT_UP = new Control(EscCode.csi('A', 1, 3), "ALT & UP");
  public static final Key ALT_DOWN = new Control(EscCode.csi('B', 1, 3), "ALT & DOWN");
  public static final Key ALT_RIGHT = new Control(EscCode.csi('C', 1, 3), "ALT & RIGHT");
  public static final Key ALT_LEFT = new Control(EscCode.csi('D', 1, 3), "ALT & LEFT");
  
  public static final Key ALT_HOME = new Control(EscCode.csi('H', 1, 3), "ALT & HOME");
  public static final Key ALT_END = new Control(EscCode.csi('F', 1, 3), "ALT & END");
  
  public static final Key ALT_INSERT = new Control(EscCode.csi('~', 2, 3), "ALT & INSERT");
  public static final Key ALT_DELETE = new Control(EscCode.csi('~', 3, 3), "ALT & DELETE");
  
  public static final Key ALT_PAGE_UP = new Control(EscCode.csi('~', 5, 3), "ALT & PAGE UP");
  public static final Key ALT_PAGE_DOWN = new Control(EscCode.csi('~', 6, 3), "ALT & PAGE DOWN");
  
  public static final Key ALT_F1 = new Control(EscCode.csi('P', 1, 3), "ALT & F1");
  public static final Key ALT_F2 = new Control(EscCode.csi('Q', 1, 3), "ALT & F2");
  public static final Key ALT_F3 = new Control(EscCode.csi('R', 1, 3), "ALT & F3");
  public static final Key ALT_F4 = new Control(EscCode.csi('S', 1, 3), "ALT & F4");
  
  public static final Key ALT_F5 = new Control(EscCode.csi('~', 15, 3), "ALT & F5");
  public static final Key ALT_F6 = new Control(EscCode.csi('~', 17, 3), "ALT & F6");
  public static final Key ALT_F7 = new Control(EscCode.csi('~', 18, 3), "ALT & F7");
  public static final Key ALT_F8 = new Control(EscCode.csi('~', 19, 3), "ALT & F8");
  
  public static final Key ALT_F9 = new Control(EscCode.csi('~', 20, 3), "ALT & F9");
  public static final Key ALT_F10 = new Control(EscCode.csi('~', 21, 3), "ALT & F10");
  public static final Key ALT_F11 = new Control(EscCode.csi('~', 23, 3), "ALT & F11");
  public static final Key ALT_F12 = new Control(EscCode.csi('~', 24, 3), "ALT & F12");

  public static final Key CTRL_ALT_UP = new Control(EscCode.csi('A', 1, 7), "CTRL & ALT & UP");
  public static final Key CTRL_ALT_DOWN = new Control(EscCode.csi('B', 1, 7), "CTRL & ALT & DOWN");
  public static final Key CTRL_ALT_RIGHT = new Control(EscCode.csi('C', 1, 7), "CTRL & ALT & RIGHT");
  public static final Key CTRL_ALT_LEFT = new Control(EscCode.csi('D', 1, 7), "CTRL & ALT & LEFT");
  
  public static final Key CTRL_ALT_HOME = new Control(EscCode.csi('H', 1, 7), "CTRL & ALT & HOME");
  public static final Key CTRL_ALT_END = new Control(EscCode.csi('F', 1, 7), "CTRL & ALT & END");
  
  public static final Key CTRL_ALT_INSERT = new Control(EscCode.csi('~', 2, 7), "CTRL & ALT & INSERT");
  public static final Key CTRL_ALT_DELETE = new Control(EscCode.csi('~', 3, 7), "CTRL & ALT & DELETE");
  
  public static final Key CTRL_ALT_PAGE_UP = new Control(EscCode.csi('~', 5, 7), "CTRL & ALT & PAGE UP");
  public static final Key CTRL_ALT_PAGE_DOWN = new Control(EscCode.csi('~', 6, 7), "CTRL & ALT & PAGE DOWN");
  
  public static final Key CTRL_ALT_F1 = new Control(EscCode.csi('P', 1, 7), "CTRL & ALT & F1");
  public static final Key CTRL_ALT_F2 = new Control(EscCode.csi('Q', 1, 7), "CTRL & ALT & F2");
  public static final Key CTRL_ALT_F3 = new Control(EscCode.csi('R', 1, 7), "CTRL & ALT & F3");
  public static final Key CTRL_ALT_F4 = new Control(EscCode.csi('S', 1, 7), "CTRL & ALT & F4");
  
  public static final Key CTRL_ALT_F5 = new Control(EscCode.csi('~', 15, 7), "CTRL & ALT & F5");
  public static final Key CTRL_ALT_F6 = new Control(EscCode.csi('~', 17, 7), "CTRL & ALT & F6");
  public static final Key CTRL_ALT_F7 = new Control(EscCode.csi('~', 18, 7), "CTRL & ALT & F7");
  public static final Key CTRL_ALT_F8 = new Control(EscCode.csi('~', 19, 7), "CTRL & ALT & F8");
  
  public static final Key CTRL_ALT_F9 = new Control(EscCode.csi('~', 20, 7), "CTRL & ALT & F9");
  public static final Key CTRL_ALT_F10 = new Control(EscCode.csi('~', 21, 7), "CTRL & ALT & F10");
  public static final Key CTRL_ALT_F11 = new Control(EscCode.csi('~', 23, 7), "CTRL & ALT & F11");
  public static final Key CTRL_ALT_F12 = new Control(EscCode.csi('~', 24, 7), "CTRL & ALT & F12");

  private static Key[] WELL_KNOWN = new Key[] { 
      UP, DOWN, RIGHT, LEFT,
      HOME, END, INSERT, DELETE, PAGE_UP, PAGE_DOWN,
      F1, F2, F3, F4,
      F5, F6, F7, F8,
      F9, F10, F11, F12,
      
      CTRL_UP, CTRL_DOWN, CTRL_RIGHT, CTRL_LEFT,
      CTRL_HOME, CTRL_END, CTRL_INSERT, CTRL_DELETE, CTRL_PAGE_UP, CTRL_PAGE_DOWN,
      CTRL_F1, CTRL_F2, CTRL_F3, CTRL_F4,
      CTRL_F5, CTRL_F6, CTRL_F7, CTRL_F8,
      CTRL_F9, CTRL_F10, CTRL_F11, CTRL_F12,

      ALT_UP, ALT_DOWN, ALT_RIGHT, ALT_LEFT,
      ALT_HOME, ALT_END, ALT_INSERT, ALT_DELETE, ALT_PAGE_UP, ALT_PAGE_DOWN,
      ALT_F1, ALT_F2, ALT_F3, ALT_F4,
      ALT_F5, ALT_F6, ALT_F7, ALT_F8,
      ALT_F9, ALT_F10, ALT_F11, ALT_F12,

      CTRL_ALT_UP, CTRL_ALT_DOWN, CTRL_ALT_RIGHT, CTRL_ALT_LEFT,
      CTRL_ALT_HOME, CTRL_ALT_END, CTRL_ALT_INSERT, CTRL_ALT_DELETE, CTRL_ALT_PAGE_UP, CTRL_ALT_PAGE_DOWN,
      CTRL_ALT_F1, CTRL_ALT_F2, CTRL_ALT_F3, CTRL_ALT_F4,
      CTRL_ALT_F5, CTRL_ALT_F6, CTRL_ALT_F7, CTRL_ALT_F8,
      CTRL_ALT_F9, CTRL_ALT_F10, CTRL_ALT_F11, CTRL_ALT_F12
  };

  public abstract boolean isPrintable();
  public abstract boolean isControl();
  
  /**
   * Either the printable character of the pressed key e.g. "a" or the name of the control key &gt;UP&lt;
   */
  @Override
  public String toString()
  {
    return ""; 
  }
  
  /**
   * Either the printable character of the pressed key e.g. 'a'. '\033' ESCAPE for control keys 
   */
  public char toChar()
  {
    return ' ';
  }
  
  public static class Printable extends Key
  {
    private char ch;

    public Printable(char ch)
    {
      this.ch = ch;
    }

    @Override
    public boolean isPrintable()
    {
      return true;
    }

    @Override
    public boolean isControl()
    {
      return false;
    }
    
    @Override
    public String toString()
    {
      return ""+ch;
    }
    
    @Override
    public char toChar()
    {
      return ch;
    }
    
    @Override
    public boolean equals(Object other)
    {
      if (this == other)
      {
        return true;
      }
      if (other == null)
      {
        return false;
      }
      if (other.getClass() != Printable.class)
      {
        return false;
      }
      Printable printable = (Printable) other;
      return printable.ch == ch;
    }
    
    @Override
    public int hashCode()
    {
      return Character.hashCode(ch);
    }
  }
  
  public static class Control extends Key
  {
    private EscCode escCode;
    private String name;
    
    private Control(EscCode escCode, String name)
    {
      this.escCode = escCode;
      this.name = "<"+name+">";
    }
    
    private Control(EscCode escCode)
    {
      this(escCode, escCode.toString());
    }
    
    @Override
    public boolean isPrintable()
    {
      return false;
    }

    @Override
    public boolean isControl()
    {
      return true;
    }
    
    @Override
    public boolean equals(Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (obj == null)
      {
        return false;
      }
      if (obj.getClass() != Control.class)
      {
        return false;
      }
      Control other = (Control) obj;
      return escCode.equals(other.escCode);
    }
    
    @Override
    public int hashCode()
    {
      return escCode.hashCode();
    }
    
    @Override
    public String toString()
    {
      return name;
    }
    
    @Override
    public char toChar()
    {
      return EscCodeParser.ESCAPE;
    }

    public static Key forEscCode(EscCode escCode)
    {
      for (Key control : WELL_KNOWN)
      {
        if (((Control)control).escCode.equals(escCode))
        {
          return control;
        }
      }
      return new Control(escCode);
    }
  }  
}
