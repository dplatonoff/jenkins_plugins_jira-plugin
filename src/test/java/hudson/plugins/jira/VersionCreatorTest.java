package hudson.plugins.jira;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.plugins.jira.extension.ExtendedVersion;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.powermock.reflect.Whitebox;


@RunWith(MockitoJUnitRunner.class)
public class VersionCreatorTest {
    private static final String JIRA_VER = Long.toString(System.currentTimeMillis());
    private static final String JIRA_PRJ = "TEST_PRJ";
    private static final String JIRA_VER_PARAM = "${JIRA_VER}";
    private static final String JIRA_PRJ_PARAM = "${JIRA_PRJ}";
    private static final Long ANY_ID = System.currentTimeMillis();
    private static final DateTime ANY_DATE = new DateTime();

    @Mock
    AbstractBuild build;
    @Mock
    BuildListener listener;
    @Mock
    PrintStream logger;
    @Mock
    EnvVars env;
    @Mock
    AbstractProject project;
    @Mock
    JiraSite site;
    @Mock
    JiraSession session;
    @Captor
    ArgumentCaptor<String> versionCaptor;
    @Captor
    ArgumentCaptor<String> projectCaptor;

    private VersionCreator versionCreator = spy(VersionCreator.class);
    private ExtendedVersion existingVersion = new ExtendedVersion(null, ANY_ID, JIRA_VER, null, false, false, ANY_DATE, ANY_DATE);

    @Before
    public void createMocks() throws Exception {
        Whitebox.setInternalState(site,"jiraSession", session);

        when(env.expand(Mockito.anyString())).thenAnswer((Answer<String>) invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            String expanded = (String) args[0];
            if (expanded.equals(JIRA_PRJ_PARAM))
                return JIRA_PRJ;
            else if (expanded.equals(JIRA_VER_PARAM))
                return JIRA_VER;
            else
                return expanded;
        });
        when(listener.getLogger()).thenReturn(logger);
        doReturn(site).when(versionCreator).getSiteForProject(any());
    }

    @Test
    public void callsJiraWithSpecifiedParameters() throws InterruptedException, IOException {
        when(build.getEnvironment(listener)).thenReturn(env);
        when(site.getSession( any() )).thenReturn(session);
        when(session.getVersions(JIRA_PRJ)).thenReturn(Arrays.asList(existingVersion));

        versionCreator.perform(project, JIRA_VER, JIRA_PRJ, build, listener);
        verify(session).addVersion(versionCaptor.capture(), projectCaptor.capture());
        assertThat(projectCaptor.getValue(), is(JIRA_PRJ));
        assertThat(versionCaptor.getValue(), is(JIRA_VER));
    }

    @Test
    public void expandsEnvParameters() throws InterruptedException, IOException {
        when(build.getEnvironment(listener)).thenReturn(env);
        when(site.getSession(any())).thenReturn(session);
        when(session.getVersions(JIRA_PRJ)).thenReturn(Arrays.asList(existingVersion));

        versionCreator.perform(project, JIRA_VER_PARAM, JIRA_PRJ_PARAM, build, listener);
        verify(session).addVersion(versionCaptor.capture(), projectCaptor.capture());
        assertThat(projectCaptor.getValue(), is(JIRA_PRJ));
        assertThat(versionCaptor.getValue(), is(JIRA_VER));
    }

    @Test
    public void buildDidNotFailWhenVersionExists() throws IOException, InterruptedException {
        when(build.getEnvironment(listener)).thenReturn(env);
        ExtendedVersion releasedVersion = new ExtendedVersion(null, ANY_ID, JIRA_VER, null, false, true, ANY_DATE, ANY_DATE);
        when(site.getSession(any())).thenReturn(session);
        when(session.getVersions(JIRA_PRJ)).thenReturn(Arrays.asList(releasedVersion));

        versionCreator.perform(project, JIRA_VER_PARAM, JIRA_PRJ_PARAM, build, listener);
        verify(session, times(0))
                .addVersion(any(), any());
    }

}
