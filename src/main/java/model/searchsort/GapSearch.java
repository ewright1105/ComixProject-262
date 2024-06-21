package model.searchsort;

import model.Comic;

import java.util.ArrayList;
import java.util.List;

public class GapSearch implements ComicSearcher{
    @Override
    public List<Comic> search(List<Comic> comics) {
        List<Comic> results = new ArrayList<>();
        ComicSorter sorter = new SortByDefault();
        sorter.sort(comics);
        int idx = 0;
        while (idx < comics.size() - 1){
            List<Comic> volList = getVolumeList(comics, idx);
            if (hasGap(volList)) {
                results.addAll(volList);
            }
            idx += volList.size();
        }
        return results;
    }

    /*
    Checks if there is a gap within a list of comics (of the same series title and volume)
     */
    private boolean hasGap(List<Comic> comics){
        if (comics.size() < 10){ // Change to 10 before full release
            return false;
        }
        int expectedIssue = Integer.parseInt(comics.get(0).getIssueNumString());
        int gapSize = 0;
        for (Comic issue : comics){
            if (expectedIssue != Integer.parseInt(issue.getIssueNumString())){
                gapSize++;
                expectedIssue++;
            }
            expectedIssue++;
        }
        return gapSize <= 2 && gapSize > 0;
    }
    /*
    Filters the passed list based on the series title and volume number of element at the passed index
     */
    private List<Comic> getVolumeList(List<Comic> comics, int index) {
        List<Comic> volumeList = comics.stream()
                .filter(e -> verifySeriesTitle(comics.get(index).getSeriesTitle(), e.getSeriesTitle()))
                .filter(e -> verifyVolumeNum(comics.get(index).getVolumeNum(), e.getVolumeNum()))
                .toList();
        return volumeList;
    }
    /*
    Checks if two series titles are the same. Used as a function parameter for stream().filter()
     */
    private boolean verifySeriesTitle(String st1, String st2){
        return st1.equals(st2);
    }
    /*
    Checks if two volume numbers are the same. Used as a function parameter for stream().filter()
     */
    private boolean verifyVolumeNum(int vn1, int vn2){
        return vn1 == vn2;
    }
}
