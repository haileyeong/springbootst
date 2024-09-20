import org.junit.jupiter.api.*;

public class JUnitCycleQuiz {

  @Test
  public void junitQuiz3() {
    System.out.println("This is first test");
  }

  @Test
  public void junitQuz4() {
    System.out.println("This is second test");
  }

  @BeforeEach
  public void hello() {
    System.out.println("Hello!");
  }

  @AfterAll
  public static void bye() {
    System.out.println("Bye!");
  }

}
