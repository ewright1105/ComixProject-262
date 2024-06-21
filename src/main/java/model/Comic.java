package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Comic {
    private String publisher;
    private String seriesTitle;
    private int volumeNum = 1;
    private String publicationDate;
    private String creators;
    private String principalCharacters;
    private String description;
    private BigDecimal value = BigDecimal.valueOf(0);
    private MarkedStatus markedStatus;
    // change to marks ?
    private String issueNumString;
    private String fullTitle;

    public Comic(String publisher, String seriesTitle, int volumeNum, String issueNumString, String publicationDate) {
        this.publisher = publisher;
        this.seriesTitle = seriesTitle;
        this.volumeNum = volumeNum;
        this.issueNumString = issueNumString;
        this.publicationDate = publicationDate;
        this.markedStatus = new MarkedStatus(.10);
    }

    public Comic(String publisher, String seriesTitle, int volumeNum, String issueNum, String publicationDate,
            String creators, String fullTitle) {
        this.publisher = publisher;
        this.seriesTitle = seriesTitle;
        this.volumeNum = volumeNum;
        this.issueNumString = issueNum;
        this.publicationDate = publicationDate;
        this.creators = creators;
        this.fullTitle = fullTitle;
        this.markedStatus = new MarkedStatus(.10);
    }

    public Comic(String publisher, String seriesTitle, int volumeNum, String issueNumString, String publicationDate,
            String creators,
            String principalCharacters, String description, BigDecimal value) {
        this.publisher = publisher;
        this.seriesTitle = seriesTitle;
        this.volumeNum = volumeNum;
        this.issueNumString = issueNumString;
        this.publicationDate = publicationDate;
        this.creators = creators;
        this.principalCharacters = principalCharacters;
        this.description = description;
        this.markedStatus = new MarkedStatus(value.doubleValue());
        this.value = value;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public int getVolumeNum() {
        return volumeNum;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getCreators() {
        return creators;
    }

    public String getPrincipalCharacters() {
        return principalCharacters;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getComicValue() {
        return markedStatus.getValue();
    }

    public boolean getMarkedStatus() {
        return markedStatus.getStatus();
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public String getIssueNumString() {
        return issueNumString;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public void setVolumeNum(int volumeNum) {
        this.volumeNum = volumeNum;
    }

    public void setIssueNumString(String issueNumString) {
        this.issueNumString = issueNumString;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setCreators(String creators) {
        this.creators = creators;
    }

    public void setPrincipalCharacters(String principalCharacters) {
        this.principalCharacters = principalCharacters;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(BigDecimal value) {
        markedStatus.setBaseValue(value.doubleValue());
    }

    public void addMarks(int grade, boolean slabbed, String signature, boolean authenticated) {
        this.markedStatus.setGrade(grade);
        this.markedStatus.setSignature(signature);
        if (slabbed) {
            this.markedStatus.slabComic();
        } if (authenticated) {
            this.markedStatus.authenticateSignatures(1);
        }
    }

    public int getGrade(){
        return this.markedStatus.getGrade();
    }

    public List<String> getDetails() {
        List<String> details = new ArrayList<>();
        details.add(seriesTitle);
        details.add(fullTitle);
        details.add(issueNumString);
        details.add(publisher);
        details.add(publicationDate);
        details.add(creators);
        return details;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Comic) {
            Comic other = (Comic) obj;
            return getSeriesTitle().equals(other.getSeriesTitle())
                    && getVolumeNum() == other.getVolumeNum()
                    && getIssueNumString().equals(other.getIssueNumString())
                    && getPublicationDate().equals(other.getPublicationDate());
        }
        return false;
    }

    @Override
    public String toString() {
        BigDecimal value = markedStatus.getValue();
        if (value.intValue() != 0) {
            return seriesTitle + "," + volumeNum + "," + issueNumString + "," + publisher + ","
                    + publicationDate + "," + value + ".";
        } else {
            return seriesTitle + "," + volumeNum + "," + issueNumString + "," + publisher + ","
                    + publicationDate + ".";
        }
    }

}