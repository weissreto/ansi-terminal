package ch.weiss.terminal.table;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

import ch.weiss.check.Check;
import ch.weiss.terminal.AnsiTerminal;
import ch.weiss.terminal.Style;
import ch.weiss.terminal.StyledText;

public class Column<R,V>
{
  private final StyledText title;
  private final int width;
  private final Function<R, V> valueProvider;
  private final Function<V, StyledText> styledTextProvider;
  private final Comparator<V> sorter;
  private final AnsiTerminal term = AnsiTerminal.get();

  private Column(ColumnBuilder<R,V> builder)
  {
    this.title = new StyledText(builder.title, builder.titleStyle);
    this.width = builder.width;
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

  void printCell(R row)
  {
    V value = valueProvider.apply(row);
    StyledText text = styledTextProvider.apply(value);
    text = trimToWidth(text);
    term.write(text);
    term.reset();
    term.write(fillWithWhitespaces(text.length()));
  }

  void printTitle()
  {
    StyledText trimmedTitle = trimToWidth(title);
    term.write(trimmedTitle);
    term.reset();
    term.write(fillWithWhitespaces(trimmedTitle.length()));
  }
 
  private StyledText trimToWidth(StyledText text)
  {
    if (text.length() < width)
    {
      return text;
    }
    StyledText trimmedText = text.abbreviate(width - 4);
    trimmedText = trimmedText.append("...");
    return trimmedText;
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
    private int width;
    private Style titleStyle;
    private Function<R, V> valueProvider;
    private Function<V, String> textProvider;
    private Function<V, StyledText> styledTextProvider;
    private Comparator<V> sorter;
    private Style cellStyle;

    public ColumnBuilder(String title, int width, Function<R, V> valueProvider)
    {
      Check.parameter("title").withValue(title).isNotNull();
      Check.parameter("width").withValue(width).isPositive().isNotZero();
      Check.parameter("valueProvider").withValue(valueProvider).isNotNull();
      this.title = title;
      this.width = width;
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
    
    public Column<R,V> toColumn()
    {
      return new Column<>(this); 
    }
  }
}