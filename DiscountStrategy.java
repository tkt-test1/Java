// DiscountStrategy.java
import java.math.BigDecimal;

/**
 * 割引計算の戦略を定義するインターフェース
 * ラムダ式として実装されることを想定
 */
@FunctionalInterface
public interface DiscountStrategy {
    BigDecimal applyDiscount(BigDecimal originalPrice);
}
