package ch.rweiss.terminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ch.rweiss.check.Check;

public class StyledText
{
  private final List<Part> parts;
  
  public StyledText(String text)
  {
    this(text, null);
  }
  
  public StyledText(String text, Style style)
  {
    Check.parameter("text").withValue(text).isNotNull();
    parts = Arrays.asList(new Part(text, style));
  }
  
  private StyledText(List<Part> parts)
  {
    Check.parameter("parts").withValue(parts).isNotNull();
    Check.parameter("parts.size()").withValue(parts.size()).isGreaterOrEqualTo(1);
    this.parts = Collections.unmodifiableList(parts);
  }

  public StyledText append(String text)
  {
    return append(new StyledText(text, lastPart().style));
  }
  
  public StyledText append(String text, Style style)
  {
    return append(new StyledText(text, style));
  }
  
  public StyledText append(StyledText text)
  {
    Check.parameter("text").withValue(text).isNotNull();
    
    List<Part> mergedParts = new ArrayList<>();    
    Part lastPart = lastPart();
    Part firstPart = text.firstPart();
    if (Objects.equals(lastPart.style, firstPart.style))
    {
      mergedParts.addAll(parts.subList(0, parts.size()-1));
      Part mergedPart = new Part(lastPart.text + firstPart.text, lastPart.style);
      mergedParts.add(mergedPart);
      mergedParts.addAll(text.parts.subList(1, text.parts.size()));
    }
    else
    {  
      mergedParts.addAll(parts);
      mergedParts.addAll(text.parts);
    }  
    return new StyledText(mergedParts);
  }
  
  public StyledText abbreviate(int length)
  {
    Check.parameter("length").withValue(length).isGreaterOrEqualTo(0);
    
    if (length() <= length)
    {
      return this;
    }
    if (length == 0)
    {
      return new StyledText("", firstPart().style());
    }
    List<Part> newParts = new ArrayList<>();
    int lengthSum = 0;
    for (Part part : parts)      
    {
      if (lengthSum + part.text.length() >= length)
      {
        int missingLength = length - lengthSum;
        if (missingLength > 0)
        {
          String abbreviatedText = part.text.substring(0, missingLength);
          Part abbreviatedPart = new Part(abbreviatedText, part.style);
          newParts.add(abbreviatedPart);
        }
        return new StyledText(newParts);
      }
      newParts.add(part);
      lengthSum = lengthSum + part.text.length();
    }
    return new StyledText(newParts);
  }
  
  public int length()
  {
    return parts
        .stream()
        .mapToInt(part -> part.text.length())
        .sum();
  }

  private Part firstPart()
  {
    return parts.get(0);
  }

  private Part lastPart()
  {
    return parts.get(parts.size()-1);
  }
  
  List<Part> parts()
  {
    return parts;
  }

  static final class Part
  {
    private final String text;
    private final Style style;
    
    private Part(String text, Style style)
    {
      this.text = text;
      this.style = style;
    }
    
    String text()
    {
      return text;
    }
    
    Style style()
    {
      return style;
    }
  }   
}
