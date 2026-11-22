package hudson.plugins.jquery;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hudson.model.PageDecorator;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class JQueryTest {

    @Test
    void pageDecoratorIsRegistered(JenkinsRule r) {
        JQuery decorator = r.jenkins.getExtensionList(PageDecorator.class).get(JQuery.class);
        assertNotNull(decorator, "JQuery PageDecorator should be registered in Jenkins extension list");
    }

    @Test
    void headerInjectsJQueryAndNoConflictAdjuncts(JenkinsRule r) throws Exception {
        try (JenkinsRule.WebClient wc = r.createWebClient()) {
            HtmlPage page = wc.goTo("");
            String html = page.getWebResponse().getContentAsString();
            assertTrue(
                    html.contains("org/kohsuke/stapler/jquery"),
                    "Rendered page header should include org.kohsuke.stapler.jquery adjunct");
            assertTrue(
                    html.contains("hudson/plugins/jquery/JQuery/jquery-no-conflict"),
                    "Rendered page header should include hudson.plugins.jquery.JQuery.jquery-no-conflict adjunct");
        }
    }

    @Test
    void colorBoxAndAdjunctResourcesArePackaged() {
        assertNotNull(
                JQuery.class.getResource("/hudson/plugins/jquery/JQuery/header.jelly"),
                "JQuery header.jelly must be present on classpath");
        assertNotNull(
                JQuery.class.getResource("/hudson/plugins/jquery/JQuery/jquery-no-conflict.js"),
                "jquery-no-conflict.js must be present on classpath");
        assertNotNull(
                JQuery.class.getResource("/hudson/plugins/jquery/colorbox.jelly"),
                "colorbox.jelly adjunct must be present on classpath");
        assertNotNull(
                JQuery.class.getResource("/hudson/plugins/jquery/colorbox/js/jquery.colorbox-min.js"),
                "jquery.colorbox-min.js must be present on classpath");
        assertNotNull(
                JQuery.class.getResource("/hudson/plugins/jquery/colorbox/css/colorbox.css"),
                "colorbox.css must be present on classpath");
    }
}
