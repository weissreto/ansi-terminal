SET JAVA_HOME=c:\program files\java\jdk-11
SET MAVEN=C:\Tools\maven\apache-maven-3.5.2\bin\mvn

REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestColor
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestCursorNavigation
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestCursorPosition
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestCursorVisibility
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestFontStyles
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestGetCursorPosition
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestLine
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestLine2
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestRectangle
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestStyles
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestText
REM SET MAIN_CLASS=ch.rweiss.terminal.ManualTestOffScreen
SET MAIN_CLASS=ch.rweiss.terminal.ManualTestInput

%MAVEN% exec:java -Dexec.mainClass=%MAIN_CLASS% -Dexec.arguments="%*" -Dexec.classpathScope=test