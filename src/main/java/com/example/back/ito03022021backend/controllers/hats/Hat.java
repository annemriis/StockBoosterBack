package com.example.back.ito03022021backend.controllers.hats;

public class Hat {
    private Long id;
    private String size;
    private String colour;
    private String price;
    private String style;

    @Override
    public String toString() {
        return "Hat{" +
                "id=" + id +
                ", size='" + size + '\'' +
                ", colour='" + colour + '\'' +
                ", price='" + price + '\'' +
                ", style='" + style + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
