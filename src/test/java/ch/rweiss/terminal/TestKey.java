package ch.rweiss.terminal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import ch.rweiss.terminal.Key.Printable;

public class TestKey
{
  private static final Printable KEY_B = new Key.Printable('b');

  @Test
  public void equals()
  {
    assertThat(Key.DELETE)
        .isEqualTo(Key.DELETE)
        .isNotEqualTo(Key.ALT_DELETE)
        .isNotEqualTo(KEY_B)
        .isNotEqualTo(null);
    
    Key.Printable otherInstanceButEqualKey = new Key.Printable('b');
    Key.Printable otherInstanceNotEqualKey = new Key.Printable('a');
    assertThat(KEY_B)
        .isEqualTo(KEY_B)
        .isEqualTo(otherInstanceButEqualKey)
        .isNotEqualTo(otherInstanceNotEqualKey)
        .isNotEqualTo(Key.DELETE)
        .isNotEqualTo(null);
  }
  
  @Test 
  public void _hashCode()
  {
    assertThat(Key.DELETE.hashCode())
        .isEqualTo(Key.DELETE.hashCode())
        .isNotEqualTo(Key.ALT_DELETE.hashCode());

    Key.Printable otherInstanceButEqualKey = new Key.Printable('b');
    Key.Printable otherInstanceNotEqualKey = new Key.Printable('a');
    assertThat(KEY_B.hashCode())
        .isEqualTo(KEY_B.hashCode())
        .isEqualTo(otherInstanceButEqualKey.hashCode())
        .isNotEqualTo(otherInstanceNotEqualKey.hashCode())
        .isNotEqualTo(Key.DELETE.hashCode());
  }
  
  @Test
  public void _toString()
  {
    assertThat(Key.DELETE.toString()).isEqualTo("<DELETE>");
    assertThat(Key.ALT_DELETE.toString()).isEqualTo("<ALT & DELETE>");
    assertThat(Key.CTRL_DELETE.toString()).isEqualTo("<CTRL & DELETE>");
    assertThat(Key.CTRL_ALT_DELETE.toString()).isEqualTo("<CTRL & ALT & DELETE>");
    assertThat(KEY_B.toString()).isEqualTo("b");
  }
  
  @Test
  public void isPrintable()
  {
    assertThat(Key.DELETE.isPrintable()).isFalse();
    assertThat(KEY_B.isPrintable()).isTrue();
  }

  @Test
  public void isControl()
  {
    assertThat(Key.DELETE.isControl()).isTrue();
    assertThat(KEY_B.isControl()).isFalse();
  }
  
  @Test
  public void forEscCode()
  {
    assertThat(Key.Control.forEscCode(EscCode.csi('~', 3))).isEqualTo(Key.DELETE);
    assertThat(Key.Control.forEscCode(EscCode.csi('~', 3, 3))).isEqualTo(Key.ALT_DELETE);
    assertThat(Key.Control.forEscCode(EscCode.csi('~', 3, 5))).isEqualTo(Key.CTRL_DELETE);
    assertThat(Key.Control.forEscCode(EscCode.csi('~', 3, 7))).isEqualTo(Key.CTRL_ALT_DELETE);
    assertThat(Key.Control.forEscCode(EscCode.csi('~', 3, 2))).isEqualTo(Key.Control.forEscCode(EscCode.csi('~', 3, 2)));
  }

}
