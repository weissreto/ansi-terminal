package ch.rweiss.terminal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class TestColor
{
  private static final Color RGB_BLACK = new Color(0,0,0);

  @Test
  public void equals()
  {
    assertThat(Color.BLACK)
        .isEqualTo(Color.BLACK)
        .isNotEqualTo(Color.WHITE)
        .isNotEqualTo(RGB_BLACK)
        .isNotEqualTo(FontStyle.BOLD)
        .isNotEqualTo(null);
    
    assertThat(RGB_BLACK)
        .isEqualTo(RGB_BLACK)
        .isEqualTo(new Color(0,0,0))
        .isNotEqualTo(new Color(1,0,0))
        .isNotEqualTo(new Color(0,1,0))
        .isNotEqualTo(new Color(0,0,1))
        .isNotEqualTo(Color.BLACK);
  }
  
  @Test
  public void _hashCode()
  {
    assertThat(Color.BLACK.hashCode())
        .isEqualTo(Color.BLACK.hashCode())
        .isNotEqualTo(Color.WHITE.hashCode());

    assertThat(RGB_BLACK.hashCode())
        .isEqualTo(RGB_BLACK.hashCode())
        .isEqualTo(new Color(0,0,0).hashCode())
        .isNotEqualTo(new Color(1,0,0).hashCode())
        .isNotEqualTo(new Color(0,1,0).hashCode())
        .isNotEqualTo(new Color(0,0,1).hashCode())
        .isNotEqualTo(Color.BLACK.hashCode());
  }
  
  @Test
  public void rgbRanges()
  {
    assertThatThrownBy(() -> new Color(-1, 0, 0)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Color(0, -1, 0)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Color(0, 0, -1)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Color(256, 0, 0)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Color(0, 256, 0)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new Color(0, 0, 256)).isInstanceOf(IllegalArgumentException.class);
    assertThatCode(() -> new Color(0,0,0)).doesNotThrowAnyException();
    assertThatCode(() -> new Color(255, 255, 255)).doesNotThrowAnyException();
  }
  
  @Test
  public void foreground()
  {
    assertThat(Color.BLACK.foreground()).isEqualTo(EscCode.sgr(30));
    assertThat(Color.WHITE.foreground()).isEqualTo(EscCode.sgr(37));
    assertThat(RGB_BLACK.foreground()).isEqualTo(EscCode.sgr(38,2,0,0,0));
    assertThat(new Color(1,2,3).foreground()).isEqualTo(EscCode.sgr(38,2,1,2,3));
  }
  
  @Test
  public void background()
  {
    assertThat(Color.BLACK.background()).isEqualTo(EscCode.sgr(40));
    assertThat(Color.WHITE.background()).isEqualTo(EscCode.sgr(47));
    assertThat(RGB_BLACK.background()).isEqualTo(EscCode.sgr(48,2,0,0,0));
    assertThat(new Color(1,2,3).background()).isEqualTo(EscCode.sgr(48,2,1,2,3));
  }
  
  @Test
  public void standardColor()
  {
    assertThatThrownBy(()->Color.standardColor(-1)).isInstanceOf(IllegalArgumentException.class);
    assertThat(Color.standardColor(0)).isEqualTo(Color.BLACK);
    assertThat(Color.standardColor(1)).isEqualTo(Color.RED);
    assertThat(Color.standardColor(2)).isEqualTo(Color.GREEN);
    assertThat(Color.standardColor(3)).isEqualTo(Color.YELLOW);
    assertThat(Color.standardColor(4)).isEqualTo(Color.BLUE);
    assertThat(Color.standardColor(5)).isEqualTo(Color.MAGENTA);
    assertThat(Color.standardColor(6)).isEqualTo(Color.CYAN);
    assertThat(Color.standardColor(7)).isEqualTo(Color.WHITE);
    assertThatThrownBy(()->Color.standardColor(8)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void brightStandardColor()
  {
    assertThatThrownBy(()->Color.brightStandardColor(-1)).isInstanceOf(IllegalArgumentException.class);
    assertThat(Color.brightStandardColor(0)).isEqualTo(Color.BRIGHT_BLACK);
    assertThat(Color.brightStandardColor(1)).isEqualTo(Color.BRIGHT_RED);
    assertThat(Color.brightStandardColor(2)).isEqualTo(Color.BRIGHT_GREEN);
    assertThat(Color.brightStandardColor(3)).isEqualTo(Color.BRIGHT_YELLOW);
    assertThat(Color.brightStandardColor(4)).isEqualTo(Color.BRIGHT_BLUE);
    assertThat(Color.brightStandardColor(5)).isEqualTo(Color.BRIGHT_MAGENTA);
    assertThat(Color.brightStandardColor(6)).isEqualTo(Color.BRIGHT_CYAN);
    assertThat(Color.brightStandardColor(7)).isEqualTo(Color.BRIGHT_WHITE);
    assertThatThrownBy(()->Color.brightStandardColor(8)).isInstanceOf(IllegalArgumentException.class);
  }
}
