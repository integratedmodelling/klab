package deleteme;

class Product implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6473453956188122172L;
    String name;
    String image;
    String[] regions;
    int numberSold;

    public Product(String name, String image, String[] regions, int numberSold) {
        this.name = name;
        this.image = image;
        this.regions = regions;
        this.numberSold = numberSold;
    }
}
