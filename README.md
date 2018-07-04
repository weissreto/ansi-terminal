# ansi-terminal [![Build Status](https://travis-ci.org/weissreto/ansi-terminal.svg?branch=master)](https://travis-ci.org/weissreto/ansi-terminal)
This library allows to write professional console applications in Java.

Major Features are:
* Change text foreground color
* Change text background color
* Move the cursor
* Clear the screen
* Evaluate the terminal window size
* Print information in columns that are dynamically adjusted (layouted) to the terminal window size
* Print to off screen buffer first and then update the terminal window from the buffer.
* Draw dots, lines, rectangles   

## Change text foreground color

```java
AnsiTerminal terminal = AnsiTerminal.get();
terminal.color().red().write("This text is red").newLine();
```

## Change text background color

```java
AnsiTerminal terminal = AnsiTerminal.get();
terminal.backgroundColor().red().write("This text has a red background").newLine();   
```

## Move the cursor

```java
AnsiTerminal terminal = AnsiTerminal.get();
terminal
  .cursor().up(5)
  .write("H")
  .cursor().down()
  .write("e")
  .cursor().down()
  .write("l")
  .cursor().down()
  .write("l")
  .cursor().down()
  .write("o");
```

## Clear the screen

```java
AnsiTerminal terminal = AnsiTerminal.get();
terminal.clear().screen();
terminal.write("This is an empty screen!");
```

## Evaluate the terminal window size

```java
AnsiTerminal terminal = AnsiTerminal.get();
String text = "Center of the screen";
Position maxScreenPosition = terminal.cursor().maxPosition();
terminal
    .cursor().position(maxScreenPosition.line()/2, (maxScreenPosition.column()-text.length())/2)
    .write(text);
```

## Print information in columns that are dynamically adjusted (layouted) to the terminal window size

```java
Table<Locale> table = new Table<>();
table.addColumn(table.createColumn("Locale", 40).withMinWidth(10).withTextProvider(Locale::toString).toColumn());
table.addColumn(table.createColumn("Country", 20).withMinWidth(10).withTextProvider(Locale::getCountry).toColumn());
table.addColumn(table.createColumn("Language", 20).withMinWidth(10).withTextProvider(Locale::getLanguage).toColumn());

table.setRows(Arrays.asList(Locale.getAvailableLocales()));

table.print();
```

## Print to off screen buffer

```java
AnsiTerminal terminal = AnsiTerminal.get();
terminal.offScreen().on();
try
{
  terminal.write("This is written to the off screen buffer.");
  terminal.write("It is not visible on the terminal screen until the method syncToScreen() is called.");
  terminal.offScreen().syncToScreen();
}
finally
{
  terminal.offScreen().off();
}
```

## Draw dots, lines, rectangles

```java
AnsiTerminal terminal = AnsiTerminal.get();

terminal.clear().screen();
Position position = terminal.cursor().maxPosition();

Rectangle rectangle = new Rectangle(Point.ORIGIN, position.column(), position.line());    
terminal.graphics().drawRectangle(rectangle);
```

