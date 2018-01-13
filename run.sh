#!/bin/bash

#MAIN_CLASS=ch.rweiss.terminal.ManualTestColor
#MAIN_CLASS=ch.rweiss.terminal.ManualTestCursorNavigation
#MAIN_CLASS=ch.rweiss.terminal.ManualTestCursorPosition
#MAIN_CLASS=ch.rweiss.terminal.ManualTestCursorVisibility
#MAIN_CLASS=ch.rweiss.terminal.ManualTestFontStyles
MAIN_CLASS=ch.rweiss.terminal.ManualTestGetCursorPosition
#MAIN_CLASS=ch.rweiss.terminal.ManualTestLine
#MAIN_CLASS=ch.rweiss.terminal.ManualTestLine2
#MAIN_CLASS=ch.rweiss.terminal.ManualTestRectangle
#MAIN_CLASS=ch.rweiss.terminal.ManualTestStyles
#MAIN_CLASS=ch.rweiss.terminal.ManualTestText

mvn exec:java -Dexec.mainClass=$MAIN_CLASS -Dexec.arguments="$*" -Dexec.classpathScope=test
