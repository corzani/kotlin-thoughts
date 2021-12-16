import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/bdd"],
    plugin = ["pretty","html:target/bdd.html"]
)
class RunCucumberTest