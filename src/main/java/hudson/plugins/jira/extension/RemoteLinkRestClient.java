package hudson.plugins.jira.extension;

import java.net.URI;

import io.atlassian.util.concurrent.Promise;

/**
 * Jira REST API client for adding remote links to issues.
 * <p>
 * NOTE: not using an "Extended" prefix here since this interface does not actually extend an existing Atlassian API.
 * See the <a href="https://ecosystem.atlassian.net/browse/JRJC-171">original feature request</a>.
 */
public interface RemoteLinkRestClient {

    /**
     * Adds a remote link to a Jira issue or replaces an previously existing link
     * with a matching <code>globalId</code>.
     *
     * @param uri POST request URL for adding a link (typically ending with ".../issue/JIRA-123/remotelink")
     * @param link remote link content entity
     * @return completion promise
     */
    Promise<Void> addRemoteLink(URI uri, RemoteLink link);
}
