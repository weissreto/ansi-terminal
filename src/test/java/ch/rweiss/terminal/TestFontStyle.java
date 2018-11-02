package ch.rweiss.terminal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TestFontStyle
{
  @Test
  public void test()
  {
    assertThat(FontStyle.BOLD.fontStyle()).isEqualTo(EscCode.sgr(1));
    assertThat(FontStyle.UNDERLINE.fontStyle()).isEqualTo(EscCode.sgr(4));
    assertThat(FontStyle.NEGATIVE.fontStyle()).isEqualTo(EscCode.sgr(7));
  }
}
