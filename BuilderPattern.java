public class BuilderPattern {
   public static void main(String[] args) {
      CarDirector director = new CarDirector();
      CarBuilder builder = new CarBuilder();
      director.buildLambo(builder).model("aventador").prop3(21);
      Car car = builder.build();

      builder.reset();

      director.buildLambo(builder);
      Car car2 = builder.build();

      System.out.println(String.format("%s %s %s", car.getBrand(), car.getModel(), car.getProp3()));
      System.out.println(String.format("%s %s %s", car2.getBrand(), car2.getModel(), car2.getProp3()));

   }
}

class CarDirector {
   public CarBuilder buildLambo(CarBuilder builder) {
      return builder
            .brand("Lamborghini")
            .prop3(10);
   }
}

class CarBuilder {
   private String brand;
   private String model;
   private Integer prop3;
   private String prop4;

   public CarBuilder brand(String brand) {
      this.brand = brand;
      return this;

   }

   public CarBuilder model(String model) {
      this.model = model;
      return this;
   }

   public CarBuilder prop3(Integer prop3) {
      this.prop3 = prop3;
      return this;
   }

   public CarBuilder prop4(String prop4) {
      this.prop4 = prop4;
      return this;
   }

   public CarBuilder reset() {
      this.brand = null;
      this.model = null;
      this.prop3 = null;
      this.prop4 = null;
      return this;
   }

   public Car build() {
      return new Car(brand, model, prop3, prop4);
   }
}

class Car {
   private String brand;
   private String model;
   private Integer prop3;
   private String prop4;

   Car(String brand, String model, Integer prop3, String prop4) {
      this.brand = brand;
      this.model = model;
      this.prop3 = prop3;
      this.prop4 = prop4;
   }

   public String getBrand() {
      return brand;
   }

   public String getModel() {
      return model;
   }

   public Integer getProp3() {
      return prop3;
   }

   public String getProp4() {
      return prop4;
   }
}
