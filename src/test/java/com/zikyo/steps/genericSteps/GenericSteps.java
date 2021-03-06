package com.zikyo.steps.genericSteps;

import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.Button;
import com.sdl.selenium.web.button.InputButton;
import com.sdl.selenium.web.form.ComboBox;
import com.sdl.selenium.web.form.TextArea;
import com.sdl.selenium.web.form.TextField;
import com.sdl.selenium.web.link.WebLink;
import com.zikyo.utils.TestUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenericSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericSteps.class);
    @Given("^I open url \"([^\"]*)\"$")
    public void openUrl(String url) {
        WebDriverConfig.getDriver().get(url);
    }

    @And("^I wait (\\d+) second(?:s*)$")
    public void waitSeconds(int seconds) {
        TestUtils.sleep(seconds);
    }

    @When("^I click on link with text \"([^\"]*)\"$")
    public void I_click_on_link_with_text(String text) {
        WebLink link = new WebLink().setText(text);
        link.assertClick();
    }

    @When("^I click on element with text \"([^\"]*)\"$")
    public void I_click_on_element_with_text(String text) throws Throwable {
        WebLocator element = new WebLocator().setText(text);
        element.assertClick();
    }

    @When("^I click on \"([^\"]*)\" button from the section \"([^\"]*)\"$")
    public void I_click_on_element_from_container_with_text(String button, String section) {
        Form form = new Form().setTitle(section);
        WebLink link = new WebLink(form, button);
        link.assertClick();
    }

    @When("^I click on input button with text \"([^\"]*)\"$")
    public void I_click_on_input_button_with_text(String text) {
        InputButton button = new InputButton().setText(text);
        button.assertClick();
    }

    @Then("^I should see an element with text \"([^\"]*)\"$")
    public void assertHaveElementWithText(String text) {
        WebLocator element = new WebLocator().setText(text);
        element.assertReady();
    }

    @Then("^I should see following elements with texts \"(.*)\"$")
    public void assertHaveElementsWithText(List<String> texts) {
        for (String text : texts) {
            assertHaveElementWithText(text);
        }
    }

    @When("^I type \"([^\"]*)\" into \"([^\"]*)\" field$")
    public void typeIntoField(String value, String label) {
        TextField field = new TextField().setLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
        field.setValue(value);
    }

    @Then("^field \"([^\"]*)\" should have value \"([^\"]*)\"$")
    public void text_field_with_label_should_have_value(String label, String value) {
        TextField field = new TextField().setLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
        assertThat(field.getValue(), is(value));
    }

    @When("^I mouse over on element with text \"([^\"]*)\"$")
    public void I_mouse_over_on_element_with_text(String text) {
        WebLocator element = new WebLocator().setText(text);
        element.mouseOver();
    }

    @Given("^I set browser size to (\\d+), (\\d+)$")
    public void setBrowserSize(int width, int height) {
       LOGGER.info("I set browser size to ({}, {})", width, height);
        WebDriverConfig.getDriver().manage().window().setSize(new Dimension(width, height));
    }

    @Given("^I maximize browser$")
    public void maximizeBrowser() {
        WebDriverConfig.getDriver().manage().window().maximize();
    }

    @Then("^I should be on url \"([^\"]*)\"$")
    public void I_should_be_on_url(String url) {
        assertThat(WebDriverConfig.getDriver().getCurrentUrl(), is(url));
    }

    @And("^I select \"([^\"]*)\" in the drop-down list named \"([^\"]*)\"$")
    public void selectValueInDropdown(String value, String label) {
        ComboBox comboBox = new ComboBox().setLabel(label);
        assertThat("Failed to select " + value, comboBox.select(value));
    }

    @And("^I click on link with css \"([^\"]*)\"$")
    public void I_click_on_link_with_css(String css) {
        WebLocator link = new WebLocator().setClasses(css);
        link.assertClick();
    }

    @And("^I type \"([^\"]*)\" into field with id \"([^\"]*)\"$")
    public void I_type_into_field_with_id(String text, String id){
        TextField field = new TextField().setId(id);
        field.setValue(text);
    }

    @And("^I type \"([^\"]*)\" into textarea with id \"([^\"]*)\"$")
    public void I_type_into_textarea_with_id(String text, String id){
        TextArea field = new TextArea().setId(id);
        field.setValue(text);
    }

    @When("^I click on button with text \"([^\"]*)\"$")
    public void I_click_on_button_with_text(String text) {
        Button button = new Button().setText(text);
        button.assertClick();
    }
}
