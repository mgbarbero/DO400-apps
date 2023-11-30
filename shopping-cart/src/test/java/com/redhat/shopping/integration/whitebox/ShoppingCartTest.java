package com.redhat.shopping.integration.whitebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.inject.Inject;

import com.redhat.shopping.cart.CartService;
import com.redhat.shopping.catalog.ProductNotFoundInCatalogException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ShoppingCartTest {
    @Inject
    CartService cartService;

    @BeforeEach
    void cleanCart(){
        this.cartService.clear();
    }

    @Test
    void addingNonExistingProductCatalogRaisesAnException(){
        assertThrows(ProductNotFoundInCatalogException.class,
         ()-> this.cartService.addProduct(999, 10));
    }

    @Test
    void addingNonExistingProductCartTheTotalItemsMatchTheInitialQuantity()
       throws ProductNotFoundInCatalogException {
         this.cartService.addProduct(1, 10);
         assertEquals(10, this.cartService.totalItems());  
       }

    @Test
    void addingProductThatIsInTheCartTheTotalItemsMatchTheSumOfQuantities()
       throws ProductNotFoundInCatalogException {
         this.cartService.addProduct(1, 10);
         this.cartService.addProduct(1, 100);

         assertEquals(110, this.cartService.totalItems());  
       }
}
