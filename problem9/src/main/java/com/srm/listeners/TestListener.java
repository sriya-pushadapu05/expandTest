package com.srm.listeners;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.srm.driver.DriverFactory;
import com.srm.utils.ExtentManager;
import com.srm.utils.ScreenshotUtils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("=== Test Suite Started ===");
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getTestClass().getRealClass().getSimpleName()
                + " :: " + result.getMethod().getMethodName();

        ExtentTest test = extent.createTest(testName);
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());

        test.info("Test Started: " + result.getMethod().getQualifiedName());

        // Description from @Test
        Test annotation = result.getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(Test.class);

        if (annotation != null && !annotation.description().isEmpty()) {
            test.info("Scenario: " + annotation.description());
        }

        // Parameters
        String params = getParameters(result);
        if (!params.isEmpty()) {
            test.info("Input Data: " + params);
        }

        extentTest.set(test);

        System.out.println("Started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed ✅");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTest test = extentTest.get();

        if (test == null) {
            test = extent.createTest(result.getMethod().getMethodName());
            extentTest.set(test);
        }

        Throwable throwable = result.getThrowable();

        if (throwable != null) {
            test.fail("Reason: " + throwable.getMessage());

            test.fail("<details><summary>Stack Trace</summary><pre>"
                    + escapeHtml(getStackTrace(throwable))
                    + "</pre></details>");
        }

        // Screenshot
        try {
            String path = ScreenshotUtils.capture(
                    DriverFactory.getDriver(),
                    result.getMethod().getMethodName()
            );

            test.fail("Screenshot:",
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());

        } catch (Exception e) {
            test.warning("Screenshot failed: " + e.getMessage());
        }

        System.out.println("Failed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        extentTest.remove();
        System.out.println("=== Test Suite Finished ===");
    } 

    // 🔹 Helper: Parameters
    private String getParameters(ITestResult result) {
        Object[] params = result.getParameters();

        if (params == null || params.length == 0) return "";

        return Arrays.stream(params)
                .map(p -> p == null ? "null" : p.toString())
                .collect(Collectors.joining(" | "));
    }

    // 🔹 Helper: Stack trace
    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    // 🔹 Helper: HTML escape
    private String escapeHtml(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}