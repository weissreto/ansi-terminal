module ch.rweiss.terminal
{
  exports ch.rweiss.terminal;
  exports ch.rweiss.terminal.graphics;
  exports ch.rweiss.terminal.table;
  exports ch.rweiss.terminal.widget;
  
  requires transitive ch.rweiss.terminal.nativ;
  requires ch.rweiss.check;
}