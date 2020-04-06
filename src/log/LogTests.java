package log;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

class LoggerTest {
    @AfterEach
    void tearDown() {
        Logger.getDefaultLogSource().clear();
    }


    @Test
    void TestLogEntry() {
       LogEntry entry = new LogEntry(LogLevel.Trace, "Trace message");

       Assertions.assertEquals("Trace message", entry.getMessage());
       Assertions.assertEquals(LogLevel.Trace, entry.getLevel());
    }

    @Test
    void TestLoggerDebug() {
        Logger.getDefaultLogSource().all().iterator().remove();
        //Logger.getDefaultLogSource().all().iterator().remove();
        Logger.debug("logger debug works");
        System.out.println(Logger.getDefaultLogSource().all());
        Assertions.assertEquals("logger debug works", Logger.getDefaultLogSource().all().iterator().next().getMessage());
        Assertions.assertEquals(LogLevel.Debug, Logger.getDefaultLogSource().all().iterator().next().getLevel());

    }

    @Test
    void TestDefaultLogSource() {
        LogWindowSource source = Logger.getDefaultLogSource();
        source.append(LogLevel.Trace, "trace message");
        Assertions.assertEquals(LogLevel.Trace, source.all().iterator().next().getLevel());
        Assertions.assertEquals("trace message", source.all().iterator().next().getMessage());
        //source.all().iterator().next();
        //source.all().iterator().remove();
    }
}
