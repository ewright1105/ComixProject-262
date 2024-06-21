package model.searchsort;

import model.Comic;
import model.searchsort.ComicSorter;

import java.util.Comparator;
import java.util.List;

public class SortByDefault implements ComicSorter, Comparator<Comic>{

    @Override
    public void sort(List<Comic> collection) {
        collection.sort(this);
    }

    @Override
    public int compare(Comic o1, Comic o2) {
        int issueNum1 = Integer.parseInt(o1.getIssueNumString());
        int issueNum2 = Integer.parseInt(o2.getIssueNumString());
        if (o1.getSeriesTitle().equals(o2.getSeriesTitle())){
            if (o1.getVolumeNum() == o2.getVolumeNum()){
                if (issueNum1 == issueNum2) {
                    return 0;
                }
                return issueNum1 - issueNum2;
            }
            return o1.getVolumeNum() - o2.getVolumeNum();
        }
        return o1.getSeriesTitle().compareTo(o2.getSeriesTitle());
        
    }
    
}