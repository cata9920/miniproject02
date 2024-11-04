import com.beust.ah.A;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions fo = new FirefoxOptions();
       // fo.addArguments("--headless");
      //  fo.addArguments("--disable-gpu");
      //  fo.addArguments("--window-size=500,500");

        WebDriver driver = new FirefoxDriver(fo);
        driver.manage().timeouts().implicitlyWait(Duration.ofHours(50));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofHours(50));
        driver.manage().timeouts().scriptTimeout(Duration.ofHours(50));

        driver.get("https://www.imdb.com/list/ls021215813/");

        WebDriverWait webDriverWait = new WebDriverWait(driver,Duration.ofHours(100));



        webDriverWait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight);");




        WebElement ele = driver.findElement(By.xpath("//button[contains(@class,'icb-btn sc-bcXHqe sc-hLBbgP sc-ftTHYK dcvrLS dufgkr ecppKW')]"));
        ele.click();

        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("*")));



        List<WebElement> eles = driver.findElements(By.tagName("h3"));


        List<String> filtheredList = new ArrayList<>();


        for (WebElement e : eles) {
           // System.out.println(e.getText());
            String text = e.getText();
            String tokens[] = text.split("\\s++");

            if (isInteger(tokens[0].replace(".",""))) {

                if (tokens.length == 1) {

                  //  System.out.println(tokens[0] + " " + tokens[1]);
                    filtheredList.add(tokens[0] + " " +tokens[1]);

                }

                if (tokens.length == 2) {

                  //  System.out.println(tokens[0] + " " + tokens[1]);
                    filtheredList.add(tokens[0].replace(".","") + " " + tokens[1]);

                }


                if (tokens.length == 3) {

                  //  System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2]);
                    filtheredList.add(tokens[0].replace(".","") + " " + tokens[1] + " " + tokens[2]);
                }

                if (tokens.length == 4) {

                   // System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3]);
                    filtheredList.add(tokens[0].replace(".","") + " " + tokens[1] + " " + tokens[2] + " " + tokens[3]);

                }
            }

        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/home/$Your$User$Name/Desktop/TopFootballPlayersWebScrapper.csv"))) {

            String headercsv = "Top,FullName";
            writer.write(headercsv);
            writer.newLine();

            for (String element : filtheredList) {
                String[] full = element.split("\\s++");
                System.out.println(full.length);
                if (full.length == 1) {
                    String first = full[0];
                    String second = full[1];
                    writer.write(first + "," + second + "\n" );
                }

                if (full.length == 2) {
                    String first = full[0];
                    String second = full[1];
                   // String third = full[2];
                    writer.write(first + "," + second + "," + "\n" );
                }

                if (full.length == 3) {
                    String first = full[0];
                    String second = full[1];
                    String third = full[2];
                    writer.write(first + "," + second + "," + third + "\n" );
                }

                if (full.length == 4) {
                    String first = full[0];
                    String second = full[1];
                    String third = full[2];
                    String fourth = full[3];
                    writer.write(first + "," + second + "," + third + "," + fourth + "\n" );
                }
               // writer.write(element + "\n");
               // writer.newLine(); // Scrie un newline după fiecare element
            }
        } catch (IOException e) {
            e.printStackTrace();
        }





        driver.quit();
    }

    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            //System.out.print(e.getCause());
            return false;
        }
    }
}