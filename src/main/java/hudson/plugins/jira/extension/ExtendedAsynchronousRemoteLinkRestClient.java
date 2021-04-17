package hudson.plugins.jira.extension;

import java.net.URI;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.internal.async.AbstractAsynchronousRestClient;

import io.atlassian.util.concurrent.Promise;

/**
 * REST client implementation for adding remote links to Jira issues.
 */
public class ExtendedAsynchronousRemoteLinkRestClient extends AbstractAsynchronousRestClient
        implements RemoteLinkRestClient {

    public ExtendedAsynchronousRemoteLinkRestClient(HttpClient client) {
        super(client);
    }

    @Override
    public Promise<Void> addRemoteLink(URI uri, RemoteLink weblink) {
        return post(uri, weblink, new RemoteLinkJsonGenerator());
    }
}
