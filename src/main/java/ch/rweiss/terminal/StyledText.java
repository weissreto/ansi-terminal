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
  
  public StyledText appendLeft(String text)
  {
    if (length() == 0)
    {
      return new StyledText(text, firstPart().style());
    }    
    return new StyledText(text, firstPart().style()).append(this); 
  }

  public StyledText sub(int startPos, int length)
  {
    Check.parameter("startPos").withValue(startPos).isGreaterOrEqualTo(0).isLessThan(length());
    Check.parameter("length").withValue(length).isGreaterOrEqualTo(0);
    
    if (startPos == 0 && length >= length())
    {
      return this;
    }
    List<Part> subParts = new ArrayList<>();
    int partStartPos = 0;
    int totalLength = 0;
    int endPos = startPos + length;
    for (Part part : parts)
    {
      int partLength = part.length();
      int partEndPos = partStartPos+partLength;
      if (partStartPos <= startPos && partEndPos > startPos)
      {
        Part startPart = part.subtext(startPos-partStartPos, length);
        totalLength = startPart.length();
        subParts.add(startPart);        
      }
      else if (partStartPos > startPos && partEndPos < endPos)
      {
        subParts.add(part);
        totalLength = part.length();
      }
      else if (partStartPos > startPos && partEndPos >= endPos)
      {
        Part endPart = part.subtext(0, partLength - (partEndPos- endPos));
        subParts.add(endPart);
        totalLength = endPart.length();
      }
      if (totalLength >= length && subParts.size() > 0)
      {
        break;
      }
      partStartPos += part.length();
    }
    return new StyledText(subParts);
  }   

  public StyledText left(int length)
  {
    return sub(0, length);
  }
  
  public StyledText right(int length)
  {
    int startPos = length()-length;
    startPos = Math.max(0, startPos);
    startPos = Math.min(length()-1, startPos);
    return sub(startPos, length);
  }
  
  public int length()
  {
    return parts
        .stream()
        .mapToInt(part -> part.text.length())
        .sum();
  }
  
  public int indexOf(int startPos, char character)
  {
    Check.parameter("startPos").withValue(startPos).isPositive().isLessThan(length());
    for (int pos = startPos; pos < length(); pos++)
    {
      if (charAt(pos) == character)
      {
        return pos;
      }
    }
    return -1;
  }

  private char charAt(int pos)
  {
    Check.parameter("pos").withValue(pos).isPositive().isLessThan(length());
    int startPos = 0;
    for (Part part : parts)
    {
      if (pos >= startPos && pos < startPos + part.length())
      {
        return part.text().charAt(pos-startPos);
      }
      startPos += part.length();
    }
    throw new IllegalStateException("There is a bug in the code");
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
    
    public Part subtext(int startPos, int length)
    {
      if (startPos == 0  && length > length())
      {
        return this;
      }
      if (startPos + length > length())
      {
        length = length()-startPos;
      }
      int endPos = startPos+length; 
      return new Part(text.substring(startPos, endPos), style);
    }

    public int length()
    {
      return text.length();
    }

    String text()
    {
      return text;
    }
    
    Style style()
    {
      return style;
    }
    
    @Override
    public String toString()
    {
      return text;
    }
  }
}
