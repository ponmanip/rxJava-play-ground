package simple.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Ponmani Palanisamy on 11/08/17.
 */
@Getter
@Setter
@Slf4j
public class Command {
    public Command(int id) {
        this.id = id;
    }

    public int getId() {
       // log.info("hashcode: "+this.hashCode());
        return id;
    }

    private int id;
    private boolean subOne;
    private boolean subTwo;

    public void setPayload(List<String> payload) {
        this.payload = payload;
        //log.info("Completed command with : "+payload + " for hashcode: "+this.hashCode());
    }

    private List<String> payload;
}
