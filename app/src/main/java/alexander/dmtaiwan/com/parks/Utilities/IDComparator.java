package alexander.dmtaiwan.com.parks.Utilities;
import java.util.Comparator;

import alexander.dmtaiwan.com.parks.Models.Park;

/**
 * Created by lenovo on 11/6/2015.
 */
public class IDComparator implements Comparator<Park> {

    @Override
    public int compare(Park lhs, Park rhs) {
        return Integer.valueOf(lhs.get_id()) - Integer.valueOf(rhs.get_id());
    }
}
