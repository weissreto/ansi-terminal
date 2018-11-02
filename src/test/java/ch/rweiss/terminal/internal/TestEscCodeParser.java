package ch.rweiss.terminal.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import ch.rweiss.terminal.EscCode;

public class TestEscCodeParser
{
  @Test
  public void start()
  {
    assertThat(EscCodeParser.start(EscCodeParser.ESCAPE)).isNotNull();
    assertThat(EscCodeParser.start('a')).isNull();
    assertThat(EscCodeParser.start('X')).isNull();
    assertThat(EscCodeParser.start('p')).isNull();
  }

  @Test
  public void isNotComplete()
  {
    assertNotComplete(EscCode.csi('A'));
    assertNotComplete(EscCode.csi('A', 1, 3));
    assertNotComplete(EscCode.esc("OP"));
    assertNotComplete(EscCode.csi('~', 15));
    assertNotComplete(EscCode.csi('~', 15, 5));
  }
  
  @Test
  public void toEscCode()
  {
    assertEscCode(EscCode.csi('A'));
    assertEscCode(EscCode.csi('A', 1, 3));
    assertEscCode(EscCode.esc("OP"));
    assertEscCode(EscCode.csi('~', 15));
    assertEscCode(EscCode.csi('~', 15, 5));
  }

  private static void assertNotComplete(EscCode escCode)
  {
    String esc = escCode.escCode();
    EscCodeParser parser = EscCodeParser.start(esc.charAt(0));
    assertThat(parser).isNotNull();
    assertThat(parser.isNotComplete()).isTrue();
    for (int pos = 1; pos < esc.length()-1; pos++)
    {
      parser.putNext(esc.charAt(pos));
      assertThat(parser.isNotComplete()).isTrue();
    }
    parser.putNext(esc.charAt(esc.length()-1));
    assertThat(parser.isNotComplete()).isFalse();
  }

  private static void assertEscCode(EscCode escCode)
  {
    String esc = escCode.escCode();
    EscCodeParser parser = EscCodeParser.start(esc.charAt(0));
    for (int pos = 1; pos < esc.length(); pos++)
    {
      parser.putNext(esc.charAt(pos));
    }
    assertThat(parser.toEscCode()).isEqualTo(escCode);
  }

}
