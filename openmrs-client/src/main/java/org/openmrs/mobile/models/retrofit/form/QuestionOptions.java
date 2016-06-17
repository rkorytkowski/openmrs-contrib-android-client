
package org.openmrs.mobile.models.retrofit.form;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionOptions {

    @SerializedName("rendering")
    @Expose
    private String rendering;
    @SerializedName("concept")
    @Expose
    private String concept;

    /**
     * 
     * @return
     *     The rendering
     */
    public String getRendering() {
        return rendering;
    }

    /**
     * 
     * @param rendering
     *     The rendering
     */
    public void setRendering(String rendering) {
        this.rendering = rendering;
    }

    /**
     * 
     * @return
     *     The concept
     */
    public String getConcept() {
        return concept;
    }

    /**
     * 
     * @param concept
     *     The concept
     */
    public void setConcept(String concept) {
        this.concept = concept;
    }

}
