package com.example;



import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;



public class AppTest extends SetUp {
    
	public static final String url = "https://www.gittigidiyor.com/";
    public static final String loginurl = "https://www.gittigidiyor.com/uye-girisi";
    String ProductPriceString;
    
	@Test
	public void test() throws InterruptedException {
		
		//hedef sayfa açılır
		driver.get(url);
       
        Thread.sleep(1000);

        //url kontrol
        if(driver.getCurrentUrl().endsWith("gittigidiyor.com/")){
            System.out.println("url is true");
        }
        else {
            System.out.println("url is false");
        }
        //sonraki method çağrılır
        login();
        
	}
        public void login() throws InterruptedException{
        //loginbilgilerigirilir
        driver.navigate().to(loginurl);
        driver.findElement(By.id("L-UserNameField")).sendKeys("denemetest11111@gmail.com");
        driver.findElement(By.id("L-PasswordField")).sendKeys("deneme1");
        driver.findElement(By.id("gg-login-enter")).click();
        Thread.sleep(500000);
        //loginkontrol
        boolean profile = driver.findElement(By.xpath("//div[contains(@title,'Hesabım')]")).isDisplayed();
        if(profile==true) {
        	System.out.println("login succesful");
        	
        }
        else {
        	System.out.println("login failed");
        	}
       
        //sonraki method
        search();
       }
        public void search() throws InterruptedException{
        //bilgisayar kelimesi aratılır
        driver.findElement(By.cssSelector("input[name='k']")).sendKeys("Bilgisayar");
        driver.findElement(By.cssSelector("button[data-cy=search-find-button]")).click();
        
        //aşağıya inilir
        Thread.sleep(3000);
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        Thread.sleep(500);
        //2.sayfaya gidilir
        driver.findElement(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li[2]/a")).click();
        //url kontrol
        if(driver.getCurrentUrl().endsWith("www.gittigidiyor.com/arama/?k=Bilgisayar&sf=2")){
            System.out.println("url is true");
        }
        else {
            System.out.println("url is false");
        }   
        addProduct();
        }
        public void addProduct() throws InterruptedException{
        	//itemler listeye atılır
            WebElement productUL = driver.findElement(By.xpath("//*[@id=\"best-match-right\"]/div[3]/div[2]/ul"));
            List<WebElement> productList= productUL.findElements(By.tagName("a"));
            //random sayı oluşturulur
            Random random = new Random();
            int num = random.nextInt(productList.size());
            
           //kontrol
            System.out.println(num);
           

            
            int i = 1;
            //random seçmek için for döngüsü
            for (WebElement li : productList) {
                if (i==num){ //
                    li.click();
                    System.out.println("Radomly  "+num+"is selected and go to the product link.");
                    break;

                }
                i+=1;
            }

            Thread.sleep(1000);
            
            //sonraki method
            addToBasket();
        }
        public void addToBasket() throws InterruptedException{
        //indirimli indirimsiz fiyatlar tanımlanır
        WebElement ProductPrice = driver.findElement(By.id("sp-price-highPrice")); 
        WebElement ProductPriceDiscount = driver.findElement(By.id("sp-price-lowPrice")); 
        String nodiscount = ProductPrice.getText();          
        String discount = ProductPriceDiscount.getText();   
        //indirimli fiyat varsa atanır
        if (discount.isEmpty()){   
        	System.out.println("Product Price : " + nodiscount );
            ProductPriceString = nodiscount;
        }else{                      
        	System.out.println("Product Price : " + discount);
            ProductPriceString = discount;
        }
        
       
        Thread.sleep(1000);
        //aşağıya inilir
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollBy(0,1000)");
        //sepete eklenir
        driver.findElement(By.id("add-to-basket")).click();
        System.out.println("Product added to Basket");
        
        goToBasket();
        
        }
        //sepede gidilir
        public void goToBasket() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"header_wrapper\"]/div[4]/div[3]/a/div[1]")).click(); 
        System.out.println("Showing Basket");
        //sepet fiyatı 
        WebElement BasketPrice = driver.findElement(By.xpath("//*[@id=\"cart-price-container\"]/div[3]/p")); 
        String BasketPriceString= BasketPrice.getText();
        System.out.println("Basket Price : " + BasketPriceString);

        
        //sepetle anasayfa fiyat kontrol
        if(BasketPriceString.equals(ProductPriceString) ==true){ 
        System.out.println("Product price and basket price same ");
        
        }
        else {
        	System.out.println(" Prices are different");
        }
        //sonraki method
        increase();
        }
        public void increase() throws InterruptedException{
        //sepet sayısı 2'ye çıkarılır
        Actions action = new Actions(driver);
        WebElement elem = driver.findElement(By.xpath("//*[@class='amount']"));
        action.moveToElement(elem).build().perform();
        action.contextClick(elem).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
        System.out.println("Product increased to 2 ");

        Thread.sleep(1000);
         
        //2 olduğu kontrol edilir
        String numberOfProduct= (driver.findElement(By.xpath("//*[@id=\"submit-cart\"]/div/div[2]/div[3]/div/div[1]/div/div[5]/div[1]/div/ul/li[1]/div[1]")).getText());

        if(numberOfProduct.charAt(14)=='2') {
        System.out.println("Product number is " + numberOfProduct.charAt(14) );

        Thread.sleep(1000);
        }
        //sonraki method
        deleteItem();
        }
        
        public void deleteItem() throws InterruptedException{
        //item silme
        driver.findElement(By.cssSelector(".btn-delete.btn-update-item")).click();
        System.out.println("Products are deleted");  

        //stringler kıyaslanır
        String actual = driver.findElement(By.xpath("//*[@id=\"empty-cart-container\"]/div[1]/div[1]/div/div[2]/h2")).getAttribute("innerHTML");
        
        
        String expected = "Sepetinizde ürün bulunmamaktadır.";

        if (actual.equals(expected)){
            System.out.println("Basket is empty");
        }
        System.out.println("Test passed.");
    
        }
		
}



		


