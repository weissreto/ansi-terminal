package ch.weiss.terminal.table;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

import ch.weiss.check.Check;
import ch.weiss.terminal.AnsiTerminal;
import ch.weiss.terminal.Style;

public class Column
{
  private final String title;
  private final int width;
  private final Style titleStyle;
  private final Function<Object, Style> cellStyler;
  private final Function<Object, String> textProvider;
  private final Comparator<Object> sorter;
  private final AnsiTerminal term = AnsiTerminal.get();

  public static ColumnBuilder create(String title, int width)
  {
    return new ColumnBuilder(title, width);
  }

  private Column(ColumnBuilder builder)
  {
    this.title = builder.title;
    this.width = builder.width;
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

  void printCell(Object value)
  {
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
  
  public int compareValue(Object value1, Object value2)
  {
    return sorter.compare(value1, value2);
  }

  private int defaultSorter(Object value1, Object value2)
  {
    String text1 = textProvider.apply(value1);
    String text2 = textProvider.apply(value2);
    return Comparator.<String>nullsFirst((t1, t2)-> t1.compareTo(t2))
        .compare(text1,  text2);
  }
  
  
  public static final class ColumnBuilder
  {
    private String title;
    private int width;
    private Style titleStyle;
    private Function<Object, Style> cellStyler;
    private Function<Object, String> textProvider = Objects::toString;
    private Comparator<Object> sorter;

    public ColumnBuilder(String title, int width)
    {
      Check.parameter("title").withValue(title).isNotNull();
      Check.parameter("width").withValue(width).isPositive().isNotZero();
      this.title = title;
      this.width = width;
    }
    
    @SuppressWarnings("hiding")
    public ColumnBuilder withTextProvider(Function<Object, String> textProvider)
    {
      Check.parameter("textProvider").withValue(textProvider).isNotNull();
      this.textProvider = textProvider;
      return this;
    }
    
    @SuppressWarnings("hiding")
    public ColumnBuilder withTitleStyle(Style titleStyle)
    {
      Check.parameter("titleStyle").withValue(titleStyle).isNotNull();
      this.titleStyle = titleStyle;
      return this;
    }

    public ColumnBuilder withCellStyle(Style cellStyle)
    {
      Check.parameter("cellStyle").withValue(cellStyle).isNotNull();
      return withCellStyler(value -> cellStyle);
    }
    
    public ColumnBuilder withCellStyler(@SuppressWarnings("hiding") Function<Object, Style> cellStyler)
    {
      Check.parameter("cellStyler").withValue(cellStyler).isNotNull();
      this.cellStyler = cellStyler;
      return this;
    }
    
    public ColumnBuilder withSorter(@SuppressWarnings("hiding") Comparator<Object> sorter)
    {
      Check.parameter("sorter").withValue(sorter).isNotNull();
      this.sorter = sorter;
      return this;
    }
    
    public Column toColumn()
    {
      return new Column(this); 
    }
  }
}