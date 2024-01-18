import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuctionList {
    private int total;
    private List<AuctionItem> prices;
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AuctionItem {
        private int amount;
        private long price;
        private Date time;
        private AdditionalInfo additional;
        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public static class AdditionalInfo {
            private List<String> bonus_properties;
            private double md_k;
            private double ndmg;
            private int it_transf_count;
            private int qlt;
            private int ptn;
            private double stats_random;
            private double upgrade_bonus;
            private long spawn_time;
        }
    }
}