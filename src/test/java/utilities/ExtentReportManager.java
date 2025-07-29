package utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
    // Report components
    public ExtentSparkReporter sparkReporter; // HTML report designer
    public ExtentReports extent;              // Main report engine
    public ExtentTest test;                   // Individual test logger
    String repName;                           // Dynamic report filename

    // ==================== SETUP REPORT ====================
    public void onStart(ITestContext testContext) {
        // Generate timestamp for unique report filename
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        
        // Configure HTML reporter
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
        sparkReporter.config().setDocumentTitle("opencart Automation Report");
        sparkReporter.config().setReportName("opencart Functional Testing"); 
        sparkReporter.config().setTheme(Theme.DARK); // Dark theme UI
        
        // Initialize main report
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // Add system info
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        
        // Get test parameters from testng.xml
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);
        
        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);
        
        // Display test groups if any
        List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includeGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includeGroups.toString());
        }
    }

    // ==================== TEST PASS HANDLER ====================
    public void onTestSuccess(ITestResult result) {
        // Create test entry in report
        test = extent.createTest(result.getTestClass().getName());
        
        // Tag test with its groups
        test.assignCategory(result.getMethod().getGroups());
        
        // Log passed status
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    // ==================== TEST FAIL HANDLER ====================
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        
        // Log failure details
        test.log(Status.FAIL, result.getName() + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage()); // Error stacktrace
        
        // Capture and attach screenshot on failure
        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath); // Embed screenshot
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==================== TEST SKIP HANDLER ====================
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        
        // Log skip details
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    // ==================== FINALIZE REPORT ====================
    public void onFinish(ITestContext testContext) {
        // Generate the report
        extent.flush();
        
        // Auto-open report in default browser
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);
        
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /* Email functionality (commented out)
        try {
            URL url = new URL("file:///" + pathOfExtentReport);
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com","password"));
            email.setSSLOnConnect(true);
            email.setFrom("pavanoltraining@gmail.com");
            email.setSubject("Test Results");
            email.setMsg("Please find Attached Report....");
            email.addTo("pavankumar.busyqa@gmail.com");
            email.attach(url, "extent report", "please check report...");
            email.send();
        } catch(Exception e) {
            e.printStackTrace();
        }
        */
    }
}