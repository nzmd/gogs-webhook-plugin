/*

The MIT License (MIT)
Copyright (c) 2016 Alexander Verhaar

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF
OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

*/

package org.jenkinsci.plugins.gogs;

import hudson.Extension;
import hudson.model.Job;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class GogsProjectProperty extends JobProperty<Job<?, ?>> {
    private final String gogsSecret;
    private final boolean gogsUsePayload;

    @DataBoundConstructor
    public GogsProjectProperty(String gogsSecret, boolean gogsUsePayload) {
        this.gogsSecret = gogsSecret;
        this.gogsUsePayload = gogsUsePayload;
    }

    public String getGogsSecret() {
        return this.gogsSecret;
    }

    public boolean getGogsUsePayload() {
        return this.gogsUsePayload;
    }

    private static final Logger LOGGER = Logger.getLogger(GogsWebHook.class.getName());

    @Extension
    public static final class DescriptorImpl extends JobPropertyDescriptor {
        public static final String GOGS_PROJECT_BLOCK_NAME = "gogsProject";
        private String gogsSecret;
        private boolean gogsUsePayload;

        public String getGogsSecret() {
            return gogsSecret;
        }

        public boolean getGogsUsePayload() {
            return gogsUsePayload;
        }

        public JobProperty<?> newInstance(StaplerRequest req, JSONObject formData) {
            GogsProjectProperty tpp = req.bindJSON(
                    GogsProjectProperty.class,
                    formData.getJSONObject(GOGS_PROJECT_BLOCK_NAME)
            );
            if (tpp != null) {
                LOGGER.finest(formData.toString());
                LOGGER.finest(tpp.gogsSecret);

                gogsSecret = tpp.gogsSecret;
            }
            return tpp;
        }

        @Override
        public String getDisplayName() {
            return "Gogs Secret";
        }
    }
}
