package geektime.spring.springbucks.entity;

public class Coffee {
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    private String name;
    private Price price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
