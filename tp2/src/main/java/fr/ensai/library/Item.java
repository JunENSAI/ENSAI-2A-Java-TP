package fr.ensai.library;

public abstract class Item {
    protected String title;
    protected int year;
    protected int pageCount;

    public Item(String title, int year, int pageCount) {
        this.title = title;
        this.year = year;
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Returns the year of the item.
     *
     * @return the year of the item
     */
    public int getYear() {
        return year;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public abstract String toString();
}
