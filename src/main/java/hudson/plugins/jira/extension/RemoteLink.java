package hudson.plugins.jira.extension;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Jira remote link content entity. Used to connect an issue with an arbitrary URL, includes an optional icon.
 * <p>
 * Equals/hash/toString are implemented per existing
 * @{link com.atlassian.jira.rest.client.api.domain} convention.
 */
public class RemoteLink {
    private final String globalId;
    private final String iconUrl;
    private final String linkUrl;
    private final String linkTitle;

    public RemoteLink(String globalId, @Nullable String iconUrl, String linkUrl, String linkTitle) {
        this.globalId = globalId;
        this.iconUrl = iconUrl;
        this.linkUrl = linkUrl;
        this.linkTitle = linkTitle;
    }

    public String getGlobalId() {
        return globalId;
    }

    @Nullable
    public String getIconUrl() {
        return iconUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("globalId", globalId)
                .add("iconUrl", iconUrl)
                .add("linkUrl", linkUrl)
                .add("linkTitle", linkTitle)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RemoteLink) {
            RemoteLink that = (RemoteLink) obj;
            return Objects.equal(this.globalId, that.globalId)
                    && Objects.equal(this.linkUrl, that.linkUrl)
                    && Objects.equal(this.linkTitle, that.linkTitle)
                    && Objects.equal(this.iconUrl, that.iconUrl);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(globalId, linkUrl, linkTitle, iconUrl);
    }
}
