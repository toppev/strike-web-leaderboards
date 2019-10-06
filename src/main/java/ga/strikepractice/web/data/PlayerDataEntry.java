package ga.strikepractice.web.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerDataEntry<T>{

    /**
     * The player's name
     */
    private final String name;

    private final T value;

}
