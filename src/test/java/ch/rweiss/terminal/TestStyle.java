package ch.rweiss.terminal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TestStyle
{
  private Style DEFAULT = Style.create().toStyle();
  private Style RED = Style.create().withColor(Color.RED).toStyle();
  private Style UNDERLINE = Style.create().withFontStyle(FontStyle.UNDERLINE).toStyle();
  private Style RED_BACKGROUND = Style.create().withBackgroundColor(Color.RED).toStyle();

  @Test
  public void equals()
  {
    assertThat(DEFAULT)
        .isEqualTo(DEFAULT)
        .isEqualTo(Style.create().toStyle())
        .isNotEqualTo(RED)
        .isNotEqualTo(UNDERLINE)
        .isNotEqualTo(RED_BACKGROUND)
        .isNotEqualTo(FontStyle.BOLD)
        .isNotEqualTo(null);    
  }
  
  @Test 
  public void _hashCode()
  {
    assertThat(DEFAULT.hashCode())
        .isEqualTo(DEFAULT.hashCode())
        .isEqualTo(Style.create().toStyle().hashCode())
        .isNotEqualTo(RED.hashCode())
        .isNotEqualTo(UNDERLINE.hashCode())
        .isNotEqualTo(RED_BACKGROUND.hashCode());
  }
  
  @Test
  public void style()
  {
    assertThat(DEFAULT.style()).containsExactly(Style.RESET);
    assertThat(RED.style()).containsExactly(Style.RESET, Color.RED.foreground());
    assertThat(UNDERLINE.style()).containsExactly(Style.RESET, FontStyle.UNDERLINE.fontStyle());
    assertThat(RED_BACKGROUND.style()).containsExactly(Style.RESET, Color.RED.background());
    Style complexStyle = Style.create().withFontStyle(FontStyle.NEGATIVE).withColor(Color.BLUE).withBackgroundColor(Color.WHITE).toStyle();
    assertThat(complexStyle.style()).containsExactly(
        Style.RESET, 
        FontStyle.NEGATIVE.fontStyle(),
        Color.BLUE.foreground(), 
        Color.WHITE.background()); 
  }
}
