package ch.rweiss.terminal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import ch.rweiss.terminal.internal.EscCodeParser;

public class TestEscCode
{
  private static final EscCode ESC_OA = EscCode.esc("OA");
  private static final EscCode SGR_1 = EscCode.sgr(1);
  private static final EscCode CSI_B_123 = EscCode.csi('B', 1, 2, 3);
  private static final EscCode CSI_B = EscCode.csi('B');
  private static final EscCode CSI_A = EscCode.csi('A');

  @Test
  public void equals()
  {
    assertThat(CSI_A)
        .isEqualTo(CSI_A)
        .isEqualTo(EscCode.csi('A'))
        .isNotEqualTo(CSI_B)
        .isNotEqualTo(CSI_B_123)
        .isNotEqualTo(SGR_1)
        .isNotEqualTo(ESC_OA)
        .isNotEqualTo(FontStyle.BOLD)
        .isNotEqualTo(null);    
  }
  
  @Test 
  public void _hashCode()
  {
    assertThat(CSI_A.hashCode())
        .isEqualTo(CSI_A.hashCode())
        .isEqualTo(EscCode.csi('A').hashCode())
        .isNotEqualTo(CSI_B.hashCode())
        .isNotEqualTo(CSI_B_123.hashCode())
        .isNotEqualTo(SGR_1.hashCode())
        .isNotEqualTo(ESC_OA.hashCode());
  }
  
  @Test
  public void _toString()
  {
    assertThat(CSI_A.toString()).isEqualTo("ESC [ A");
    assertThat(CSI_B.toString()).isEqualTo("ESC [ B");
    assertThat(CSI_B_123.toString()).isEqualTo("ESC [ 1 ; 2 ; 3 B");
    assertThat(SGR_1.toString()).isEqualTo("ESC [ 1 m");
    assertThat(ESC_OA.toString()).isEqualTo("ESC O A");
  }
  
  @Test
  public void escCode()
  {
    assertThat(CSI_A.escCode()).isEqualTo(EscCodeParser.ESCAPE+"[A");
    assertThat(CSI_B.escCode()).isEqualTo(EscCodeParser.ESCAPE+"[B");
    assertThat(CSI_B_123.escCode()).isEqualTo(EscCodeParser.ESCAPE+"[1;2;3B");
    assertThat(SGR_1.escCode()).isEqualTo(EscCodeParser.ESCAPE+"[1m");
    assertThat(ESC_OA.escCode()).isEqualTo(EscCodeParser.ESCAPE+"OA");
  }
  
  @Test
  public void isCsi()
  {
    assertThat(CSI_A.isCsi()).isTrue();
    assertThat(CSI_B.isCsi()).isTrue();
    assertThat(CSI_B_123.isCsi()).isTrue();
    assertThat(SGR_1.isCsi()).isTrue();
    assertThat(ESC_OA.isCsi()).isFalse();
  }

  @Test
  public void isSgr()
  {
    assertThat(CSI_A.isSgr()).isFalse();
    assertThat(CSI_B.isSgr()).isFalse();
    assertThat(CSI_B_123.isSgr()).isFalse();
    assertThat(SGR_1.isSgr()).isTrue();
    assertThat(ESC_OA.isSgr()).isFalse();
  }
  
  @Test
  public void csiCommand()
  {
    assertThat(CSI_A.csiCommand()).isEqualTo('A');
    assertThat(CSI_B.csiCommand()).isEqualTo('B');
    assertThat(CSI_B_123.csiCommand()).isEqualTo('B');
    assertThat(SGR_1.csiCommand()).isEqualTo('m');
    assertThatThrownBy(() -> ESC_OA.csiCommand()).isInstanceOf(IllegalStateException.class);
  }    

  @Test
  public void csiArguments()
  {
    assertThat(CSI_A.csiArguments()).isEmpty();
    assertThat(CSI_B.csiArguments()).isEmpty();
    assertThat(CSI_B_123.csiArguments()).containsExactly(1,2,3);
    assertThat(SGR_1.csiArguments()).containsExactly(1);
    assertThatThrownBy(() -> ESC_OA.csiArguments()).isInstanceOf(IllegalStateException.class);
  }    

}
