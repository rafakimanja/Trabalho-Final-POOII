package pacote_vaga;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Conversor {

    public Timestamp toTimestamp(LocalDateTime valor){
        return Timestamp.valueOf(valor);
    }

    public LocalDateTime toLocalDateTime(Timestamp valor){
        return valor.toLocalDateTime();
    }
}
