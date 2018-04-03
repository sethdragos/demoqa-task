package com.lottoland.interview.steps;

import com.lottoland.interview.common.Constants;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.pages.Pages;
import org.openqa.selenium.WebDriver;


public class BaseTest {

    @Managed(uniqueSession = true)
    public WebDriver driver;

    @ManagedPages(defaultUrl = Constants.DEFAULT_URL)
    public Pages pages;

}
