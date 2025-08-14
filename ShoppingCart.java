// ShoppingCart.java
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    // カート内の合計金額を計算
    public BigDecimal calculateTotalPrice() {
        // Stream APIを使用して合計値を計算
        return products.stream()
                       .map(Product::getPrice) // 各Productからpriceを取得
                       .reduce(BigDecimal.ZERO, BigDecimal::add); // 全てのpriceを合計
    }

    // 割引戦略を適用して合計金額を計算
    public BigDecimal calculateDiscountedPrice(DiscountStrategy strategy) {
        BigDecimal totalPrice = calculateTotalPrice();
        // 渡された戦略（ラムダ式）を適用
        return strategy.applyDiscount(totalPrice);
    }

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(new Product("Laptop", new BigDecimal("1200.00")));
        cart.addProduct(new Product("Mouse", new BigDecimal("50.00")));
        cart.addProduct(new Product("Keyboard", new BigDecimal("150.00")));

        // --- 割引戦略の定義（ラムダ式を使用） ---

        // 10%割引
        DiscountStrategy tenPercentOff = (originalPrice) -> originalPrice.multiply(new BigDecimal("0.90"));

        // 300ドル以上の購入で50ドル割引
        DiscountStrategy over300DollarOff = (originalPrice) -> {
            if (originalPrice.compareTo(new BigDecimal("300.00")) > 0) {
                return originalPrice.subtract(new BigDecimal("50.00"));
            } else {
                return originalPrice;
            }
        };

        // --- 割引の適用と出力 ---

        BigDecimal originalPrice = cart.calculateTotalPrice();
        System.out.println("元の合計金額: $" + originalPrice);
        System.out.println("--------------------");

        // 10%割引を適用
        BigDecimal priceAfterTenPercentOff = cart.calculateDiscountedPrice(tenPercentOff);
        System.out.println("10%割引適用後の金額: $" + priceAfterTenPercentOff);
        
        // 300ドル以上購入で50ドル割引を適用
        BigDecimal priceAfterOver300Off = cart.calculateDiscountedPrice(over300DollarOff);
        System.out.println("300ドル以上購入で50ドル割引適用後の金額: $" + priceAfterOver300Off);
    }
}
