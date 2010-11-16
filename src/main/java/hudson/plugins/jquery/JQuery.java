package hudson.plugins.jquery;

import hudson.Extension;
import hudson.model.PageDecorator;

@Extension(ordinal=1)
public class JQuery extends PageDecorator {

    public JQuery() {
        super(JQuery.class);
    }

}
