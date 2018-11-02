package ch.rweiss.terminal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class TestPosition
{
  private Position TOP_LEFT = new Position(0,0);

  @Test
  public void equals()
  {
    assertThat(TOP_LEFT)
        .isEqualTo(TOP_LEFT)
        .isEqualTo(new Position(0,0))
        .isNotEqualTo(new Position(0,1))
        .isNotEqualTo(new Position(1,0))
        .isNotEqualTo(FontStyle.BOLD)
        .isNotEqualTo(null);    
  }
  
  @Test 
  public void _hashCode()
  {
    assertThat(TOP_LEFT.hashCode())
        .isEqualTo(TOP_LEFT.hashCode())
        .isEqualTo(new Position(0,0).hashCode())
        .isNotEqualTo(new Position(0,1).hashCode())
        .isNotEqualTo(new Position(1,0).hashCode());
  }
  
  @Test
  public void _toString()
  {
    assertThat(TOP_LEFT.toString()).isEqualTo("Position[line=0, column=0]");
    assertThat(new Position(0,1).toString()).isEqualTo("Position[line=0, column=1]");
    assertThat(new Position(1,0).toString()).isEqualTo("Position[line=1, column=0]");
  }

  @Test
  public void line()
  {
    assertThat(TOP_LEFT.line()).isEqualTo(0);
    assertThat(new Position(1,0).line()).isEqualTo(1);
    assertThat(new Position(2,0).line()).isEqualTo(2);
  }
  
  @Test
  public void column()
  {
    assertThat(TOP_LEFT.column()).isEqualTo(0);
    assertThat(new Position(0,1).column()).isEqualTo(1);
    assertThat(new Position(0,2).column()).isEqualTo(2);
  }

  @Test
  public void rangeCheck()
  {
    assertThatCode(()-> new Position(0,0)).doesNotThrowAnyException();
    assertThatCode(()-> new Position(12345678,12354564)).doesNotThrowAnyException();
    assertThatThrownBy(()-> new Position(-1,0)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(()-> new Position(0,-1)).isInstanceOf(IllegalArgumentException.class);
  }
}
