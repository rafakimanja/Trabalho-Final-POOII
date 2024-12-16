package main.gui.models.Registro;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Conversor {

    public Timestamp toTimestamp(LocalDateTime valor){
        if(valor != null)return Timestamp.valueOf(valor);
        else return null;

    }

    public LocalDateTime toLocalDateTime(Timestamp valor){
        if(valor != null) return valor.toLocalDateTime();
        else return null;
    }
}
