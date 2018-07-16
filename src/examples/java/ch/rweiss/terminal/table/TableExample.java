package ch.rweiss.terminal.table;

import java.util.Arrays;
import java.util.Locale;

public class TableExample
{
  public static void main(String[] args)
  {
    Table<Locale> table = new Table<>();
    table.addColumn(table.createColumn("Locale", 40).withMinWidth(10).withTextProvider(Locale::toString).toColumn());
    table.addColumn(table.createColumn("Country", 20).withMinWidth(10).withTextProvider(Locale::getCountry).toColumn());
    table.addColumn(table.createColumn("Language", 20).withMinWidth(10).withTextProvider(Locale::getLanguage).toColumn());
    
    table.setRows(Arrays.asList(Locale.getAvailableLocales()));
    
    table.print();
  }
}
