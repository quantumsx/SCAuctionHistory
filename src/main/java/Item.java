import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class Item {
    private String itemName;
    private long price;
    private int ptn;
    private String qlt;
    private Date time;

    public Item(String itemName, long price, String qlt, int ptn, Date time) {
        this.itemName = itemName;
        this.price = price;
        this.qlt = qlt;
        this.ptn = ptn;
        this.time = time;
        }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM HH:mm:ss");
        return formatter.format(time);
    }
    }