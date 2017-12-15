package ch.weiss.terminal.table;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

import ch.weiss.check.Check;
import ch.weiss.terminal.AnsiTerminal;
import ch.weiss.terminal.Style;

public class Column<R,V>
{
  private final String title;
  private final int width;
  private final Style titleStyle;
  private final Function<R, V> valueProvider;
  private final Function<V, String> textProvider;
  private final Function<V, Style> cellStyler;
  private final Comparator<V> sorter;
  private final AnsiTerminal term = AnsiTerminal.get();

  private Column(ColumnBuilder<R,V> builder)
  {
    this.title = builder.title;
    this.width = builder.width;
    this.valueProvider = builder.valueProvider;
    this.titleStyle = builder.titleStyle;
    this.cellStyler = builder.cellStyler;
    this.textProvider = builder.textProvider;
    if (builder.sorter == null)
    {
      sorter = this::defaultSorter;
    }
    else
    {
      sorter = builder.sorter;
    }
  }

  String getTitle()
  {
    return title;
  }

  void printCell(R row)
  {
    V value = valueProvider.apply(row);
    if (cellStyler != null)
    {
      term.style(cellStyler.apply(value));
    }
    String valueStr = trimToWidth(textProvider.apply(value));
    term.write(valueStr);
    term.reset();
    term.write(whitespaces(valueStr));
  }

  void printTitle()
  {
    term.style(titleStyle);
    String trimmedTitle = trimToWidth(title);
    term.write(trimmedTitle);
    term.reset();
    term.write(whitespaces(trimmedTitle));
  }
 

  private String trimToWidth(String str)
  {
    if (str.length() < width-1)
    {
      return str;
    }
    String trimmedStr = str.substring(0, width - 4);
    trimmedStr = trimmedStr+"...";
    return trimmedStr;
  }

  private String whitespaces(String str)
  {
    StringBuilder builder = new StringBuilder();
    for (int pos = str.length(); pos < width; pos++)
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
    String text1 = textProvider.apply(value1);
    String text2 = textProvider.apply(value2);
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
    private Function<V, String> textProvider = Objects::toString;
    private Function<V, Style> cellStyler;
    private Comparator<V> sorter;

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

    public ColumnBuilder<R,V> withCellStyle(Style cellStyle)
    {
      Check.parameter("cellStyle").withValue(cellStyle).isNotNull();
      return withCellStyler(value -> cellStyle);
    }
    
    public ColumnBuilder<R,V> withCellStyler(@SuppressWarnings("hiding") Function<V, Style> cellStyler)
    {
      Check.parameter("cellStyler").withValue(cellStyler).isNotNull();
      this.cellStyler = cellStyler;
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