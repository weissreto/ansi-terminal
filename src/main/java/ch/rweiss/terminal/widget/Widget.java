package ch.rweiss.terminal.widget;

import ch.rweiss.terminal.graphics.Graphics;
import ch.rweiss.terminal.graphics.Rectangle;

public abstract class Widget
{
  private Rectangle bounds;
  
  public Rectangle bounds()
  {
    return bounds;
  }
  
  public void bounds(@SuppressWarnings("hiding") Rectangle bounds)
  {
    this.bounds = bounds;
  }
  
  public abstract void paint(Graphics graphics);
}
