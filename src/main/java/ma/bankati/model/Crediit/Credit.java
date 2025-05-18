package ma.bankati.model.Crediit;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @Builder
public class Credit {

    public Long id;
    public Double mtCredit;
    public Long nbrMois;
    public String motif;
    public Status status;



    public Credit(Long id, Double mtCredit, Long nbrMois, String motif, Status status) {
        this.id = id;
        this.mtCredit = mtCredit;
        this.nbrMois = nbrMois;
        this.motif = motif;
        this.status = Status.valueOf(status.name());
    }
}
