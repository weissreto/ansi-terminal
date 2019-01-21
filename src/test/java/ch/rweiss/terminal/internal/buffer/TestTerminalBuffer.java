package ch.rweiss.terminal.internal.buffer;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractObjectAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.rweiss.terminal.AnsiTerminal;
import ch.rweiss.terminal.Color;
import ch.rweiss.terminal.FontStyle;
import ch.rweiss.terminal.Helper;
import ch.rweiss.terminal.Position;

public class TestTerminalBuffer
{
  private AnsiTerminal terminal = AnsiTerminal.get();
  private TerminalBuffer testee;

  @BeforeEach
  public void before()
  {
    terminal.offScreen().on(new Position(5,10));
    testee = Helper.getOffScreenBuffer(terminal);
  }

  @Test
  public void simpleWrite()
  {
    terminal.write("Hi");
    assertThat(testee.dump())
        .isEqualTo("Hi        \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          ");
  }

  @Test
  public void writeOverEndOfLine()
  {
    terminal.write("1234567890123");
    assertThat(testee.dump())
        .isEqualTo("1234567890\n" + 
                   "123       \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          ");
  }
  
  @Test
  public void writeWithNewLine()
  {
    terminal.write("12345\n67890\n123");
    assertThat(testee.dump())
        .isEqualTo("12345     \n" + 
                   "67890     \n" + 
                   "123       \n" + 
                   "          \n" + 
                   "          ");
  }

  @Test
  public void upAndDown()
  {
    terminal.write(1)
        .cursor().down()
        .write(2)
        .cursor().down()
        .write(3)
        .cursor().down()
        .write(4)
        .cursor().down()
        .write(5)
        .cursor().up()
        .write(6)
        .cursor().up()
        .write(7)
        .cursor().up()
        .write(8)
        .cursor()
        .up().write(9);
    assertThat(testee.dump())
        .isEqualTo("1       9 \n" + 
                   " 2     8  \n" + 
                   "  3   7   \n" + 
                   "   4 6    \n" + 
                   "    5     ");
  }

  @Test
  public void upAndDownWithParameter()
  {
    terminal
        .write(1)
        .cursor().down(3)
        .write(2)
        .cursor().up(3)
        .write(3)
        .cursor().down(3)
        .write(4)
        .cursor().up(3)
        .write(5)
        .cursor().down(3)
        .write(6)
        .cursor().up(3)
        .write(7)
        .cursor().down(3)
        .write(8)
        .cursor().up(3)
        .write(9);
    assertThat(testee.dump())
        .isEqualTo("1 3 5 7 9 \n" + 
                   "          \n" + 
                   "          \n" + 
                   " 2 4 6 8  \n" + 
                   "          ");
  }

  @Test
  public void forwardAndBackward()
  {
    terminal
        .cursor().forward()
        .write(1)
        .cursor().forward()
        .cursor().forward()
        .write(2)
        .cursor().backward()
        .cursor().backward()
        .write(3);
    assertThat(testee.dump())
        .isEqualTo(" 1 32     \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          ");
  }

  @Test
  public void forwardAndBackwardWithParameter()
  {
    terminal
        .cursor().forward(3)
        .write(1)
        .cursor().backward(3)
        .write(2)
        .cursor().forward(5)
        .write(3)
        .cursor().backward(3)
        .write(4);
    assertThat(testee.dump())
        .isEqualTo(" 2 1 4 3  \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          ");
  }
  
  @Test
  public void cursorHide()
  {
    terminal.cursor().hide();
    assertThat(testee.dump())
        .as("cursor hide should be ignored but should not throw an exception")
        .isEqualTo("          \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          ");
  }
  
  @Test
  public void cursorShow()
  {
    terminal.cursor().show();
    assertThat(testee.dump())
        .as("cursor show should be ignored but should not throw an exception")
        .isEqualTo("          \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          ");
  }

  @Test
  public void newLine()
  {
    terminal
        .write(1)
        .newLine()
        .write(2)
        .newLine()
        .write(3);
    assertThat(testee.dump())
        .isEqualTo("1         \n" + 
                   "2         \n" + 
                   "3         \n" + 
                   "          \n" + 
                   "          ");
  }
  
  @Test
  public void nextLine()
  {
    terminal
        .write(12)
        .cursor().nextLine()
        .write(34)
        .cursor().nextLine()
        .write(56);
    assertThat(testee.dump())
        .isEqualTo("12        \n" + 
                   "34        \n" + 
                   "56        \n" + 
                   "          \n" + 
                   "          ");
  }

  @Test
  public void nextLineWithParameters()
  {
    terminal
        .write(12)
        .cursor().nextLine(2)
        .write(34)
        .cursor().nextLine(2)
        .write(56);
    assertThat(testee.dump())
        .isEqualTo("12        \n" + 
                   "          \n" + 
                   "34        \n" + 
                   "          \n" + 
                   "56        ");
  }

  @Test
  public void previousLine()
  {
    terminal
        .cursor().nextLine(3)
        .write(12)
        .cursor().previousLine()
        .write(34)
        .cursor().previousLine()
        .write(56);
    assertThat(testee.dump())
        .isEqualTo("          \n" + 
                   "56        \n" + 
                   "34        \n" + 
                   "12        \n" +
                   "          ");
  }

  @Test
  public void previousLineWithParameters()
  {
    terminal
        .cursor().nextLine(4)
        .write(12)
        .cursor().previousLine(2)
        .write(34)
        .cursor().previousLine(2)
        .write(56);
    assertThat(testee.dump())
        .isEqualTo("56        \n" + 
                   "          \n" + 
                   "34        \n" + 
                   "          \n" +
                   "12        ");
  }
  
  @Test
  public void column()
  {
    terminal
        .cursor().column(3)
        .write(1)
        .cursor().column(1)
        .write(2)
        .cursor().column(8)
        .write(3);
    assertThat(testee.dump())
        .isEqualTo("2 1    3  \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          \n" +
                   "          ");
  }

  @Test
  public void position()
  {
    terminal
        .cursor().position(5,10)
        .write(1)
        .cursor().position(4,8)
        .write(2)
        .cursor().position(new Position(3,6))
        .write(3)
        .cursor().position(2,4)
        .write(4)
        .cursor().position(1,2)
        .write(5);
    assertThat(testee.dump())
        .isEqualTo(" 5        \n" + 
                   "   4      \n" + 
                   "     3    \n" + 
                   "       2  \n" +
                   "         1");
  }
  
  @Test
  public void getPosition()
  {
    assertThat(terminal.cursor().position())
        .isEqualTo(new Position(1,1));
    
    terminal
        .cursor().position(5,10);

    assertThat(terminal.cursor().position())
        .isEqualTo(new Position(5,10));
    
    terminal
        .cursor().position(new Position(3,5));

    assertThat(terminal.cursor().position())
        .isEqualTo(new Position(3,5));
  }
  
  @Test
  public void maxPosition()
  {
    assertThat(terminal.cursor().maxPosition())
        .isEqualTo(new Position(5,10));
  }

  @Test
  public void clearScreen()
  {
    fillScreen();
    terminal
        .cursor().position(3,5)
        .clear().screen();
    assertThat(testee.dump())
        .isEqualTo("          \n" + 
                   "          \n" + 
                   "          \n" + 
                   "          \n" +
                   "          ");
  }

  @Test
  public void clearScreenFromBegin()
  {
    fillScreen();
    terminal
        .cursor().position(3,5)
        .clear().screenFromBegin();
    assertThat(testee.dump())
        .isEqualTo("          \n" + 
                   "          \n" + 
                   "     XXXXX\n" + 
                   "XXXXXXXXXX\n" +
                   "XXXXXXXXXX");
  }

  @Test
  public void clearScreenToEnd()
  {
    fillScreen();
    terminal
        .cursor().position(3,5)
        .clear().screenToEnd();
    assertThat(testee.dump())
        .isEqualTo("XXXXXXXXXX\n" + 
                   "XXXXXXXXXX\n" +
                   "XXXX      \n" + 
                   "          \n" + 
                   "          ");
  }

  @Test
  public void clearLine()
  {
    fillScreen();
    terminal
        .cursor().position(3,5)
        .clear().line();
    assertThat(testee.dump())
        .isEqualTo("XXXXXXXXXX\n" + 
                   "XXXXXXXXXX\n" +
                   "          \n" + 
                   "XXXXXXXXXX\n" + 
                   "XXXXXXXXXX");
  }

  @Test
  public void clearLineFromBegin()
  {
    fillScreen();
    terminal
        .cursor().position(3,5)
        .clear().lineFromBegin();
    assertThat(testee.dump())
        .isEqualTo("XXXXXXXXXX\n" + 
                   "XXXXXXXXXX\n" +
                   "     XXXXX\n" + 
                   "XXXXXXXXXX\n" + 
                   "XXXXXXXXXX");
  }

  @Test
  public void clearLineToEnd()
  {
    fillScreen();
    terminal
        .cursor().position(3,5)
        .clear().lineToEnd();
    assertThat(testee.dump())
        .isEqualTo("XXXXXXXXXX\n" + 
                   "XXXXXXXXXX\n" +
                   "XXXX      \n" + 
                   "XXXXXXXXXX\n" + 
                   "XXXXXXXXXX");
  }
  
  @Test
  public void colorStandard()
  {
    terminal
      .color().black()
      .write(1)
      .color().white()
      .write(2)
      .color().blue()
      .write(3)
      .color().cyan()
      .write(4)
      .color().green()
      .write(5)
      .color().magenta()
      .write(6)
      .color().red()
      .write(7)
      .color().yellow()
      .write(8);
    assertColorAt(1,1).isEqualTo(Color.BLACK);
    assertColorAt(1,2).isEqualTo(Color.WHITE);
    assertColorAt(1,3).isEqualTo(Color.BLUE);
    assertColorAt(1,4).isEqualTo(Color.CYAN);
    assertColorAt(1,5).isEqualTo(Color.GREEN);
    assertColorAt(1,6).isEqualTo(Color.MAGENTA);
    assertColorAt(1,7).isEqualTo(Color.RED);
    assertColorAt(1,8).isEqualTo(Color.YELLOW);
  }

  @Test
  public void colorBright()
  {
    terminal
      .color().brightBlack()
      .write(1)
      .color().brightWhite()
      .write(2)
      .color().brightBlue()
      .write(3)
      .color().brightCyan()
      .write(4)
      .color().brightGreen()
      .write(5)
      .color().brightMagenta()
      .write(6)
      .color().brightRed()
      .write(7)
      .color().brightYellow()
      .write(8);
    assertColorAt(1,1).isEqualTo(Color.BRIGHT_BLACK);
    assertColorAt(1,2).isEqualTo(Color.BRIGHT_WHITE);
    assertColorAt(1,3).isEqualTo(Color.BRIGHT_BLUE);
    assertColorAt(1,4).isEqualTo(Color.BRIGHT_CYAN);
    assertColorAt(1,5).isEqualTo(Color.BRIGHT_GREEN);
    assertColorAt(1,6).isEqualTo(Color.BRIGHT_MAGENTA);
    assertColorAt(1,7).isEqualTo(Color.BRIGHT_RED);
    assertColorAt(1,8).isEqualTo(Color.BRIGHT_YELLOW);
  }

  @Test
  public void colorRgb()
  {
    terminal
      .color().rgb(10,20,30)
      .write(1)
      .color().rgb(100,110,120)
      .write(2);
    assertColorAt(1,1).isEqualTo(new Color(10,20,30));
    assertColorAt(1,2).isEqualTo(new Color(100, 110, 120));
  }

  @Test
  public void color()
  {
    Color color1 = new Color(10,20,30);
    Color color2 = new Color(10,20,30);
    terminal
      .color(color1)
      .write(1)
      .color(color2)
      .write(2);
    assertColorAt(1,1).isEqualTo(color1);
    assertColorAt(1,2).isEqualTo(color2);
  }

  @Test
  public void backgroundColorStandard()
  {
    terminal
      .backgroundColor().black()
      .write(1)
      .backgroundColor().white()
      .write(2)
      .backgroundColor().blue()
      .write(3)
      .backgroundColor().cyan()
      .write(4)
      .backgroundColor().green()
      .write(5)
      .backgroundColor().magenta()
      .write(6)
      .backgroundColor().red()
      .write(7)
      .backgroundColor().yellow()
      .write(8);
    assertBackgroundColorAt(1,1).isEqualTo(Color.BLACK);
    assertBackgroundColorAt(1,2).isEqualTo(Color.WHITE);
    assertBackgroundColorAt(1,3).isEqualTo(Color.BLUE);
    assertBackgroundColorAt(1,4).isEqualTo(Color.CYAN);
    assertBackgroundColorAt(1,5).isEqualTo(Color.GREEN);
    assertBackgroundColorAt(1,6).isEqualTo(Color.MAGENTA);
    assertBackgroundColorAt(1,7).isEqualTo(Color.RED);
    assertBackgroundColorAt(1,8).isEqualTo(Color.YELLOW);
  }

  @Test
  public void backgroundColorBright()
  {
    terminal
      .backgroundColor().brightBlack()
      .write(1)
      .backgroundColor().brightWhite()
      .write(2)
      .backgroundColor().brightBlue()
      .write(3)
      .backgroundColor().brightCyan()
      .write(4)
      .backgroundColor().brightGreen()
      .write(5)
      .backgroundColor().brightMagenta()
      .write(6)
      .backgroundColor().brightRed()
      .write(7)
      .backgroundColor().brightYellow()
      .write(8);
    assertBackgroundColorAt(1,1).isEqualTo(Color.BRIGHT_BLACK);
    assertBackgroundColorAt(1,2).isEqualTo(Color.BRIGHT_WHITE);
    assertBackgroundColorAt(1,3).isEqualTo(Color.BRIGHT_BLUE);
    assertBackgroundColorAt(1,4).isEqualTo(Color.BRIGHT_CYAN);
    assertBackgroundColorAt(1,5).isEqualTo(Color.BRIGHT_GREEN);
    assertBackgroundColorAt(1,6).isEqualTo(Color.BRIGHT_MAGENTA);
    assertBackgroundColorAt(1,7).isEqualTo(Color.BRIGHT_RED);
    assertBackgroundColorAt(1,8).isEqualTo(Color.BRIGHT_YELLOW);
  }

  @Test
  public void backgroundColorRgb()
  {
    terminal
      .backgroundColor().rgb(10,20,30)
      .write(1)
      .backgroundColor().rgb(100,110,120)
      .write(2);
    assertBackgroundColorAt(1,1).isEqualTo(new Color(10,20,30));
    assertBackgroundColorAt(1,2).isEqualTo(new Color(100, 110, 120));
  }

  @Test
  public void backgroundColor()
  {
    Color color1 = new Color(10,20,30);
    Color color2 = new Color(10,20,30);
    terminal
      .backgroundColor(color1)
      .write(1)
      .backgroundColor(color2)
      .write(2);
    assertBackgroundColorAt(1,1).isEqualTo(color1);
    assertBackgroundColorAt(1,2).isEqualTo(color2);
  }
  
  @Test
  public void bold()
  {
    terminal
      .fontStyle().bold()
      .write(1);
    assertFontStyleAt(1,1).isEqualTo(FontStyle.BOLD);
  }

  @Test
  public void underline()
  {
    terminal
      .fontStyle().underline()
      .write(1);
    assertFontStyleAt(1,1).isEqualTo(FontStyle.UNDERLINE);
  }

  @Test
  public void negative()
  {
    terminal
      .fontStyle().negative()
      .write(1);
    assertFontStyleAt(1,1).isEqualTo(FontStyle.NEGATIVE);
  }

  @Test
  public void fontStyle()
  {
    terminal
      .fontStyle(FontStyle.NEGATIVE)
      .write(1);
    assertFontStyleAt(1,1).isEqualTo(FontStyle.NEGATIVE);
  }

  @Test
  public void reset()
  {
    terminal
      .write(1)
      .fontStyle(FontStyle.NEGATIVE)
      .write(2)
      .color().black()
      .write(3)
      .backgroundColor().white()
      .write(4)
      .reset()
      .write(5);
    assertThat(testee.dump())
        .isEqualTo("12345     \n" + 
                   "          \n" +
                   "          \n" + 
                   "          \n" + 
                   "          ");
    assertColorAt(1,1).isNull();
    assertBackgroundColorAt(1,1).isNull();
    assertFontStyleAt(1,1).isNull();
    
    assertColorAt(1,2).isNull();
    assertBackgroundColorAt(1,2).isNull();
    assertFontStyleAt(1,2).isEqualTo(FontStyle.NEGATIVE);

    assertColorAt(1,3).isEqualTo(Color.BLACK);
    assertBackgroundColorAt(1,3).isNull();
    assertFontStyleAt(1,3).isEqualTo(FontStyle.NEGATIVE);

    assertColorAt(1,4).isEqualTo(Color.BLACK);
    assertBackgroundColorAt(1,4).isEqualTo(Color.WHITE);
    assertFontStyleAt(1,3).isEqualTo(FontStyle.NEGATIVE);
    
    assertColorAt(1,5).isNull();
    assertBackgroundColorAt(1,5).isNull();
    assertFontStyleAt(1,5).isNull();
  }
  
  private AbstractObjectAssert<?, Color> assertColorAt(int line, int column)
  {
    return assertThat(this.testee.getCharacter(line, column).color());
  }

  private AbstractObjectAssert<?, Color> assertBackgroundColorAt(int line, int column)
  {
    return assertThat(this.testee.getCharacter(line, column).backgroundColor());
  }
  
  private AbstractObjectAssert<?, FontStyle> assertFontStyleAt(int line, int column)
  {
    return assertThat(this.testee.getCharacter(line, column).fontStyle());
  }
  

  private void fillScreen()
  {
    for (int line=1; line <= 5; line++)
    {
      for (int column=1; column <= 10; column++)
      {
        terminal.write("X");
      }
    }
  }
}
