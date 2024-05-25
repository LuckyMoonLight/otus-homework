package dto;


import lombok.ToString;

@ToString
public class Product {
    private Long id;
    private String title;
    private String description;
    private Long cost;
    private Long weight;
    private Long wight;
    private Long length;
    private Long height;
    public static Bulder bulder() { return new Bulder(); }

    private Product(Bulder bulder) {
        id = bulder.id;
        title = bulder.title;
        description = bulder.description;
        cost = bulder.cost;
        weight = bulder.weight;
        wight = bulder.wight;
        length = bulder.length;
        height = bulder.height;
    }

    public static final class Bulder {
        private Long id;
        private String title;
        private String description;
        private Long cost;
        private Long weight;
        private Long wight;
        private Long length;
        private Long height;


        public Product build() {
            return new Product(this);
        }

        public Bulder id(Long id) { this.id = id; return this; }
        public Bulder title(String title) { this.title = title; return this; }
        public Bulder description(String description) {this.description = description; return this;}
        public Bulder cost(Long cost) { this.cost = cost; return this; }
        public Bulder weight(Long weight) { this.weight = weight; return this; }
        public Bulder wight(Long wight) { this.wight = wight; return this; }
        public Bulder length(Long length) { this.length = length; return this; }
        public Bulder height(Long height) { this.height = height; return this; }

    }
}
