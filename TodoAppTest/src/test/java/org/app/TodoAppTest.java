package org.app;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoAppTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String URL = "http://localhost:3000";

    @BeforeAll
    void setupAll() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\bgano\\OneDrive\\Documents\\EST\\S6\\Test logiciel\\selenium-tests\\TodoAppTest\\src\\main\\resources\\chromeDriverManager\\chromedriver.exe"); // à adapter
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(URL);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testAjoutTache() {
        WebElement input = driver.findElement(By.xpath("//input[@type='text']"));
        input.sendKeys("Apprendre Selenium");

        WebElement addButton = driver.findElement(By.xpath("//button[text()='Ajouter']"));
        addButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Apprendre Selenium')]")));
        WebElement addedTask = driver.findElement(By.xpath("//span[contains(text(), 'Apprendre Selenium')]"));

        assertTrue(addedTask.isDisplayed(), "La tâche doit être visible");
    }

    @Test
    void testAjoutTacheVide() {
        WebElement input = driver.findElement(By.xpath("//input[@type='text']"));
        WebElement addButton = driver.findElement(By.xpath("//button[text()='Ajouter']"));

        List<WebElement> tasksBefore = driver.findElements(By.tagName("span"));

        addButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.tagName("span"), tasksBefore.size()));

        List<WebElement> tasksAfter = driver.findElements(By.tagName("span"));

        assertEquals(tasksBefore.size(), tasksAfter.size(), "Aucune tâche ne doit être ajoutée");
    }

    @Test
    void testSuppressionTache() {
        WebElement input = driver.findElement(By.xpath("//input[@type='text']"));
        input.sendKeys("Tâche à supprimer");

        WebElement addButton = driver.findElement(By.xpath("//button[text()='Ajouter']"));
        addButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Tâche à supprimer')]")));
        List<WebElement> tasksBefore = driver.findElements(By.tagName("span"));

        WebElement deleteButton = driver.findElement(By.xpath("(//button[text()='Supprimer'])[1]"));
        deleteButton.click();

        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.tagName("span"), tasksBefore.size()));

        List<WebElement> tasksAfter = driver.findElements(By.tagName("span"));

        assertEquals(tasksBefore.size() - 1, tasksAfter.size(), "La tâche doit être supprimée");
    }

    @Test
    void testModificationEtat() {
        WebElement input = driver.findElement(By.xpath("//input[@type='text']"));
        input.sendKeys("Tâche test statut");

        WebElement addButton = driver.findElement(By.xpath("//button[text()='Ajouter']"));
        addButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Tâche test statut')]")));
        WebElement completeButton = driver.findElement(By.xpath("(//button[contains(text(), 'Completer')])[1]"));
        completeButton.click();

        // Attente que l'élément ait une classe modifiée
        WebElement modifiedTask = driver.findElement(By.xpath("//span[contains(text(), 'Tâche test statut')]"));
        wait.until(driver -> modifiedTask.getAttribute("class").contains("line-through"));

        String classAttr = modifiedTask.getAttribute("class");
        assertTrue(classAttr.contains("line-through") || classAttr.contains("bg-green-200"),
                "La tâche doit être marquée comme complétée");
    }
}
