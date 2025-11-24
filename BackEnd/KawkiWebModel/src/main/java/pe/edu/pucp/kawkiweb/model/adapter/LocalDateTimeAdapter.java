package pe.edu.pucp.kawkiweb.model.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Adaptador JAXB para permitir la conversión entre LocalDateTime y String
 * cuando se generan o consumen mensajes SOAP (XML).
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return (v != null) ? LocalDateTime.parse(v, FORMATTER) : null;
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return (v != null) ? v.format(FORMATTER) : null;
    }
}
