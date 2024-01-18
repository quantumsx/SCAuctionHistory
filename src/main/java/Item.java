import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Item {
    private String itemName;
    private long price;
    private int ptn;
    private int qlt;
    private Date time;

    public Item(String itemName, long price, int qlt, int ptn, Date time) {
        this.itemName = itemName;
        this.price = price;
        this.qlt = qlt;
        this.ptn = ptn;
        this.time = time;
        }
    }