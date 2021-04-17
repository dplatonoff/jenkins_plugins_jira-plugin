package hudson.plugins.jira.extension;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoteLinkJsonGeneratorTest {

    @Test
    public void jsonWithIcon() throws Exception {
        assertEquals("{"
                + "\"globalId\":\"link-id\","
                + "\"object\":{"
                + "\"url\":\"https:\\/\\/ci.jenkins.io\\/job\\/test-build\\/42\","
                + "\"title\":\"SUCCESS: Integrated in test-build #42\","
                + "\"icon\":{"
                + "\"url16x16\":\"https:\\/\\/ci.jenkins.io\\/images\\/16x16\\/blue.png\"}}}",
                new RemoteLinkJsonGenerator().generate(new RemoteLink(
                        "link-id",
                        "https://ci.jenkins.io/images/16x16/blue.png",
                        "https://ci.jenkins.io/job/test-build/42",
                        "SUCCESS: Integrated in test-build #42"))
                .toString());
    }

    @Test
    public void jsonWithoutIcon() throws Exception {
        assertEquals("{"
                + "\"globalId\":\"link-id\","
                + "\"object\":{"
                + "\"url\":\"https:\\/\\/ci.jenkins.io\\/job\\/test-build\\/42\","
                + "\"title\":\"SUCCESS: Integrated in test-build #42\"}}",
                new RemoteLinkJsonGenerator().generate(new RemoteLink(
                        "link-id",
                        null,
                        "https://ci.jenkins.io/job/test-build/42",
                        "SUCCESS: Integrated in test-build #42"))
                .toString());
    }
}
