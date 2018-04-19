package ch.rweiss.terminal.table;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

import ch.rweiss.check.Check;
import ch.rweiss.terminal.AnsiTerminal;
import ch.rweiss.terminal.Style;
import ch.rweiss.terminal.StyledText;

public class Column<R,V>
{
  private final StyledText title;
  private int width;
  private ColumnLayout layout;
  private final AbbreviateStyle abbreviateStyle;
  private final Function<R, V> valueProvider;
  private final Function<V, StyledText> styledTextProvider;
  private final Comparator<V> sorter;
  private final AnsiTerminal term = AnsiTerminal.get();

  private Column(ColumnBuilder<R,V> builder)
  {
    this.title = new StyledText(builder.title, builder.titleStyle);
    this.layout = builder.layout;
    this.width = layout.getPreferredWidth();
    this.abbreviateStyle = builder.abbreviateStyle;
    this.valueProvider = builder.valueProvider;
    this.styledTextProvider = ensureStyledTextProvider(builder);
    if (builder.sorter == null)
    {
      sorter = this::defaultSorter;
    }
    else
    {
      sorter = builder.sorter;
    }
  }

  private Function<V, StyledText> ensureStyledTextProvider(ColumnBuilder<R, V> builder)
  {
    if (builder.styledTextProvider != null)
    {
      return builder.styledTextProvider;
    }
    Function<V, String> textProvider = builder.textProvider != null ? builder.textProvider : Objects::toString;
    Style cellStyle = builder.cellStyle;
    if (cellStyle != null)
    {
      return value -> new StyledText(textProvider.apply(value), cellStyle);
    }
    return  value -> new StyledText(textProvider.apply(value));
  }

  String getTitle()
  {
    return title.toString();
  }
  
  ColumnLayout getLayout()
  {
    return layout;
  }
  
  void setWidth(int width)
  {
    this.width = width;
  }

  boolean printCell(R row, int line)
  {
    if (line > 0 && abbreviateStyle != AbbreviateStyle.NONE)
    {
      term.reset();
      term.write(fillWithWhitespaces(0));
      return false;
    }
    V value = valueProvider.apply(row);
    StyledText text = styledTextProvider.apply(value);
    StyledText lineText = trimToWidthAndLine(text, line);
    term.write(lineText);
    term.reset();
    term.write(fillWithWhitespaces(lineText.length()));
    return abbreviateStyle == AbbreviateStyle.NONE && 
           text.length() > (line+1)*width;
  }

  void printTitle()
  {
    StyledText trimmedTitle = trimmedTitle();
    term.write(trimmedTitle);
    term.reset();
    term.write(fillWithWhitespaces(trimmedTitle.length()));
  }
 
  private StyledText trimmedTitle()
  {
    if (title.length() < width)
    {
      return title;
    }
    return abbreviateRightWithDots(title);
  }

  private StyledText trimToWidthAndLine(StyledText text, int line)
  {
    if (text.length() < width)
    {
      return text;
    }
    switch(abbreviateStyle)
    {
      case NONE:
        return trimToLine(text, line);
      case LEFT:
        return abbreviateLeft(text);
      case RIGHT:
        return abbreviateRight(text);
      case LEFT_WITH_DOTS:
        return abbreviateLeftWithDots(text);
      case RIGHT_WITH_DOTS:
        return abbreviateRightWithDots(text);
      default:
        throw new IllegalStateException("Unknown AbbreviateStyle " + abbreviateStyle);
    }
  }

  private StyledText trimToLine(StyledText text, int line)
  {
    StyledText lineText = text.sub(line*width, width);
    return lineText;
  }

  private StyledText abbreviateLeft(StyledText text)
  {
    StyledText abbreviatedText = text.right(width-1);
    return abbreviatedText;
  }

  private StyledText abbreviateRight(StyledText text)
  {
    StyledText abbreviatedText = text.left(width-1);
    return abbreviatedText;
  }

  private StyledText abbreviateLeftWithDots(StyledText text)
  {
    StyledText abbreviatedText = text.right(width-4);    
    abbreviatedText = abbreviatedText.appendLeft("...");
    return abbreviatedText;
  }

  private StyledText abbreviateRightWithDots(StyledText text)
  {
    StyledText abbreviatedText = text.left(width - 4);
    abbreviatedText = abbreviatedText.append("...");
    return abbreviatedText;
  }
  private String fillWithWhitespaces(int length)
  {
    StringBuilder builder = new StringBuilder();
    for (int pos = length; pos < width; pos++)
    {
      builder.append(' ');
    }
    return builder.toString();
  }
  
  public int compareValue(R row1, R row2)
  {
    V value1 = valueProvider.apply(row1);
    V value2 = valueProvider.apply(row2);
    return sorter.compare(value1, value2);
  }

  @SuppressWarnings("unchecked")
  private int defaultSorter(V value1, V value2)
  {
    if (value1 == null && value2 == null)
    {
      return 0;
    }
    if (value1 == null && value2 != null)
    {
      return -1;
    }
    if (value1 != null && value2 == null)
    {
      return 1;
    }
    if (value1 instanceof Comparable)
    {
      return ((Comparable<V>)value1).compareTo(value2);
    }
    String text1 = Objects.toString(styledTextProvider.apply(value1));
    String text2 = Objects.toString(styledTextProvider.apply(value2));
    return Comparator.<String>nullsFirst((t1, t2)-> t1.compareTo(t2))
        .compare(text1,  text2);
  }

  static <R,V> ColumnBuilder<R,V> create(String title, int width, Function<R, V> valueProvider)
  {
    return new ColumnBuilder<>(title, width, valueProvider);
  }

  static <R> ColumnBuilder<R,R> create(String title, int width)
  {
    return new ColumnBuilder<>(title, width, row -> row);
  }

  public static final class ColumnBuilder<R, V>
  {
    private String title;
    private ColumnLayout layout = new ColumnLayout();
    private Style titleStyle;
    private Function<R, V> valueProvider;
    private Function<V, String> textProvider;
    private Function<V, StyledText> styledTextProvider;
    private Comparator<V> sorter;
    private Style cellStyle;
    private AbbreviateStyle abbreviateStyle = AbbreviateStyle.RIGHT_WITH_DOTS;

    public ColumnBuilder(String title, int width, Function<R, V> valueProvider)
    {
      Check.parameter("title").withValue(title).isNotNull();
      Check.parameter("width").withValue(width).isPositive();
      Check.parameter("valueProvider").withValue(valueProvider).isNotNull();
      this.title = title;
      this.layout.setFixedWith(width);
      this.valueProvider = valueProvider;
    }
    
    @SuppressWarnings("hiding")
    public ColumnBuilder<R,V> withTextProvider(Function<V, String> textProvider)
    {
      Check.parameter("textProvider").withValue(textProvider).isNotNull();
      Check.state("styledTextProvider").withValue(styledTextProvider).isNull();
      this.textProvider = textProvider;
      return this;
    }
    
    @SuppressWarnings("hiding")
    public ColumnBuilder<R,V> withTitleStyle(Style titleStyle)
    {
      Check.parameter("titleStyle").withValue(titleStyle).isNotNull();
      this.titleStyle = titleStyle;
      return this;
    }

    public ColumnBuilder<R,V> withCellStyle(@SuppressWarnings("hiding") Style cellStyle)
    {
      Check.parameter("cellStyle").withValue(cellStyle).isNotNull();
      Check.state("styledTextProvider").withValue(styledTextProvider).isNull();
      
      this.cellStyle = cellStyle;
      return this;
    }
    
    public ColumnBuilder<R,V> withStyledTextProvider(@SuppressWarnings("hiding") Function<V, StyledText> styledTextProvider)
    {
      Check.parameter("styledTextProvider").withValue(styledTextProvider).isNotNull();
      Check.state("textProvider").withValue(textProvider).isNull();
      Check.state("cellStyle").withValue(cellStyle).isNull();
      this.styledTextProvider = styledTextProvider;
      return this;
    }
    
    public ColumnBuilder<R,V> withSorter(@SuppressWarnings("hiding") Comparator<V> sorter)
    {
      Check.parameter("sorter").withValue(sorter).isNotNull();
      this.sorter = sorter;
      return this;
    }

    public ColumnBuilder<R, V> multiLine()
    {
      withAbbreviateStyle(AbbreviateStyle.NONE);
      return this;
    }
    
    public ColumnBuilder<R, V> withAbbreviateStyle(@SuppressWarnings("hiding") AbbreviateStyle abbreviateStyle)
    {
      this.abbreviateStyle = abbreviateStyle;
      return this;
    }

    public Column<R,V> toColumn()
    {
      return new Column<>(this); 
    }

  }
}