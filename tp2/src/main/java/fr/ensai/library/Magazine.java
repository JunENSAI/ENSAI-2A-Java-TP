package fr.ensai.library;

public class Magazine extends Item {
    private String issn;
    private int issueNumber;

    public Magazine(String issn, String title, int year, int pageCount, int issueNumber) {
        super(title, year, pageCount);
        this.issn = issn;
        this.issueNumber = issueNumber;
    }

    public String getIssn() {
        return issn;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public String toString() {
        return "Magazine: " + title + " Issue " + issueNumber +
               " (" + year + ", " + pageCount + " pages, ISSN: " + issn + ")";
    }
}
