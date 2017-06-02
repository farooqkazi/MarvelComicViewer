package Model;

import java.util.List;

/**
 * Created by sidd on 02/06/17.
 */

public class Comic {
    private int id;
    private String title;
    private int pageCount;
    private String description;
    private List<CreatorList> creators;
    private ComicPrice[] prices;

}
