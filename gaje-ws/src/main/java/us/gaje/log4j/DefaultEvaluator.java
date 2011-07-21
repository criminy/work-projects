package us.gaje.log4j;


import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;
public class DefaultEvaluator implements TriggeringEventEvaluator {

public boolean isTriggeringEvent(LoggingEvent event) { return true; }

} 