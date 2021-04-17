package hudson.plugins.jira.extension;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.atlassian.jira.rest.client.internal.json.gen.JsonGenerator;

/**
 * JSON generator for Jira remote links. Supports the minimal necessary subset of the
 * <a href="https://developer.atlassian.com/server/jira/platform/using-fields-in-remote-issue-links/">remote link spec</a>.
 */
public class RemoteLinkJsonGenerator implements JsonGenerator<RemoteLink> {

    @Override
    public JSONObject generate(RemoteLink link) throws JSONException {
        final JSONObject json = new JSONObject();

        json.put("globalId", link.getGlobalId());
        json.put("object", new JSONObject()
                .put("url", link.getLinkUrl())
                .put("title", link.getLinkTitle())
                .putOpt("icon", link.getIconUrl() == null ? null
                        : new JSONObject().put("url16x16", link.getIconUrl())));

        return json;
    }
}
